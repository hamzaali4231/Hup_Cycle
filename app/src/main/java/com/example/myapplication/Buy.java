package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.ViewHold.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Buy extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference productDatabaseReference;

    public DrawerLayout drawerLayout;

    public String selectedSort, selectedCategory;
    private RecyclerView recyclerView;
    public Query itemQuery;
    private Spinner dropDownSort, dropDownCategory;

    int check =0;
    int check2 =0;

    DatabaseReference itemDatabase;

    ArrayList<Products> list;
    MyAdapter adapter;

    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        productDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Products");

        Toolbar toolbar = findViewById(R.id.toolbar);
        drawerLayout= findViewById(R.id.drawer_layout);
        dropDownSort = (Spinner) findViewById(R.id.sortBySpinner);
        dropDownCategory = (Spinner) findViewById(R.id.categorySpinner);

        dropDownSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (++check > 1){
                    itemSortFilter();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dropDownCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (++check2 > 1) {
                    itemCategoryFilter();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Drop down menus
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.category_names));

        ArrayAdapter<String> sortAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.sort_by));


        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownCategory.setAdapter(categoryAdapter);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownSort.setAdapter(sortAdapter);


        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void itemSortFilter() {
         selectedSort = dropDownSort.getSelectedItem().toString();

        if (selectedSort.equals("Lowest Price")) {
            Collections.sort(list, new Comparator<Products>() {
                @Override
                public int compare(Products item1, Products item2) {
                    return Integer.compare(Integer.parseInt(item1.getPrice()), Integer.parseInt(item2.getPrice()));
                }
            });

        } else if (selectedSort.equals("Highest Price")) {
            Collections.sort(list, new Comparator<Products>() {
                @Override
                public int compare(Products item1, Products item2) {
                    return Integer.compare(Integer.parseInt(item2.getPrice()), Integer.parseInt(item1.getPrice()));
                }
            });

        } else if (selectedSort.equals("Recommended") && selectedCategory.equals("All Category")){
            defaultGetItems();
        }

        if (!selectedSort.equals("Recommended")){
            adapter = new MyAdapter(Buy.this, list);
            recyclerView.setAdapter(adapter);
        }


    }

    private void itemCategoryFilter() {
        selectedCategory = dropDownCategory.getSelectedItem().toString();

        if (selectedCategory.equals("All Category")){
            defaultGetItems();
        } else if (selectedCategory.equals("Art")) {
            itemQuery = productDatabaseReference.orderByChild("category").equalTo(selectedCategory);
            getCategoryItem();
        } else if (selectedCategory.equals("Electronics")) {
            itemQuery = productDatabaseReference.orderByChild("category").equalTo(selectedCategory);
            getCategoryItem();
        } else if (selectedCategory.equals("Fashion")) {
            itemQuery = productDatabaseReference.orderByChild("category").equalTo(selectedCategory);
            getCategoryItem();
        } else if (selectedCategory.equals("Home")) {
            itemQuery = productDatabaseReference.orderByChild("category").equalTo(selectedCategory);
            getCategoryItem();
        } else if (selectedCategory.equals("Health & Beauty")) {
            itemQuery = productDatabaseReference.orderByChild("category").equalTo(selectedCategory);
            getCategoryItem();
        } else if (selectedCategory.equals("Home Garden")) {
            itemQuery = productDatabaseReference.orderByChild("category").equalTo(selectedCategory);
            getCategoryItem();
        } else if (selectedCategory.equals("Motor")) {
            itemQuery = productDatabaseReference.orderByChild("category").equalTo(selectedCategory);
            getCategoryItem();
        } else if (selectedCategory.equals("Sports")) {
            itemQuery = productDatabaseReference.orderByChild("category").equalTo(selectedCategory);
            getCategoryItem();
        }

        dropDownSort.setSelection(0);
    }

    public void getCategoryItem() {
        itemQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<Products>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Products p = dataSnapshot1.getValue(Products.class);
                        list.add(p);
                    }
                } else {
                    Toast.makeText(Buy.this, "Empty", Toast.LENGTH_LONG).show();
                }

                adapter = new MyAdapter(Buy.this, list);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Buy.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void defaultGetItems() {
        FirebaseRecyclerOptions <Products> options =
                new FirebaseRecyclerOptions.Builder<Products>().setQuery(productDatabaseReference,Products.class)
                        .build();

        FirebaseRecyclerAdapter <Products, ProductViewHolder> adapter = new
                FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i, @NonNull final Products model) {

                        productViewHolder.productName.setText(model.getName());
                        productViewHolder.productDescription.setText(model.getDescription());
                        productViewHolder.productPrice.setText("Price: £" + model.getPrice());
                        Picasso.get().load(model.getImage()).into(productViewHolder.imageView);

                        productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(Buy.this, ProductDetailsActivity.class);
                                intent.putExtra("loginUsername", getIntent().getStringExtra("loginUsername"));
                                intent.putExtra("id",model.getId());
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_items_layout,
                                parent,false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


    @Override
    protected void onStart() {
        super.onStart();
        defaultGetItems();
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }}

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            int id=menuItem.getItemId();

            if (id==R.id.nav_cart){
                Intent intent = new Intent(Buy.this,CartActivity.class);
                intent.putExtra("loginUsername", getIntent().getStringExtra("loginUsername"));
                System.out.println("Login opening");
                startActivity(intent);

            }
            else if (id == R.id.nav_about){
                Intent intent = new Intent(Buy.this,aboutFragment.class);
                System.out.println("Login opening");
                startActivity(intent);
            }

            else if (id == R.id.nav_login){
                Intent intent = new Intent(Buy.this,Login.class);
                System.out.println("Login opening");
                startActivity(intent);
            }
            else if(id ==R.id.nav_map){
                Intent intent = new Intent(Buy.this,LocationPage2.class);
                startActivity(intent);
            }

            else if (id== R.id.nav_buy){

            }



//        switch (menuItem.getItemId()){
////
////            case R.id.nav_login:
////                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
////                        new Login()).commit();
////                break;
////            case R.id.nav_about:
////                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
////                        new aboutFragment()).commit();
////                break;
//
//            case R.id.nav_map:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new MapsActivity()).commit();
//                break;
//            case R.id.nav_buy:
//
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new buyFragment()).commit();
//                break;
//
////            case R.id.nav_cart:
////                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
////                        new CartActivity()).commit();
////                break;
//
//            case R.id.nav_share:
//                Toast.makeText(this,"IT is shared", Toast.LENGTH_SHORT).show();
//        }
        DrawerLayout drawerLayout=  findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}


package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Arrays;


public class Buy extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference productDatabaseReference;

    public DrawerLayout drawerLayout;

    private RecyclerView recyclerView;
    public Query itemQuery;
    private Spinner dropDownSort, dropDownCategory;

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

        dropDownCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                itemCategorySorter();
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
//        if (savedInstanceState==null) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                    new Login()).commit();
//            navigationView.setCheckedItem(R.id.nav_view);
//        }

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }


    private void itemCategorySorter() {
        String selectedItem = dropDownCategory.getSelectedItem().toString();


        if (selectedItem.equals("Electronics")) {
            itemQuery = productDatabaseReference.orderByChild("category").equalTo(selectedItem);
            getCategoryItem();
        }
    }

    public void getCategoryItem() {
        itemQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("************************** " + dataSnapshot);
                System.out.println(" ");
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


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions <Products> options =
                new FirebaseRecyclerOptions.Builder<Products>().setQuery(productDatabaseReference,Products.class)
                        .build();

        FirebaseRecyclerAdapter <Products, ProductViewHolder> adapter = new
                FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i, @NonNull final Products model) {


                        productViewHolder.productName.setText(model.getName());
                        productViewHolder.productDescription.setText(model.getDescription());
                        productViewHolder.productPrice.setText("Price = " + model.getPrice());
                        Picasso.get().load(model.getImage()).into(productViewHolder.imageView);

                        productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(Buy.this, ProductDetailsActivity.class);
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

//        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Login()).commit();
//        navigationView.setCheckedItem(R.id.nav_login);
////>>>>>>> Stashed changes
//        Intent intent = new Intent(Buy.this, Login.class);
//        startActivity(intent);
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


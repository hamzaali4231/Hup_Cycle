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
import com.example.myapplication.ViewHold.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class Buy extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference productDatabaseReference;

    public DrawerLayout drawerLayout;

    private RecyclerView recyclerView;


    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        productDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Products");


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout= findViewById(R.id.drawer_layout);


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

//        if (id == R.id.action_settings)
//        {
//            return true;
//        }

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


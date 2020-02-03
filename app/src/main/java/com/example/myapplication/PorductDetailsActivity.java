package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PorductDetailsActivity extends AppCompatActivity {


   // private Button addtocart;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private TextView priceproduct, nameproduct, descriptionproduct;
    private String productsID = "", state= "Normal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productsID = getIntent().getStringExtra("pid");
//        addtocart = (Button) findViewById(R.id.but_add_cart);
        productImage = (ImageView) findViewById(R.id.product_image_details);
        numberButton = (ElegantNumberButton) findViewById(R.id.amount_btn);

        priceproduct = (TextView) findViewById(R.id.product_price_details);
        nameproduct = (TextView) findViewById(R.id.product_name_details);
        descriptionproduct = (TextView) findViewById(R.id.product_description_details);

        getProductDetails(productsID);
        
    }

    private void getProductDetails(String productsID) {
        DatabaseReference productsref = FirebaseDatabase.getInstance().getReference().child("Products");

        productsref.child(productsID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    Products products= dataSnapshot.getValue(Products.class);
                    nameproduct.setText(products.getName());
                    priceproduct.setText(products.getPrice());
                    descriptionproduct.setText(products.getDescription());
                    Picasso.get().load(products.getImage()).into(productImage);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

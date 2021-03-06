package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailsActivity extends AppCompatActivity {
    private Button addtocart;
    private TextView priceProduct, nameProduct, descriptionProduct, quantityProduct, user;
    private String productsID = "", state= "Normal";
    private ElegantNumberButton numberButton;
    private ImageView productImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productsID = getIntent().getStringExtra("id");

        if (productsID == null) {
            throw new IllegalStateException("No PID");
        }
        addtocart = (Button) findViewById(R.id.but_add_cart);
        productImage = (ImageView) findViewById(R.id.product_image_details);
        numberButton = (ElegantNumberButton) findViewById(R.id.amount_cart_btn);
        priceProduct = (TextView) findViewById(R.id.product_price_details);
        quantityProduct = (TextView) findViewById(R.id.product_quantity);
        nameProduct = (TextView) findViewById(R.id.product_name_details);
        descriptionProduct = (TextView) findViewById(R.id.product_description_details);
        user = (TextView) findViewById(R.id.userText);

       getProductDetails(productsID);

//       numberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
//           @Override
//           public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
//               if (oldValue >= Integer.valueOf(String.valueOf(quantityProduct)) ) {
//                   System.out.println("***********************" + Integer.valueOf(numberButton.getNumber()));
//                   System.out.println("***********************" + Integer.valueOf(String.valueOf(quantityProduct)));
//                   numberButton.setNumber(String.valueOf(quantityProduct));
//                   Toast.makeText(ProductDetailsActivity.this, "Error", Toast.LENGTH_SHORT).show();
//               }
//           }
//       });

       addtocart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               addingToCartList();
           }
       });
        
    }

    private void addingToCartList() {

        String saveCurrentTime, saveCurrentDate;

        Calendar callForDate =  Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd MM, yyyy");
        saveCurrentDate = currentDate.format(callForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH, MM, SS");
        saveCurrentTime = currentTime.format(callForDate.getTime());

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("id", productsID);
        cartMap.put("user", getIntent().getStringExtra("loginUsername"));
        cartMap.put("name", nameProduct.getText().toString());
        cartMap.put("price", priceProduct.getText().toString());
        //cartMap.put("ImageUrl",productImage);
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("quantity", numberButton.getNumber());

        cartListRef.child("User View").child("Products").child(productsID).updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            cartListRef.child("Admin View").child("Products").child(productsID).
                                    updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    Toast.makeText(ProductDetailsActivity.this, "Item " +
                                                    "added to cart",
                                            Toast.LENGTH_SHORT).show();
                                    finish();

                                }
                            });
                        }
                    }
                });

    }

    private void getProductDetails(String productsID) {
        DatabaseReference productsref = FirebaseDatabase.getInstance().getReference().child("Products");

        productsref.child(productsID).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.child("user").exists()){
                        String userValue = (String) dataSnapshot.child("user").getValue();
                        user.setVisibility(View.VISIBLE);
                        user.setText("Sold By: " + userValue);
                    }
                    Products products= dataSnapshot.getValue(Products.class);
                    nameProduct.setText(products.getName());
                    priceProduct.setText("£" + products.getPrice());
                    if (Integer.parseInt(products.getQuantity()) > 10){
                        quantityProduct.setText("Quantity: 10+");
                    } else {
                        quantityProduct.setText("Quantity: " + products.getQuantity());
                    }
                    descriptionProduct.setText(products.getDescription());
                    Picasso.get().load(products.getImage()).into(productImage);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

}

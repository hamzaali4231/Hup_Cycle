package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class PaymentMethodMenu extends AppCompatActivity {

    private Button cardButton, cashButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_method_menu);

        cardButton = (Button) findViewById(R.id.cardScreen);
        cashButton = (Button) findViewById(R.id.cashScreen);

        cardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cashPayment();
            }
        });

        cashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               cashPayment();
            }
        });
    }

    public void cashPayment() {
        FirebaseDatabase.getInstance().getReference().child("Cart List")
                .child("User View").removeValue().addOnCompleteListener
                (new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent in = new Intent(PaymentMethodMenu.this, Buy.class);
                        Toast.makeText(PaymentMethodMenu.this, "Items reserved", Toast.LENGTH_SHORT).show();
                        startActivity(in);
                        finish();
                    }
                });

    }
}

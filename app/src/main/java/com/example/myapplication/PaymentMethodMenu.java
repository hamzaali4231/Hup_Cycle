package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

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
                Intent intent = new Intent(PaymentMethodMenu.this, CardPaymentActivity.class);
                intent.putExtra("userEmail", getIntent().getStringExtra("userEmail"));
                startActivity(intent);

            }
        });

        cashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        });
    }

    private void sendMail() {

        String mail = getIntent().getStringExtra("userEmail");
        String message = "Item has been reserved. You have 7 days to collect your item or reservation will be declined";
        String subject = "Item Reservation";

        //Send Mail
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,mail,subject,message);
        javaMailAPI.execute();

        cashPayment();

    }

    public void cashPayment() {
        FirebaseDatabase.getInstance().getReference().child("Cart List")
                .child("User View").removeValue().addOnCompleteListener
                (new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(PaymentMethodMenu.this, "Items reserved", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

    }

}

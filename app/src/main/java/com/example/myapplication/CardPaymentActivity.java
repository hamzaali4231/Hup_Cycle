package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CardPaymentActivity extends AppCompatActivity {

    private Button confirmbutton;

    private TextView totalAmount;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_payment);

        confirmbutton = findViewById(R.id.confirm_button);
        layoutManager = new LinearLayoutManager(this);



        confirmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        makePayment();

    }

    public void makePayment(){

        String cardcehcker = ((EditText) findViewById(R.id.cardno)).getText().toString();
        String sortchecker = ((EditText) findViewById(R.id.sortcode)).getText().toString();
        String cvvchecker = ((EditText) findViewById(R.id.cvv)).getText().toString();

        if (cardcehcker.trim().length() == 16 && sortchecker.trim().length() == 6 &&
                cvvchecker.trim().length() == 3) {
            {
                FirebaseDatabase.getInstance().getReference().child("Cart List")
                        .child("User View").removeValue().addOnCompleteListener
                        (new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(CardPaymentActivity.this, "Items reserved", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                                                });


            }

        }
        else{
            Toast.makeText(CardPaymentActivity.this, "Error occured", Toast.LENGTH_SHORT).show();

        }
    }
}




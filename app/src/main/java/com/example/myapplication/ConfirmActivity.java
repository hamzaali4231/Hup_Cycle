package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.ViewHold.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ConfirmActivity extends AppCompatActivity {

    private Button confirmbutton;

    private TextView totalAmount;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_order);

        confirmbutton = findViewById(R.id.confirm_button);
        layoutManager = new LinearLayoutManager(this);



        confirmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePayment();
            }
        });
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
                                Intent in = new Intent(ConfirmActivity.this, Buy.class);
                                Toast.makeText(ConfirmActivity.this, "Items reserved", Toast.LENGTH_SHORT).show();
                                startActivity(in);
                                finish();
                            }
                                                });


                                    }}
    }
}




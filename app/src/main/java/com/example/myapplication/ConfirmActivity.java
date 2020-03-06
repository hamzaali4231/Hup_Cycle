package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmActivity extends AppCompatActivity {

    private Button confirmbutton;

    private String totalAmount ="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_order);

        totalAmount=getIntent().getStringExtra("Total is");
        confirmbutton= findViewById(R.id.confirm_button);

    }
}

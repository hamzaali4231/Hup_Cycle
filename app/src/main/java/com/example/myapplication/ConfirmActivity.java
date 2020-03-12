package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmActivity extends AppCompatActivity {

    private Button confirmbutton;

    private TextView totalAmount;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_order);

        confirmbutton= findViewById(R.id.confirm_button);


        confirmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardcehcker = ((EditText) findViewById(R.id.cardno)).getText().toString();
                String sortchecker = ((EditText) findViewById(R.id.sortcode)).getText().toString();
                String cvvchecker = ((EditText) findViewById(R.id.cvv)).getText().toString();

                if (cardcehcker.trim().length() == 16 && sortchecker.trim().length() == 6 &&
                        cvvchecker.trim().length() == 3 ){
                    {
                        Intent in = new Intent(ConfirmActivity.this, Buy.class);
                        startActivity(in);
                    }}
            }
        });
    }
}

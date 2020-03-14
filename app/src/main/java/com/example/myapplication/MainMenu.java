package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity {

    private Button buyButton, sellButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        buyButton = (Button) findViewById(R.id.buyScreen);
        sellButton = (Button) findViewById(R.id.sellScreen);

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Buy.class);
                intent.putExtra("loginUsername", getIntent().getStringExtra("loginUsername"));
                startActivity(intent);
            }
        });

        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), Sell.class);
                intent.putExtra("loginUsername", getIntent().getStringExtra("loginUsername"));
                startActivity(intent);
            }
        });
    }
}

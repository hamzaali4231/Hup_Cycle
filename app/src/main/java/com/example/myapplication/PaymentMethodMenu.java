package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;

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
                Intent intent = new Intent(getApplicationContext(), CardPaymentActivity.class);
                startActivity(intent);
            }
        });

        cashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), Sell.class);
                startActivity(intent);
            }
        });
    }
}

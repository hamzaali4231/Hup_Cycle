package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.hololo.tutorial.library.PermissionStep;
import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;

public class WelcomeScreen extends TutorialActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addFragment(new Step.Builder().setTitle("Get Paid")
                .setContent("Sell your unwanted items and get paid.")
                .setBackgroundColor(Color.parseColor("#d0d74b")) // int background color
                .setDrawable(R.drawable.money) // int top drawable
                .build());

        addFragment(new Step.Builder().setTitle("Large Audience!")
                .setContent("Connect with the thousands of customers that are already using the app")
                .setBackgroundColor(Color.parseColor("#0f3945"))
                .setDrawable(R.drawable.people) // int top drawable
                .build());
    }

    @Override
    public void finishTutorial() {
        Intent intent = new Intent(WelcomeScreen.this, Login.class);
        startActivity(intent);
    }

    @Override
    public void currentFragmentPosition(int position) {

    }
}

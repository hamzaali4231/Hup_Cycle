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

        addFragment(new Step.Builder().setTitle("Welcome To Hup Cycle")
                .setContent("An app for the popular second hand shop, Hup Cycle")
                .setBackgroundColor(Color.parseColor("#645193")) // int background color
                .setDrawable(R.drawable.welcome) // int top drawable
                .build());

        addFragment(new Step.Builder().setTitle("Get Paid")
                .setContent("Sell unwanted items and get paid in cash with no hidden charges")
                .setBackgroundColor(Color.parseColor("#86a84a")) // int background color
                .setDrawable(R.drawable.money) // int top drawable
                .build());

        addFragment(new Step.Builder().setTitle("Easy Payment")
                .setContent("Pay for items with either cash in hand or by card, Simple!")
                .setBackgroundColor(Color.parseColor("#fcc601"))
                .setDrawable(R.drawable.payment) // int top drawable
                .build());

        addFragment(new Step.Builder().setTitle("Many Items")
                .setContent("Browse through the many categories of items being sold")
                .setBackgroundColor(Color.parseColor("#424242")) // int background color
                .setDrawable(R.drawable.shopping) // int top drawable
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

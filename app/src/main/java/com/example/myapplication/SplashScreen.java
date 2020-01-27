package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity implements Runnable{

    private final static int Delay=3000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Handler splashHandler= new Handler();
        splashHandler.postDelayed(this,Delay);
    }

    @Override
    public void run() {
        Intent splashIntent = new Intent(SplashScreen.this, MainActivity.class);
        SplashScreen.this.startActivity(splashIntent);
        SplashScreen.this.finish();
    }
}


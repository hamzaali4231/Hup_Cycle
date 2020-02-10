package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity implements Runnable{

    private final static int Delay=5000;

    Animation topAnimation;

    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Handler splashHandler= new Handler();
        splashHandler.postDelayed(this,Delay);

        //Animations for splash screen
        image = findViewById(R.id.logo);
        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation_splash);
        image.startAnimation(topAnimation);

    }

    @Override
    public void run() {
        Intent splashIntent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(splashIntent);
       finish();
    }
}


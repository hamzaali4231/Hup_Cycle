package com.example.myapplication;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.Serializable;

import androidx.appcompat.app.AppCompatActivity;

public class AdminAddItem extends AppCompatActivity {

    private ImageView item1, item2, item3;


    @Override
    protected void onCreate (Bundle savedInstance) {

        super.onCreate(savedInstance);

        setContentView(R.layout.activity_admin_add_item);

    }


}

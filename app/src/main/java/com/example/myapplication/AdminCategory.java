package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AdminCategory extends AppCompatActivity {

    private ImageView itemImage;

    @Override
    protected void onCreate (Bundle savedInstance) {

        super.onCreate(savedInstance);

        setContentView(R.layout.activity_admin_select_category);

        itemImage = (ImageView) findViewById(R.id.itemDatabase);


        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminCategory.this, AdminAddNewItem.class);

                intent.putExtra("Category", "item1");
                startActivity(intent);
            }
        });

        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminCategory.this, AdminAddNewItem.class);

                intent.putExtra("Category", "item2");
                startActivity(intent);
            }
        });

        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(AdminCategory.this, AdminAddNewItem.class);

                intent.putExtra("Category", "item3");
                startActivity(intent);
            }
        });
    }
}

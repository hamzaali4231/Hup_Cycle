package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class AdminCategory extends AppCompatActivity {

    private ImageView item1, item2, item3;

    @Override
    protected void onCreate (Bundle savedInstance) {

        super.onCreate(savedInstance);

        setContentView(R.layout.activity_admin_select_category);

        item1 = (ImageView) findViewById(R.id.item1);
        item2 = (ImageView) findViewById(R.id.item2);
        item3= (ImageView) findViewById(R.id.item3);


        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminCategory.this, AdminAddNewItem.class);

                intent.putExtra("Category", "item1");
                startActivity(intent);
            }
        });

        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AdminCategory.this, AdminAddNewItem.class);

                intent.putExtra("Category", "item2");
                startActivity(intent);
            }
        });

        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(AdminCategory.this, AdminAddNewItem.class);

                intent.putExtra("Category", "item3");
                startActivity(intent);
            }
        });
    }
}

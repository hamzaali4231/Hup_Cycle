package com.example.myapplication;

import android.net.Uri;
import android.widget.ImageView;

public class Cart {

    private String date, id, name, price, quantity, time;
    private Uri imageView;
    Cart(){}


    public Cart(String date, String id, String name, String price, String quantity, String time,
                Uri imageView) {
        this.date = date;
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.time = time;
        this.imageView=imageView;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public Uri getImageView() {
        return imageView;
    }

    public void setImageView(Uri imageView) {
        this.imageView = imageView;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

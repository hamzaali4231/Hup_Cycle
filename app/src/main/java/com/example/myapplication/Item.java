package com.example.myapplication;

import android.media.Image;

import java.io.Serializable;

public class Item implements Serializable {
    public String itemName;
    public double itemPrice;
    public Image itemImage;

    public Item(String itemName, double itemPrice, Image itemImage) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemImage = itemImage;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Image getItemImage() {
        return itemImage;
    }

    public void setItemImage(Image itemImage) {
        this.itemImage = itemImage;
    }


}

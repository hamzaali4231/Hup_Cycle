package com.example.myapplication;

public class Products {

    private String name;
    private String description;
    private String price;
    //private String quantity;
    private String image;
    private String category;
    private String date;
    private String id;
    private String time;

    public Products(){

    }

    public Products(String name, String description, String price, String id, String image, String category, String date, String time) {
        this.name = name;
        this.description = description;
        this.price = price;
        //this.quantity = quantity;
        this.image = image;
        this.category = category;
        this.date = date;
        this.time = time;
        this.id=id;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public String getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(String quantity) {
//        this.quantity = quantity;
//    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

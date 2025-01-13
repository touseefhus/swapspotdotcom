package com.example.app;

public class Product {

    public String category;
    public String imageUri;
    public String name;
    public  String description;


    public Product(String category, String name, String description, String imageURI) {
        this.category = category;
        this.name = name;
        this.imageUri = imageURI;
        this.description= description;

    }

    public Product(){

    }

}

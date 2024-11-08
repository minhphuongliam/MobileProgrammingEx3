package com.turtorials.sharedpreference_sqlite;
public class Product {

    private int id;
    private String name;
    private double price;
    private String category;
    private String imagePath;

    public Product(int id, String name, double price, String imagePath, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
        this.category = category;
    }
    public Product(String name, double price, String imagePath, String category) {
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
        this.category = category;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getImagePath() { return imagePath; }
    public String getCategory() { return category; }
}

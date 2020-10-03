package com.example.finery;

//Model of the Product
public class Product {

    //Variable Declaration
    private String id;
    private String title;
    private String image;
    private String size;
    private String color;
    private String description;
    private int price;
    private int offer;

    //Default Constructor
    public Product() {
    }

    //Constructor
    public Product(String id, String title, String image, String size, String color, String description, int price, int offer) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.size = size;
        this.color = color;
        this.description = description;
        this.price = price;
        this.offer = offer;
    }

    //Getters and setters of variable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getOffer() {
        return offer;
    }

    public void setOffer(int offer) {
        this.offer = offer;
    }
}


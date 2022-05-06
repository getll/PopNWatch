package com.example.popnwatch;

public class CartSnack {
    //snack
    String id;
    String name;
    String img;
    double price;
    String genre;

    int quantity;

    public CartSnack(String id, String name, String img, double price, String genre, int quantity) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.price = price;
        this.genre = genre;
        this.quantity = quantity;
    }

    public CartSnack() {
    }

    public double getTotalPrice() {
        return price * quantity;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

package com.example.popnwatch;

public class CartSnack {
    //cart
    String snackCartId;
    int quantity;
    String cartId;

    //snack
    String id;
    String name;
    String img;
    double price;
    String genre;

    public CartSnack(String snackCartId, int quantity, String cartId, String id, String name, String img, double price, String genre) {
        this.snackCartId = snackCartId;
        this.quantity = quantity;
        this.cartId = cartId;
        this.id = id;
        this.name = name;
        this.img = img;
        this.price = price;
        this.genre = genre;
    }

    public CartSnack() {
    }

    public String getSnackCartId() {
        return snackCartId;
    }

    public void setSnackCartId(String snackCartId) {
        this.snackCartId = snackCartId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
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
}

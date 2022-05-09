package com.example.popnwatch;

import java.util.List;

public class Cart {
    String cartId;
    SelectedMovie movie;
    int quantity;
    String movieTitle;
    String userId;
    boolean isPaid;
    List<CartSnack> cartSnacks;

    public Cart(String cartId, SelectedMovie movie, int quantity, String movieTitle, String userId, boolean isPaid, List<CartSnack> cartSnacks) {
        this.cartId = cartId;
        this.movie = movie;
        this.quantity = quantity;
        this.movieTitle = movieTitle;
        this.userId = userId;
        this.isPaid = isPaid;
        this.cartSnacks = cartSnacks;
    }

    public Cart() {
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public SelectedMovie getMovie() {
        return movie;
    }

    public void setMovie(SelectedMovie movie) {
        this.movie = movie;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public String getApiId() {
        return movie.getApiId();
    }

    public void setApiId(String apiId) {
        movie.setApiId(apiId);
    }

    public int getScreen() {
        return movie.getScreen();
    }

    public void setScreen(int screen) {
        movie.setScreen(screen);
    }

    public String getTime() {
        return movie.getTime();
    }

    public void setTime(String time) {
        movie.setTime(time);
    }

    public List<CartSnack> getCartSnacks() {
        return cartSnacks;
    }

    public void setCartSnacks(List<CartSnack> cartSnacks) {
        this.cartSnacks = cartSnacks;
    }

    @Override
    public String toString() {
        double ticketPrice = 15.99;
        double totalSnackCartPrice = 0;
        double totalTicketPrice = ticketPrice * quantity;

        String formattedReceipt = "\tTICKET\n";
        formattedReceipt += String.format("\t%-4d x %s : %s\n", quantity, "Tickets for", movieTitle);
        formattedReceipt += String.format("\t%-4d x %-20.2f %10.2f\n\n", quantity, ticketPrice, totalTicketPrice);

        formattedReceipt += "\tScreen: " + movie.getScreen();
        formattedReceipt += "\n\tTime:   " + movie.getTime() + "\n\n";

        if (!cartSnacks.isEmpty()) {
            formattedReceipt += "\tSNACKS\n";
        }

        for (CartSnack cartSnack : cartSnacks) {
            int snackQuantity = cartSnack.getQuantity();
            String snackName = cartSnack.getName();
            double snackPrice = cartSnack.getPrice();
            double totalSnackPrice = snackQuantity * snackPrice;

            formattedReceipt += String.format("\t%-4d x %s\n", snackQuantity, snackName);
            formattedReceipt += String.format("\t%-4d x %-20.2f %10.2f\n\n", snackQuantity, snackPrice, totalSnackPrice);

            totalSnackCartPrice += totalSnackPrice;
        }

        double subtotal = totalTicketPrice + totalSnackCartPrice;
        double qst = 0.09975 * subtotal;
        double gst = 0.05 * subtotal;
        double total = subtotal + qst + gst;
        formattedReceipt += String.format("\t%-27s %10.2f\n", "Subtotal", subtotal);
        formattedReceipt += String.format("\t%-27s %10.2f\n", "QST", qst);
        formattedReceipt += String.format("\t%-27s %10.2f\n", "GST", gst);
        formattedReceipt += String.format("\t%-27s %10.2f", "Total", total);

        return formattedReceipt;
    }
}

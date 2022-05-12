package com.example.popnwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartActivity extends AppCompatActivity {

    Button returnButton, checkoutButton, editMovieQuantityButton;
    RecyclerView snackCartRecyclerView;
    TextView movieTicketTitleTextView, movieTicketTimeTextView, movieTicketScreenTextView, ticketQuantityTextView;

    ArrayList<CartSnack> cartSnacks = new ArrayList<>();
    SnackCartRecyclerViewAdapter snackCartRecyclerViewAdapter;

    CartDb cartDb;
    MovieDB movieDb;
    String cartId;

    int ticketQuantity = 0;
    String ticketTitle = "none";
    String selectedMovieId = "-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        returnButton = findViewById(R.id.returnButton);
        editMovieQuantityButton = findViewById(R.id.editMovieQuantityButton);
        checkoutButton = findViewById(R.id.checkoutButton);
        snackCartRecyclerView = findViewById(R.id.snackCartRecyclerView);

        ticketQuantityTextView = findViewById(R.id.ticketQuantityTextView);
        movieTicketTitleTextView = findViewById(R.id.movieTicketTitleTextView);
        movieTicketTimeTextView = findViewById(R.id.movieTicketTimeTextView);
        movieTicketScreenTextView = findViewById(R.id.movieTicketScreenTextView);

        cartDb = new CartDb(this);
        movieDb = new MovieDB(this);

        //getting the user id
        SharedPreferences sharedPreferences = getSharedPreferences("MY_APP_PREFERENCES", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "error");

        snackCartRecyclerView = findViewById(R.id.snackCartRecyclerView);
        snackCartRecyclerViewAdapter = new SnackCartRecyclerViewAdapter(cartSnacks, this);
        snackCartRecyclerView.setAdapter(snackCartRecyclerViewAdapter);
        snackCartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //displaying the cart
        displayCart(userId);
        getSnacks();

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editMovieQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText quantityInput = new EditText(CartActivity.this);
                quantityInput.setInputType(InputType.TYPE_CLASS_NUMBER);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CartActivity.this);
                alertDialog.setView(quantityInput)
                        .setTitle( "Edit Number of Tickets" )
                        .setPositiveButton( "Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String quantityStr = quantityInput.getText().toString();
                                int quantity = 1;

                                if (!quantityStr.isEmpty() || Integer.parseInt(quantityStr) > 0) {
                                    quantity = Integer.parseInt(quantityStr);
                                }

                                Cursor cartCursor = cartDb.getCart(userId);
                                String cartId = "";
                                if (cartCursor.moveToNext()) {
                                    cartId = cartCursor.getString(0);
                                }

                                //edit cart
                                if (cartDb.editCartQuantity(cartId, quantity) && !selectedMovieId.isEmpty()) {
                                    Toast.makeText(CartActivity.this, "Edited ticket quantity", Toast.LENGTH_SHORT).show();

                                    displayCart(userId);
                                }
                                else {
                                    Toast.makeText(CartActivity.this, "Could not edit ticket quantity", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } )
                        .setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        } );
                AlertDialog dialog = alertDialog.create();
                dialog.show();
            }
        });

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!selectedMovieId.isEmpty()) {
                    TextView summary = new TextView(CartActivity.this);
                    summary.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                    summary.setSingleLine(false);
                    summary.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                    summary.setTextAppearance(CartActivity.this, R.style.font);
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder( CartActivity.this );
                    alertDialog.setView(summary).setTitle( "Summary" );

                    double ticketPrice = 15.99;
                    double totalSnackCartPrice = 0;
                    double totalTicketPrice = ticketPrice * ticketQuantity;

                    String formattedReceipt = "\tTICKET\n";
                    formattedReceipt += String.format("\t%-4d x %s : %s\n", ticketQuantity, "Tickets for", ticketTitle);
                    formattedReceipt += String.format("\t%-4d x %-20.2f %10.2f\n\n", ticketQuantity, ticketPrice, totalTicketPrice);

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

                    summary.setText(formattedReceipt);

                    alertDialog.setPositiveButton( "Proceed to payment", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);

                            intent.putExtra("total", total);
                            intent.putExtra("cartId", cartId);

                            startActivity(intent);
                            finish();
                        }
                    })
                            .setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            } );
                    AlertDialog dialog = alertDialog.create();
                    dialog.show();
                }
                else {
                    Toast.makeText(CartActivity.this, "Please select a movie to watch", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void displayCart(String userId) {
        Cursor cartCursor = cartDb.getCart(userId);

        if (cartCursor.moveToNext()) { //only if there is a movie selected
            cartId = cartCursor.getString(0);

            ticketQuantity = cartCursor.getInt(1);
            ticketTitle = cartCursor.getString(3);

            ticketQuantityTextView.setText(ticketQuantity + "");
            movieTicketTitleTextView.setText(ticketTitle);

            selectedMovieId = cartCursor.getString(4);
        }

        showMovieData(selectedMovieId);
    }

    public void showMovieData(String selectedMovieId) {
        Cursor cursor = movieDb.getMovies();

        while (cursor.moveToNext()){
            //find the selected movie
            if (selectedMovieId.equals(cursor.getString(0))) {
                movieTicketTimeTextView.setText(cursor.getString(3));
                movieTicketScreenTextView.setText(cursor.getInt(2) + "");
                break;
            }
        }
    }

    public void getSnacks() {
        cartSnacks.clear();

        Cursor cursor = cartDb.getSnackCart(cartId);

        while (cursor.moveToNext()){
            String snackCartId = cursor.getString(0);
            String snackId = cursor.getString(4);
            int quantity = cursor.getInt(1);
            String cartId = cursor.getString(3);
            String snackName = cursor.getString(5);
            String snackImage = cursor.getString(6);
            double snackPrice = cursor.getDouble(7);
            String snackGenre = cursor.getString(8);

            cartSnacks.add(new CartSnack(snackCartId, quantity, cartId, snackId, snackName, snackImage, snackPrice, snackGenre));
        }

        //update the recyclerview
        snackCartRecyclerViewAdapter.notifyDataSetChanged();
    }
}
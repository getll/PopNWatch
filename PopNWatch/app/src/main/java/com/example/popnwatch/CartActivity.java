package com.example.popnwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CartActivity extends AppCompatActivity {

    Button returnButton, checkoutButton, editMovieQuantityButton;
    RecyclerView snackCartRecyclerView;
    TextView movieTicketTitleTextView, movieTicketTimeTextView, movieTicketScreenTextView, ticketQuantityTextView;
    CartDb cartDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        returnButton = findViewById(R.id.returnButton);
        editMovieQuantityButton = findViewById(R.id.editMovieQuantityButton);
        checkoutButton = findViewById(R.id.checkoutButton);

        ticketQuantityTextView = findViewById(R.id.ticketQuantityTextView);
        movieTicketTitleTextView = findViewById(R.id.movieTicketTitleTextView);
        movieTicketTimeTextView = findViewById(R.id.movieTicketTimeTextView);
        movieTicketScreenTextView = findViewById(R.id.movieTicketScreenTextView);

        snackCartRecyclerView = findViewById(R.id.snackCartRecyclerView);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editMovieQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
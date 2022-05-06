package com.example.popnwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        //show the movie and the snacks and the price in a scrollable text
        //payment button, which just sends to db, and edits cart to show them where to go
    }
}
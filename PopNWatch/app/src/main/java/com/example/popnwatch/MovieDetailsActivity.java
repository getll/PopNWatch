package com.example.popnwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MovieDetailsActivity extends AppCompatActivity {
    ImageView movieDetailImageView;
    TextView movieDetailTitleTextView, movieDetailPlotTextView, movieDetailTimeTextView, movieRuntimeTimeTextView;
    EditText ticketQuantityEditText;
    Button bookTicketButton;
    CartDb cartDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intent = getIntent();
        String movieId = intent.getStringExtra("movieId");
        String title = intent.getStringExtra("title");
        String img = intent.getStringExtra("img");
        String plot = intent.getStringExtra("plot");
        String time = intent.getStringExtra("time");
        String runtime = intent.getStringExtra("runtime") + " minutes";

        movieDetailImageView = findViewById(R.id.movieDetailImageView);
        movieDetailTitleTextView = findViewById(R.id.movieDetailTitleTextView);
        movieDetailPlotTextView = findViewById(R.id.movieDetailPlotTextView);
        movieDetailTimeTextView = findViewById(R.id.movieDetailTimeTextView);
        movieRuntimeTimeTextView = findViewById(R.id.movieRuntimeTimeTextView);
        ticketQuantityEditText = findViewById(R.id.ticketQuantityEditText);
        bookTicketButton = findViewById(R.id.bookTicketButton);

        Glide.with(this).asBitmap().load(img).into(movieDetailImageView);
        movieDetailTitleTextView.setText(title);
        movieDetailPlotTextView.setText(plot);
        movieDetailTimeTextView.setText(time);
        movieRuntimeTimeTextView.setText(runtime);

        cartDb = new CartDb(this);

        //get the current cart for the id
        SharedPreferences sharedPreferences = this.getSharedPreferences("MY_APP_PREFERENCES", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "error");

        bookTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quantityStr = ticketQuantityEditText.getText().toString();
                int quantity = 1;

                if (!quantityStr.isEmpty() || Integer.parseInt(quantityStr) > 0) {
                    quantity = Integer.parseInt(quantityStr);
                }

                Cursor cartCursor = cartDb.getCart(userId);
                String cartId = "";
                if (cartCursor.moveToNext()) {
                    cartId = cartCursor.getString(0);
                }

                //edit cart, not too sure if edit works. yes it does.
                if (cartDb.editCart(cartId, movieId, title, quantity, false)) {
                    finish();
                }
            }
        });
    }
}
package com.example.popnwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
//        cartDb.getCart();

        bookTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quantity = ticketQuantityEditText.getText().toString();
                if (quantity.isEmpty()) {
                    quantity = "0";
                }

                Integer.parseInt(quantity);

                //edit cart, not too sure if edit works

                finish();
            }
        });
    }
}
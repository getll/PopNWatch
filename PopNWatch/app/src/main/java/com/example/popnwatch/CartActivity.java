package com.example.popnwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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

    List<NewMovieDataDetail> movieData = new ArrayList<>();
    CartDb cartDb;
    MovieDB movieDb;
    String cartId;

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

        cartDb = new CartDb(this);
        movieDb = new MovieDB(this);

        new GetMovie().execute();
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
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CartActivity.this);
                alertDialog.setView(R.layout.add_to_cart_dialog)
                        .setTitle( "Edit Number of Tickets" )
                        .setPositiveButton( "Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

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

//                EditText qtyEditText = findViewById(R.id.qtyEditText);
//                qtyEditText.setText("1");
            }
        });

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getMovieData();
            }
        });
    }

    private void displayCart(String userId) {
        Cursor cartCursor = cartDb.getCart(userId);

        String selectedMovieId = "0";

        if (cartCursor.moveToNext()) { //only if there is a movie selected
            cartId = cartCursor.getString(0);
            ticketQuantityTextView.setText(cartCursor.getString(1));

            selectedMovieId = cartCursor.getString(3);
        }

        showMovieData(selectedMovieId);
    }

    private void getMovieData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MovieApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MovieApi api = retrofit.create(MovieApi.class);

        Call<NewMovieData> call = api.getNewMovies();

        call.enqueue(new Callback<NewMovieData>() {
            @Override
            public void onResponse(Call<NewMovieData> call, Response<NewMovieData> response) {
                movieData = response.body().getItems();
            }
            @Override
            public void onFailure(Call<NewMovieData> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showMovieData(String selectedMovieId) {
        Cursor cursor = movieDb.getMovies();

        System.out.println(movieData);

        while (cursor.moveToNext()){
            //find the selected movie
            if (selectedMovieId.equals(cursor.getString(0))) {

                for (NewMovieDataDetail movieDetail : movieData) {
                    if (movieDetail.getId().equals(cursor.getString(1))) {
                        //right movie from api
                        movieTicketTitleTextView.setText(movieDetail.getTitle());
                    }
                }
                movieTicketTimeTextView.setText(cursor.getString(3));
                movieTicketScreenTextView.setText(cursor.getInt(2) + "");
                break;
            }
        }
    }

    class GetMovie extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(MovieApi.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                MovieApi api = retrofit.create(MovieApi.class);

                Call<NewMovieData> call = api.getNewMovies();

                Response<NewMovieData> response = call.execute();
                movieData = response.body().getItems();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            //getting the user id
            SharedPreferences sharedPreferences = getSharedPreferences("MY_APP_PREFERENCES", Context.MODE_PRIVATE);
            String userId = sharedPreferences.getString("userId", "error");

            //displaying the cart
            displayCart(userId);
        }
    }
}
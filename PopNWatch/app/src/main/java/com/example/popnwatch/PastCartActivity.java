package com.example.popnwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PastCartActivity extends AppCompatActivity {

    TextView userIdTextView;
    RecyclerView recyclerView;
    ClientCartRecyclerViewAdapter clientCartRecyclerViewAdapter;

    List<Cart> carts = new ArrayList<>();
    List<SelectedMovie> movies = new ArrayList<>();
    CartDb cartDb;
    MovieDB movieDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_cart);

        SharedPreferences sharedPreferences = getSharedPreferences("MY_APP_PREFERENCES", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "error");

        cartDb = new CartDb(getApplicationContext());
        movieDb = new MovieDB(getApplicationContext());

        userIdTextView = findViewById(R.id.userIdTextView);
        recyclerView = findViewById(R.id.clientCartRecyclerView);

        clientCartRecyclerViewAdapter = new ClientCartRecyclerViewAdapter(carts, this);
        recyclerView.setAdapter(clientCartRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);


        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        getMovies();
        getCarts(userId);

        userIdTextView.setText("User ID: " + userId);
    }

    private void getMovies() {
        movies.clear();

        Cursor cursor = movieDb.getMovies();

        while (cursor.moveToNext()){
            String id = cursor.getString(0);
            String apiId = cursor.getString(1);
            int screen = cursor.getInt(2);
            String time = cursor.getString(3);

            movies.add(new SelectedMovie(id, apiId, screen, time));
        }
    }

    private void getCarts(String userId) {
        carts.clear();

        Cursor cursor = cartDb.getUserCarts(userId);

        while (cursor.moveToNext()) {
            String cartId = cursor.getString(0);
            int ticketQuantity = cursor.getInt(1);
            String ticketTitle = cursor.getString(3);

            String movieId = cursor.getString(2);

            SelectedMovie selectedMovie = new SelectedMovie();

            for (SelectedMovie movie : movies) {
                if (movie.getId().equals(movieId)) {
                    selectedMovie = movie;
                    break;
                }
            }

            List<CartSnack> cartSnacks = getSnacksCart(cartId);
            carts.add(new Cart(cartId, selectedMovie, ticketQuantity, ticketTitle, userId, true, cartSnacks));
        }

        clientCartRecyclerViewAdapter.notifyDataSetChanged();
    }

    public List<CartSnack> getSnacksCart(String cartId) {
        List<CartSnack> snacks = new ArrayList<>();

        Cursor cursor = cartDb.getSnackCart(cartId);

        while (cursor.moveToNext()){
            String snackCartId = cursor.getString(0);
            String snackId = cursor.getString(4);
            int quantity = cursor.getInt(1);
            String cartId2 = cursor.getString(3);
            String snackName = cursor.getString(5);
            String snackImage = cursor.getString(6);
            double snackPrice = cursor.getDouble(7);
            String snackGenre = cursor.getString(8);

            snacks.add(new CartSnack(snackCartId, quantity, cartId2, snackId, snackName, snackImage, snackPrice, snackGenre));
        }

        return snacks;
    }
}
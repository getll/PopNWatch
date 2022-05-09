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
        });
    }

    private void displayCart(String userId) {
        Cursor cartCursor = cartDb.getCart(userId);

        String selectedMovieId = "0";

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

//    class GetMovie extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            try {
//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl(MovieApi.BASE_URL)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//                MovieApi api = retrofit.create(MovieApi.class);
//
//                Call<NewMovieData> call = api.getNewMovies();
//
//                Response<NewMovieData> response = call.execute();
//                movieData = response.body().getItems();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void unused) {
//            super.onPostExecute(unused);
//
//            //getting the user id
//            SharedPreferences sharedPreferences = getSharedPreferences("MY_APP_PREFERENCES", Context.MODE_PRIVATE);
//            String userId = sharedPreferences.getString("userId", "error");
//
//            //displaying the cart
//            displayCart(userId);
//        }
//    }
}
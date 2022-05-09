package com.example.popnwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    ArrayList<String> ids = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> imgs = new ArrayList<>();
    ArrayList<String> price = new ArrayList<>();
    ArrayList<String> genre = new ArrayList<>();

    ArrayList<String> recipeNames = new ArrayList<>();
    ArrayList<String> recipeImgs = new ArrayList<>();
    ArrayList<String> recipeDesc = new ArrayList<>();
    ArrayList<String> recipeEta = new ArrayList<>();
    ArrayList<String> recipeGenre = new ArrayList<>();

    List<NewMovieDataDetail> movieData = new ArrayList<>();
    List<SelectedMovie> selectedMovies = new ArrayList<>();

    SnackDB snackDB;
    RecipesDb recipesDb;
    MovieDB movieDb;

    ClientSnackRecyclerViewAdapter snackAdapter;
    ClientRecipeRecyclerViewAdapter recipeAdapter;
    ClientMovieRecyclerViewAdapter clientMovieRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_client );

        recyclerView = findViewById( R.id.recyclerView2);
        snackDB = new SnackDB( this );
        recipesDb = new RecipesDb( this );
        movieDb = new MovieDB(this);

        getMovieData();
        snackAdapter = new ClientSnackRecyclerViewAdapter(ids, names,imgs, price, genre, ClientActivity.this);
        recipeAdapter = new ClientRecipeRecyclerViewAdapter(recipeNames, recipeImgs, recipeDesc, recipeEta, recipeGenre , this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.Logout:
                //destroy the user preferences, finish activity
                SharedPreferences sharedPreferences = getSharedPreferences("MY_APP_PREFERENCES", Context.MODE_PRIVATE);
                sharedPreferences.edit().remove("userId"). commit();
                Toast.makeText(this, " Logout is selected", Toast.LENGTH_SHORT).show();
                finish();
                break;

            case R.id.selectPage:
                Toast.makeText(this, "Select is selected", Toast.LENGTH_SHORT).show();
                break;

            case R.id.Movies:
                clientMovieRecyclerViewAdapter = new ClientMovieRecyclerViewAdapter(movieData, selectedMovies, this);
                recyclerView.setAdapter(clientMovieRecyclerViewAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(ClientActivity.this));
                getSelectedMovies();
                Toast.makeText(this, "Movies is selected", Toast.LENGTH_SHORT).show();
                break;

            case R.id.Snacks:
                recyclerView.setAdapter(snackAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(ClientActivity.this));
                getSnacks();
                //Toast.makeText(this, "Snacks selected" + names.get( 0 ), Toast.LENGTH_SHORT).show();
                break;

            case R.id.Recipes:
                recyclerView.setAdapter(recipeAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(ClientActivity.this));
                getRecipes();
                Toast.makeText(this, "Recipes is selected", Toast.LENGTH_SHORT).show();
                break;

            case R.id.Cart:
                Intent intent = new Intent(ClientActivity.this, CartActivity.class);
                startActivity(intent);
                overridePendingTransition(  R.anim.slide_in_right,
                        R.anim.slide_out_left);
                break;

            case R.id.Map:
                Intent map = new Intent(ClientActivity.this, MapActivity.class);
                startActivity(map);
                overridePendingTransition( R.anim.slide_in_right,
                        R.anim.slide_out_left);
                break;

            case R.id.PastCarts:
                Intent carts = new Intent(ClientActivity.this, PastCartActivity.class);
                startActivity(carts);
                overridePendingTransition(  R.anim.slide_in_right,
                        R.anim.slide_out_left);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public void getRecipes(){
        recipeNames.clear();
        recipeImgs.clear();
        recipeDesc.clear();
        recipeEta.clear();
        recipeGenre.clear();

        Cursor cursor = recipesDb.retrieveData();

        if(cursor.getCount() == 0){
            Toast.makeText(this,"No data", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                recipeNames.add(cursor.getString( 1 ));
                recipeImgs.add(cursor.getString(2));
                recipeDesc.add(cursor.getString(3));
                recipeEta.add(cursor.getString(4));
                recipeGenre.add(cursor.getString( 5));
            }
        }

        recipeAdapter.notifyDataSetChanged();
        //recipesDb.updateData("Fish N Chips", "a", "Bussin", "1h30", "Horror");
    }

    public void getSnacks(){
        ids.clear();
        names.clear();
        imgs.clear();
        price.clear();
        genre.clear();

        Cursor cursor = snackDB.retrieveData();

        if(cursor.getCount() == 0){
            Toast.makeText(this,"No data", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                ids.add(cursor.getString( 0 ));
                names.add(cursor.getString( 1 ));
                imgs.add(cursor.getString(2));
                price.add(cursor.getString(3));
                genre.add(cursor.getString(4));
            }
        }

        snackAdapter.notifyDataSetChanged();
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

    public void getSelectedMovies() {
        selectedMovies.clear();

        Cursor cursor = movieDb.getMovies();

        if(cursor.getCount() == 0){
            Toast.makeText(this,"No data", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()){
                String id = cursor.getString(0);
                String apiId = cursor.getString(1);
                int screen = cursor.getInt(2);
                String time = cursor.getString(3);

                selectedMovies.add(new SelectedMovie(id, apiId, screen, time));
            }
        }

        clientMovieRecyclerViewAdapter.notifyDataSetChanged();
    }
}
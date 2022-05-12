package com.example.popnwatch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminActivity extends AppCompatActivity {

    AdminSnackRecylerViewAdapter snackAdapter;
    AdminRecipeRecyclerViewAdapter recipeAdapter;
    MovieRecyclerAdapter movieRecyclerAdapter;
    SelectedMovieRecyclerViewAdapter selectedMovieRecyclerViewAdapter;

    ArrayList<String> ids = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> imgs = new ArrayList<>();
    ArrayList<String> price = new ArrayList<>();
    ArrayList<String> genre = new ArrayList<>();

    ArrayList<String> recipeIds = new ArrayList<>();
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

    FloatingActionButton mainFab, selectedMovieFab, movieFab, snackFab, recipeFab, cartFab;
    Button add;
    RecyclerView recyclerView;
    boolean isVisible;

    //Which db to manipulate
    String currentSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_admin );

        recyclerView = findViewById( R.id.recyclerView );
        mainFab = findViewById( R.id.mainFloatingActionButton );
        selectedMovieFab = findViewById( R.id.selectedMovieFloatingActionButton);
        movieFab = findViewById( R.id.movieFloatingActionButton );
        snackFab = findViewById( R.id.snackFloatingActionButton );
        recipeFab = findViewById( R.id.recipFloatingActionButton );
        cartFab = findViewById( R.id.cartFloatingActionButton );

        add = findViewById( R.id.addButton );
        isVisible = false;
        currentSelect = "";

        snackDB = new SnackDB( getApplicationContext() );
        recipesDb = new RecipesDb(getApplicationContext());
        movieDb = new MovieDB(getApplicationContext());

        snackAdapter = new AdminSnackRecylerViewAdapter(ids, names,imgs, price, genre, AdminActivity.this);
        recipeAdapter = new AdminRecipeRecyclerViewAdapter(recipeIds, recipeNames, recipeImgs, recipeDesc, recipeEta, recipeGenre, AdminActivity.this);
        selectedMovieRecyclerViewAdapter = new SelectedMovieRecyclerViewAdapter(movieData, selectedMovies, AdminActivity.this);

        getMovieData();

        mainFab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isVisible){
                    selectedMovieFab.show();
                    movieFab.show();
                    snackFab.show();
                    recipeFab.show();
                    cartFab.show();
                    isVisible = true;
                }
                else {
                    selectedMovieFab.hide();
                    movieFab.hide();
                    snackFab.hide();
                    recipeFab.hide();
                    cartFab.hide();
                    isVisible = false;
                }
            }
        } );


        cartFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, AdminPastCartsActivity.class);
                startActivity(intent);

                Toast.makeText(AdminActivity.this, "Showing receipts" + currentSelect, Toast.LENGTH_SHORT).show();
            }
        });

        movieFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentSelect = "Movie";
                changeButtonVisibility();

                movieRecyclerAdapter = new MovieRecyclerAdapter(movieData, AdminActivity.this);
                recyclerView.setAdapter(movieRecyclerAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(AdminActivity.this));
                Toast.makeText(AdminActivity.this, "Showing movies to show", Toast.LENGTH_SHORT).show();
            }
        } );

        selectedMovieFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentSelect = "SelectedMovie";
                changeButtonVisibility();

                selectedMovieRecyclerViewAdapter = new SelectedMovieRecyclerViewAdapter(movieData, selectedMovies, AdminActivity.this);
                recyclerView.setAdapter(selectedMovieRecyclerViewAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(AdminActivity.this));
                Toast.makeText(AdminActivity.this, "Showing added movies", Toast.LENGTH_SHORT).show();

                getSelectedMovies();
            }
        });

        snackFab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentSelect = "Snack";
                changeButtonVisibility();
                recyclerView.setAdapter(snackAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(AdminActivity.this));
                Toast.makeText(AdminActivity.this, "Showing snacks", Toast.LENGTH_SHORT).show();
                getSnacks();
            }
        } );

        recipeFab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentSelect = "Recipe";
                changeButtonVisibility();
                recyclerView.setAdapter(recipeAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(AdminActivity.this));
                Toast.makeText(AdminActivity.this, "Showing recipes", Toast.LENGTH_SHORT).show();
                getRecipes();
            }
        } );

        add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //This allows user to select which database they would want to mess with
                switch (currentSelect){
                    case "Snack":
                        Intent i = new Intent(AdminActivity.this, AddSnackActivity.class);
                        startActivityForResult(i, 1);
                        break;
                    case "Recipe":
                        Intent r = new Intent(AdminActivity.this, AddRecipeActivity.class);
                        startActivityForResult(r, 2);
                        break;
                    default:
                        Toast.makeText(AdminActivity.this, "Please select what will be viewed", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
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

        selectedMovieRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void getSnacks(){
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

    //cannot add a movie from the api page
    // or the selected movies (selected movies made from api)
    public void changeButtonVisibility() {
        if (currentSelect.equals("Movie") || currentSelect.equals("SelectedMovie")) {
            add.setClickable(false);
        }
        else {
            add.setClickable(true);
        }
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
                recipeIds.add(cursor.getString( 0 ));
                recipeNames.add(cursor.getString( 1 ));
                recipeImgs.add(cursor.getString(2));
                recipeDesc.add(cursor.getString(3));
                recipeEta.add(cursor.getString(4));
                recipeGenre.add(cursor.getString( 5));
            }
        }

        recipeAdapter.notifyDataSetChanged();
    }

    //to update the right recyclerview once an item gets added, either snack, recipe or movie
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) { //checking request and result code
                getSelectedMovies();
            }
            else if (requestCode == 1) {
                getSnacks();
            }
            else {
                getRecipes();
            }
        }
    }
}
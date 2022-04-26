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

    FloatingActionButton mainFab, selectedMovieFab, movieFab, snackFab, recipeFab;
    Button add, search;
    RecyclerView recyclerView;
    boolean isVisible;

    //Which db to manipulate
    String currentSelect;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if(requestCode == 1){
            recreate();
        }
    }

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

        add = findViewById( R.id.addButton );
        search = findViewById( R.id.searchButton );
        isVisible = false;

        snackDB = new SnackDB( this );
        recipesDb = new RecipesDb(this);
        movieDb = new MovieDB(this);

        snackAdapter = new AdminSnackRecylerViewAdapter(names,imgs, price, genre, AdminActivity.this);
        recipeAdapter = new AdminRecipeRecyclerViewAdapter(recipeNames, recipeImgs, recipeDesc, recipeEta, recipeGenre, AdminActivity.this);

        getMovieData();

        mainFab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isVisible){
                    selectedMovieFab.show();
                    movieFab.show();
                    snackFab.show();
                    recipeFab.show();
                    isVisible = true;
                }
                else {
                    selectedMovieFab.hide();
                    movieFab.hide();
                    snackFab.hide();
                    recipeFab.hide();
                    isVisible = false;
                }
            }
        } );

        movieFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentSelect = "Movie";
                changeButtonVisibility();
                Toast.makeText(AdminActivity.this, "Good " + currentSelect, Toast.LENGTH_SHORT).show();

                movieRecyclerAdapter = new MovieRecyclerAdapter(movieData, AdminActivity.this);
                recyclerView.setAdapter(movieRecyclerAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(AdminActivity.this));
            }
        } );

        selectedMovieFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentSelect = "SelectedMovie";
                changeButtonVisibility();
                Toast.makeText(AdminActivity.this, "weeewie " + currentSelect, Toast.LENGTH_SHORT).show();

                selectedMovieRecyclerViewAdapter = new SelectedMovieRecyclerViewAdapter(movieData, selectedMovies, AdminActivity.this);
                recyclerView.setAdapter(selectedMovieRecyclerViewAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(AdminActivity.this));

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
                getSnacks();

//                Activity activity = AdminActivity.this;
//                Intent i = new Intent(AdminActivity.this, AdminActivity.class);
//                activity.startActivityForResult(i, 1 );

                Toast.makeText(AdminActivity.this, "Good " + currentSelect, Toast.LENGTH_SHORT).show();
            }
        } );

        recipeFab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentSelect = "Recipe";
                changeButtonVisibility();
                recyclerView.setAdapter(recipeAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(AdminActivity.this));
                getRecipes();
                Toast.makeText(AdminActivity.this, "Good " + currentSelect, Toast.LENGTH_SHORT).show();
            }
        } );

        add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {



//                //This allows user to select which database they would want to mess with
               switch(currentSelect){
                   case "Movie":
                       //Test
                       Toast.makeText(AdminActivity.this, "Shit working " + currentSelect, Toast.LENGTH_SHORT).show();
                       break;

                   case "Snack":
                       Intent i = new Intent(AdminActivity.this, AddSnackActivity.class);
                       startActivity(i);
                       Toast.makeText(AdminActivity.this, "Shit working " + currentSelect, Toast.LENGTH_SHORT).show();
                       break;

                   case "Recipe":
                       Intent r = new Intent(AdminActivity.this, AddRecipeActivity.class);
                       startActivity(r);
                       Toast.makeText(AdminActivity.this, "Shit working " + currentSelect, Toast.LENGTH_SHORT).show();

                       Toast.makeText(AdminActivity.this, "Shit is working" + currentSelect, Toast.LENGTH_SHORT).show();
                       break;
                   default:
                       Toast.makeText(AdminActivity.this, "Please select what will be viewed" + currentSelect, Toast.LENGTH_SHORT).show();
                        break;
               }
            }
        } );


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
                names.add(cursor.getString( 1 ));
                imgs.add(cursor.getString(2));
                price.add(cursor.getString(3));
                genre.add(cursor.getString(4));
            }
        }

        snackAdapter.notifyDataSetChanged();
    }

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
                recipeNames.add(cursor.getString( 1 ));
                recipeImgs.add(cursor.getString(2));
                recipeDesc.add(cursor.getString(3));
                recipeEta.add(cursor.getString(4));
                recipeGenre.add(cursor.getString( 5));
            }
        }

        recipeAdapter.notifyDataSetChanged();
    }
}
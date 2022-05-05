package com.example.popnwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class ClientActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> imgs = new ArrayList<>();
    ArrayList<String> price = new ArrayList<>();
    ArrayList<String> genre = new ArrayList<>();

    ArrayList<String> recipeNames = new ArrayList<>();
    ArrayList<String> recipeImgs = new ArrayList<>();
    ArrayList<String> recipeDesc = new ArrayList<>();
    ArrayList<String> recipeEta = new ArrayList<>();
    ArrayList<String> recipeGenre = new ArrayList<>();

    SnackDB snackDB;
    RecipesDb recipesDb;

    ClientSnackRecyclerViewAdapter snackAdapter;
    ClientRecipeRecyclerViewAdapter recipeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_client );
        recyclerView = findViewById( R.id.recyclerView2);
        snackDB = new SnackDB( this );
        snackAdapter = new ClientSnackRecyclerViewAdapter(names,imgs, price, genre, ClientActivity.this);

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
                    Toast.makeText(this, " Logout is selected", Toast.LENGTH_SHORT).show();
                    break;

            case R.id.selectPage:
                Toast.makeText(this, "Select is selected", Toast.LENGTH_SHORT).show();
                break;

            case R.id.Movies:
                Toast.makeText(this, "Movies is selected", Toast.LENGTH_SHORT).show();
                break;

            case R.id.Snacks:
                recyclerView.setAdapter(snackAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(ClientActivity.this));
                getSnacks();
                //Toast.makeText(this, "Snacks selected" + names.get( 0 ), Toast.LENGTH_SHORT).show();
                break;

            case R.id.Recipes:
                Toast.makeText(this, "Recipes is selected", Toast.LENGTH_SHORT).show();
                break;

            case R.id.Cart:
                Intent intent = new Intent(ClientActivity.this, CartActivity.class);
                startActivity(intent);
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

//        recipeAdapter.notifyDataSetChanged();

        recipesDb.updateData("Fish N Chips", "a", "Bussin", "1h30", "Horror");
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
}
package com.example.popnwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    AdminRecyclerViewAdapter adminRecyclerViewAdapter;

    ArrayList<String> names = new ArrayList<>();
    ArrayList<byte[]> imgs = new ArrayList<>();
    ArrayList<String> price = new ArrayList<>();
    ArrayList<String> genre = new ArrayList<>();

    SnackDB snackDB;
    FloatingActionButton mainFab, movieFab, snackFab, recipeFab;
    Button add, search;
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
        movieFab = findViewById( R.id.movieFloatingActionButton );
        snackFab = findViewById( R.id.snackFloatingActionButton );
        recipeFab = findViewById( R.id.recipFloatingActionButton );
        add = findViewById( R.id.addButton );
        search = findViewById( R.id.searchButton );
        isVisible = false;

        snackDB = new SnackDB( this );


        mainFab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isVisible){
                    movieFab.show();
                    snackFab.show();
                    recipeFab.show();
                    isVisible = true;
                }else{
                    movieFab.hide();
                    snackFab.hide();
                    recipeFab.hide();
                    isVisible = false;
                }
            }
        } );

        movieFab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentSelect = "Movie";
                Toast.makeText(AdminActivity.this, "Good " + currentSelect, Toast.LENGTH_SHORT).show();
            }
        } );

        snackFab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentSelect = "Snack";
                adminRecyclerViewAdapter  = new
                        AdminRecyclerViewAdapter (names, imgs, price, genre, AdminActivity.this, currentSelect);
                recyclerView.setAdapter(adminRecyclerViewAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(AdminActivity.this));
                Toast.makeText(AdminActivity.this, "Good " + currentSelect, Toast.LENGTH_SHORT).show();
            }
        } );

        recipeFab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentSelect = "Recipe";
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
                       Toast.makeText(AdminActivity.this, "Shit is working" + currentSelect, Toast.LENGTH_SHORT).show();
                       break;
                   default:
                       Toast.makeText(AdminActivity.this, "Please select what will be viewed" + currentSelect, Toast.LENGTH_SHORT).show();
                        break;
               }
            }
        } );
    }

    public void getSnacks(){
        Cursor cursor = snackDB.retrieveData();

        if(cursor.getCount() == 0){
            Toast.makeText(this,"No data", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                names.add(cursor.getString( 1 ));
                imgs.add(cursor.getBlob(2));
                price.add(cursor.getString(3));
                genre.add(cursor.getString(4));
            }
        }
    }
}
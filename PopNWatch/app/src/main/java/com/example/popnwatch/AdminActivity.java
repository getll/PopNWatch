package com.example.popnwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AdminActivity extends AppCompatActivity {

    FloatingActionButton mainFab, movieFab, snackFab, recipeFab;
    boolean isVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_admin );

        mainFab = findViewById( R.id.mainFloatingActionButton );
        movieFab = findViewById( R.id.movieFloatingActionButton );
        snackFab = findViewById( R.id.snackFloatingActionButton );
        recipeFab = findViewById( R.id.recipFloatingActionButton );
        isVisible = false;

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
    }
}
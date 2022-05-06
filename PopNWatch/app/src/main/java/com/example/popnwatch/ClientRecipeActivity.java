package com.example.popnwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class ClientRecipeActivity extends AppCompatActivity {

    EditText name, imgUrl, desc, eta, genre;
    String recipeName, recipeImg, recipeDesc, recipeEta, recipeGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_client_recipe );
    }

    public void getIntentExtra(){
        if(getIntent().hasExtra("names") && getIntent().hasExtra("imgs") &&
                getIntent().hasExtra("desc") && getIntent().hasExtra("eta") && getIntent().hasExtra( "genre" )){

            recipeName = getIntent().getStringExtra("names") ;
            recipeImg = getIntent().getStringExtra("imgs");
            recipeDesc = getIntent().getStringExtra("desc");
            recipeEta = getIntent().getStringExtra("eta");
            recipeGenre = getIntent().getStringExtra("genre");

            name.setText(recipeName);
            imgUrl.setText(recipeImg);;
            desc.setText(recipeDesc);;
            eta.setText(recipeEta);;
            genre.setText(recipeGenre);;


        }else{
            Toast.makeText(this,"No data was passed", Toast.LENGTH_SHORT).show();
        }
    }
}
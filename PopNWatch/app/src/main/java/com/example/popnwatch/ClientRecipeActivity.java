package com.example.popnwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class ClientRecipeActivity extends AppCompatActivity {

    TextView name, desc, eta, genre;
    ImageView img;
    String recipeName, recipeImg, recipeDesc, recipeEta, recipeGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_client_recipe );
        name = findViewById( R.id.recipeTitleTextView );
        img = findViewById( R.id.recipeImageView );
        desc = findViewById( R.id.recipeDescTextView );
        eta = findViewById( R.id.recipeEtaTextView );
        getIntentExtra();
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
            Glide.with(this)
                    .asBitmap()
                    .load(recipeImg)
                    .into(img);
            desc.setText(recipeDesc);
            eta.setText(recipeEta);;



        }else{
            Toast.makeText(this,"No data was passed", Toast.LENGTH_SHORT).show();
        }
    }
}
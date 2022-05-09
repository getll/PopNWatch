package com.example.popnwatch;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class EditRecipeActivity extends AppCompatActivity {

    EditText name, imgUrl, desc, eta, genre;
    String id, recipeName, recipeImg, recipeDesc, recipeEta, recipeGenre;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_edit_recipe );
        name = findViewById( R.id.nameRecipeEditTextView);
        imgUrl = findViewById( R.id.imgURLTextPersonName );
        desc = findViewById( R.id.descriptionEditTextview );
        eta = findViewById( R.id.etaEditTextView );
        genre = findViewById( R.id.genreRecipeEditTextView );
        update = findViewById( R.id.updateRecipeButton );

        getIntentExtra();

        RecipesDb db = new RecipesDb( getApplicationContext() );

        update.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db.updateData(id, name.getText().toString().trim(), imgUrl.getText().toString().trim(),
                        desc.getText().toString().trim(), eta.getText().toString().trim(), genre.getText().toString().trim() )) {
                    Toast.makeText( EditRecipeActivity.this, "Recipe updated", Toast.LENGTH_SHORT ).show();
                    finish();
                }
            }
        });

//        List<String> recipeNames = new ArrayList<>();
//        Cursor cursor = db.retrieveData();
//
//        if(cursor.getCount() == 0){
//            Toast.makeText(this,"No data", Toast.LENGTH_SHORT).show();
//        }else{
//            while(cursor.moveToNext()){
//                recipeNames.add(cursor.getString( 1 ));
//            }
//        }
//
//        System.out.println(recipeNames);

    }

    public void getIntentExtra(){
        if(getIntent().hasExtra("names") && getIntent().hasExtra("imgs") &&
                getIntent().hasExtra("desc") && getIntent().hasExtra("eta") && getIntent().hasExtra( "genre" )){

            recipeName = getIntent().getStringExtra("names") ;
            id = getIntent().getStringExtra("id") ;
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
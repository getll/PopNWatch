package com.example.popnwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddRecipeActivity extends AppCompatActivity {

    EditText name,img, desc, eta, genre;
    Button addRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_recipe );

        name = findViewById(R.id.nameRecipeEditTextView);
        img = findViewById(R.id.imgURLTextPersonName);
        desc = findViewById(R.id.descriptionEditTextview);
        eta = findViewById(R.id.etaEditTextView);
        genre = findViewById(R.id.genreRecipeEditTextView);

        addRecipe = findViewById(R.id.addRecipeButton);

        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecipesDb db = new RecipesDb(AddRecipeActivity.this);
                db.addRecipe(name.getText().toString().trim(),img.getText().toString().trim(),desc.getText().toString().trim(),
                        eta.getText().toString().trim(),genre.getText().toString().trim());
                Toast.makeText(AddRecipeActivity.this, "Recipe Added Successfully ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
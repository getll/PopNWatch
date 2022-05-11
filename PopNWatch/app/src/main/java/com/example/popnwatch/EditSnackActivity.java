package com.example.popnwatch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditSnackActivity extends AppCompatActivity {

    EditText name, imgUrl, price, genre;
    String id, snackName, snackImg, snackPrice, snackGenre;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_edit_snack_ );

        name = findViewById( R.id.nameSnackEditTextView);
        price = findViewById( R.id.priceEditTextView);
        genre = findViewById( R.id.genreSnackEditTextView);
        imgUrl  = findViewById( R.id.imgUrlEditText );
        update = findViewById( R.id.updateSnackButton );

        getIntentExtra();

        SnackDB db = new SnackDB( getApplicationContext() );

        update.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    if (db.updateData(id,
                            name.getText().toString().trim(),
                            imgUrl.getText().toString().trim(),
                            Double.parseDouble(price.getText().toString().trim()),
                            genre.getText().toString().trim() )
                    ) {
                        Toast.makeText( EditSnackActivity.this, "Snack updated", Toast.LENGTH_SHORT ).show();
                        Intent resultIntent = new Intent();
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                }
            }
        });
    }

    public void getIntentExtra(){
        if(getIntent().hasExtra("names") && getIntent().hasExtra("imgs") &&
                getIntent().hasExtra("price") && getIntent().hasExtra("genre")){

            id = getIntent().getStringExtra("id") ;
            snackName = getIntent().getStringExtra("names") ;
            snackImg = getIntent().getStringExtra("imgs");
            snackPrice = getIntent().getStringExtra("price");
            snackGenre = getIntent().getStringExtra("genre");

            name.setText(snackName);
            imgUrl.setText(snackImg);;
            price.setText(snackPrice);;
            genre.setText(snackGenre);;


        }else{
            Toast.makeText(this,"No data was passed", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean validate() {
        if (
            name.getText().toString().isEmpty() ||
            price.getText().toString().isEmpty() ||
            genre.getText().toString().isEmpty() ||
            imgUrl.getText().toString().isEmpty()
        ) {
            Toast.makeText(EditSnackActivity.this, "Do not leave fields empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!Patterns.WEB_URL.matcher(imgUrl.getText().toString()).matches()) {
            Toast.makeText(EditSnackActivity.this, "Invalid url", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
package com.example.popnwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class AddSnackActivity extends AppCompatActivity {

    EditText name,imgUrl, price, genre;

    Button add, upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_snack );

        name = findViewById( R.id.nameSnackEditTextView);
        price = findViewById( R.id.priceEditTextView);
        genre = findViewById( R.id.genreSnackEditTextView);
        imgUrl  = findViewById( R.id.imgUrlEditText );
        add = findViewById( R.id.addSnackButton );



        add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SnackDB snackDB = new SnackDB(AddSnackActivity.this );


                snackDB.addSnack(name.getText().toString().trim(), imgUrl.getText().toString().trim(),
                        Double.parseDouble(price.getText().toString().trim()), genre.getText().toString().trim());

                Toast.makeText(AddSnackActivity.this, "Snack Added Successfully ", Toast.LENGTH_SHORT).show();
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        } );
    }
}
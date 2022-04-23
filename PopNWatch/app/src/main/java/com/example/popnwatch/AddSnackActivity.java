package com.example.popnwatch;

import androidx.appcompat.app.AppCompatActivity;

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

    EditText name, price, genre;
    ImageView img;
    Button add, upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_snack );

        name = findViewById( R.id.nameSnackEditTextView);
        price = findViewById( R.id.priceEditTextView);
        genre = findViewById( R.id.genreSnackEditTextView);
        img = findViewById(R.id.snackImageView );
        add = findViewById( R.id.addSnackButton );
        upload = findViewById( R.id.uploadSnackButton );



        add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SnackDB snackDB = new SnackDB(AddSnackActivity.this );
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.snacktest);
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                byte[] img = byteArray.toByteArray();

                snackDB.addSnack(name.getText().toString().trim(), img,
                        Double.parseDouble(price.getText().toString().trim()), genre.getText().toString().trim());
                Toast.makeText(AddSnackActivity.this, "Snack Added Successfully ", Toast.LENGTH_SHORT).show();
            }
        } );
    }
}
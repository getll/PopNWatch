package com.example.popnwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class EditMovieActivity extends AppCompatActivity {

    TextView displayTitleTextView;
    RadioButton morningRadioButton, afternoonRadioButton, eveningRadioButton;
    EditText screenEditText;
    Button editShowingButton;
    MovieDB movieDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);

        Intent intent = getIntent();

        String apiId = intent.getStringExtra("apiId");
        String title = intent.getStringExtra("title");

        String id = intent.getStringExtra("id");
        int screen = intent.getIntExtra("screen", 0);
        String time = intent.getStringExtra("time");

        displayTitleTextView = findViewById(R.id.displayTitleTextView);
        morningRadioButton = findViewById(R.id.morningRadioButton);
        afternoonRadioButton = findViewById(R.id.afternoonRadioButton);
        eveningRadioButton = findViewById(R.id.eveningRadioButton);
        screenEditText = findViewById(R.id.screenEditText);
        editShowingButton = findViewById(R.id.editShowingButton);

        displayTitleTextView.setText(title);
        screenEditText.setText("" + screen);

        if (time.equals("morning")) {
            morningRadioButton.setChecked(true);
        }
        else if (time.equals("afternoon")) {
            afternoonRadioButton.setChecked(true);
        }
        else {
            eveningRadioButton.setChecked(true);
        }

        movieDB = new MovieDB(getApplicationContext());

        editShowingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    int newScreen = Integer.parseInt(screenEditText.getText().toString().trim());
                    String newTime = "";

                    if (morningRadioButton.isChecked()) {
                        newTime = "morning";
                    }
                    else if (afternoonRadioButton.isChecked()) {
                        newTime = "afternoon";
                    }
                    else { //cannot unselect a radio button
                        newTime = "evening";
                    }

                    if (movieDB.editMovie(id, apiId, newScreen, newTime)) {
                        Toast.makeText(EditMovieActivity.this, "Movie edited", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(EditMovieActivity.this, "Movie could not be edited", Toast.LENGTH_SHORT).show();
                    }

                    Intent resultIntent = new Intent();
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
    }

    public boolean validate() {
        if (screenEditText.getText().toString().isEmpty()) {
            Toast.makeText(EditMovieActivity.this, "Please enter a screen", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
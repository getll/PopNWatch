package com.example.popnwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class AddMovieActivity extends AppCompatActivity {

    TextView titleTextView;
    EditText screenEditText;
    Button addShowingButton;
    MovieDB movieDB;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String title = intent.getStringExtra("title");

        movieDB = new MovieDB(this);

        titleTextView = findViewById(R.id.displayTitleTextView);
        screenEditText = findViewById(R.id.screenEditText);
        addShowingButton = findViewById(R.id.addShowingButton);
        radioGroup = findViewById(R.id.radioGroup);

        titleTextView.setText(title);

        addShowingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String showtime;
                int selectedRadio = radioGroup.getCheckedRadioButtonId();

                switch (selectedRadio) {
                    case (R.id.afternoonRadioButton):
                        showtime = "afternoon";
                        break;
                    case (R.id.eveningRadioButton):
                        showtime = "evening";
                        break;
                    default: //if morning and all other cases
                        showtime = "morning";
                        break;
                }

                int screen = Integer.parseInt(screenEditText.getText().toString());

                if (movieDB.addMovie(id, screen, showtime)) {
                    Toast.makeText(AddMovieActivity.this, ("movie added: " + title), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(AddMovieActivity.this, ("movie cannot be added: " + title), Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        });
    }
}
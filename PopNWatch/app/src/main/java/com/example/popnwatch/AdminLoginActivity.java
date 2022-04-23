package com.example.popnwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class AdminLoginActivity extends AppCompatActivity {

    EditText email, password;
    Button login, register, signIn, admin;
    AdminDB adminDB;
    SnackDB snack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);
        login = findViewById(R.id.loginButton);
        register = findViewById(R.id.registerButton);
        signIn = findViewById(R.id.signInButton);
        admin = findViewById(R.id.adminButton);

        adminDB = new AdminDB(this );
//        snack = new SnackDB( this );

        login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if(verifyCredentials() == true){
                    adminDB.addAdmin( "admin@hotmail.com", "admin" );
                    Toast.makeText(AdminLoginActivity.this, "Log in sucessful",Toast.LENGTH_SHORT ).show();
                    Intent i = new Intent(AdminLoginActivity.this, AdminActivity.class);
                    startActivity(i);
//                }else
//                    Toast.makeText(AdminLoginActivity.this, "Invalid Credentials",Toast.LENGTH_SHORT ).show();



            }
        } );
    }

    public boolean verifyCredentials(){
        Cursor cursor = adminDB.retrieveAdmin();

        if(cursor.getCount() == 0){
            Toast.makeText(this,"No data", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                String adminEmail = cursor.getString(1);
                String adminPassword = cursor.getString(2);
                if(adminEmail.equals( email.getText().toString().toString().trim())
                        && adminPassword.equals(password.getText().toString().trim()))
                {
                    return true;
                }
            }
        }
        return false;
    }
}
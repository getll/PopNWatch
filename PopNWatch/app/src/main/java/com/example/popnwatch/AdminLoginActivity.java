package com.example.popnwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLoginActivity extends AppCompatActivity {

    EditText email, password;
    Button login, register, signIn, admin;
    AdminDB adminDB;
    UserDB userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_admin_login );
        email = findViewById(R.id.cardNumberEditText);
        password = findViewById(R.id.passwordEditText);
        login = findViewById(R.id.loginButton);
        register = findViewById(R.id.registerButton);
        signIn = findViewById(R.id.signInButton);
        admin = findViewById(R.id.adminButton);

        adminDB = new AdminDB(getApplicationContext());
        userDB = new UserDB(getApplicationContext());

        //clears any currently existing user preferences, only use if want to change every restart
        //otherwise you can check for one and if there is, just keep going


        login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if(verifyCredentials() == true){
//                    //set the user preferences here for id
//                    Toast.makeText(AdminLoginActivity.this, "Log in sucessful",Toast.LENGTH_SHORT ).show();
//                    Intent i = new Intent(AdminLoginActivity.this, AdminActivity.class);

//                    startActivity(i);
//                }else
//                    Toast.makeText(AdminLoginActivity.this, "Invalid Credentials",Toast.LENGTH_SHORT ).show();

//                adminDB.addAdmin("admin@hotmail.com", "admin");
                Toast.makeText(AdminLoginActivity.this, "Log in sucessful",Toast.LENGTH_SHORT ).show();
//                Intent i = new Intent(AdminLoginActivity.this, AdminActivity.class);
                Intent i = new Intent(AdminLoginActivity.this, ClientActivity.class);

                //test to keep login stuff
//                userDB.addUser("dmar", "erm", "2003-06-06", "ermitano.den@gmail.com", "sauce");

                SharedPreferences sharedPreferences = getSharedPreferences("MY_APP_PREFERENCES", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userId", "1");
                editor.commit();

                startActivity(i);
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
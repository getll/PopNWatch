package com.example.popnwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button register, login, admin;
    AdminDB adminDB;
    UserDB userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        register = findViewById( R.id.registerButton );
        login = findViewById( R.id.signInButton );
        admin = findViewById( R.id.adminButton );

        adminDB = new AdminDB(getApplicationContext());
        userDB = new UserDB(getApplicationContext());

        register.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterFragment registerFragment = new RegisterFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, registerFragment);
                transaction.commit();
            }
        } );

        admin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminLoginFragment adminLoginFragmentFragment = new AdminLoginFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, adminLoginFragmentFragment);
                transaction.commit();
            }
        } );

        login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginFragment loginFragment = new LoginFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, loginFragment);
                transaction.commit();
            }
        } );
    }
}
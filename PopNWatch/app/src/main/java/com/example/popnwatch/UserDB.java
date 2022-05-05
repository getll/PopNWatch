package com.example.popnwatch;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class UserDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "PopNWatch.db";
    private static final int DB_VERSION = 1;

    private static final String USER = "User";
    private static final String USER_ID ="user_id";
    private static final String USER_FIRSTNAME="firstName";
    private static final String USER_LASTNAME="lastName";
    private static final String USER_BIRTHDAY="birthday";
    private static final String USER_EMAIL="email";
    private static final String USER_PASSWORD="password";

    public UserDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "Drop table if exists " + USER;
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }

//    public void addUser() {
//
//    }
//
//    public Cursor getAllUsers() {
//
//    }
}

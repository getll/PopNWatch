package com.example.popnwatch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;



public class AdminDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "PopNWatch.db";
    private static final int DB_VERSION = 1;

    private static final String ADMIN = "Admin";
    private static final String ADMIN_ID ="admin_id";
    private static final String ADMIN_EMAIL="email";
    private static final String ADMIN_PASSWORD="password";

    public AdminDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+ADMIN+" ("+ADMIN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ ADMIN_EMAIL + " VARCHAR(50), " + ADMIN_PASSWORD + " VARCHAR(25));";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String query = "Drop table if exists " + ADMIN;
        db.execSQL(query);
        onCreate(db);
    }

    public Cursor retrieveAdmin(){
        String query = "SELECT * FROM " + ADMIN;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}

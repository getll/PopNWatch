package com.example.popnwatch;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SnackDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "PopNWatch.db";
    private static final int DB_VERSION = 1;

    private static final String Snacks = "Snacks";
    private static final String ID ="snack_id";
    private static final String SNACK_NAME="name";
    private static final String SNACK_IMG="img";
    private static final String SNACK_PRICE="price";
    private static final String SNACK_GENRE="genre";

    public SnackDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+Snacks+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ SNACK_NAME + " VARCHAR(25), " + SNACK_IMG + " BLOB," + SNACK_PRICE + " DECIMAL(5,2)," + SNACK_GENRE + " VARCHAR(25));";
        db.execSQL( query );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String query = "Drop table if exists " + Snacks;
        db.execSQL(query);
        onCreate(db);
    }

    public boolean addSnack(String name, byte[] img, double price, String genre ) {

        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("img", img);
        contentValues.put("price", price);
        contentValues.put("genre", genre);
        database.insert("Snacks",null,contentValues);

        return true;

    }
}

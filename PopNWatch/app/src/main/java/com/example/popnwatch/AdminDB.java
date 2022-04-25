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

    private static final String Recipes = "Recipes";
    private static final String RECIPE_ID ="recipe_id";
    private static final String RECIPES_NAME="name";
    private static final String RECIPES_IMG="img";
    private static final String RECIPES_DESC="description";
    private static final String RECIPES_ETA="ETA";
    private static final String RECIPES_GENRE="genre";

    private static final String Snacks = "Snacks";
    private static final String SNACK_ID ="snack_id";
    private static final String SNACK_NAME="name";
    private static final String SNACK_IMG="img";
    private static final String SNACK_PRICE="price";
    private static final String SNACK_GENRE="genre";




    public AdminDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE "+ADMIN+" ("+ADMIN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ ADMIN_EMAIL + " VARCHAR(50), " + ADMIN_PASSWORD + " VARCHAR(25));";
        sqLiteDatabase.execSQL(query);
        query = "CREATE TABLE "+ Recipes +" ("+RECIPE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ RECIPES_NAME + " VARCHAR(50), " + RECIPES_IMG + " VARCHAR(255)," + RECIPES_DESC + " VARCHAR(255),"
                + RECIPES_ETA + " VARCHAR(25)," + RECIPES_GENRE + " VARCHAR(25));";

        sqLiteDatabase.execSQL(query);
        query = "CREATE TABLE "+ Snacks +" ("+SNACK_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ SNACK_NAME + " VARCHAR(25), " + SNACK_IMG + " VARCHAR(255)," + SNACK_PRICE + " DECIMAL(5,2)," + SNACK_GENRE + " VARCHAR(25));";
        sqLiteDatabase.execSQL( query );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "Drop table if exists " + ADMIN;
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }
    public boolean addAdmin(String email, String password ) {

        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        database.insert("Admin",null,contentValues);

        return true;

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

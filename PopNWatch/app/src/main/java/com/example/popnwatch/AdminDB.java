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

    private static final String User = "User";
    private static final String USER_ID ="user_id";
    private static final String USER_FIRSTNAME="firstName";
    private static final String USER_LASTNAME="lastName";
    private static final String USER_BIRTHDAY="birthday";
    private static final String USER_EMAIL="email";
    private static final String USER_PASSWORD="password";

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

    private static final String Movies = "Movies";
    private static final String MOVIE_ID ="movie_id";
    private static final String MOVIE_API_ID = "api_id";
    private static final String MOVIE_SCREEN = "screen";
    private static final String MOVIE_TIME = "time";

    private static final String Cart = "Cart";
    private static final String CART_ID = "cart_id";
    private static final String CART_MOVIE_ID = "movie_id";
    private static final String CART_QUANTITY = "quantity";
    private static final String CART_MOVIE_TITLE = "movie_title";
    private static final String CART_USER_ID = "user_id";
    private static final String CART_IS_PAID = "is_paid";

    private static final String Snack_Cart = "Snack_Cart";
    private static final String SNACK_CART_ID = "snack_cart_id";
    private static final String SNACK_CART_SNACK_ID = "snack_id";
    private static final String SNACK_CART_QUANTITY = "quantity";
    private static final String SNACK_CART_CART_ID = "cart_id";

    public AdminDB(@Nullable Context context) {

        super(context, DB_NAME, null, DB_VERSION);
        SQLiteDatabase db = getWritableDatabase();
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE IF NOT EXISTS "+ADMIN+" ("+ADMIN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ ADMIN_EMAIL + " VARCHAR(50), " + ADMIN_PASSWORD + " VARCHAR(25));";
        sqLiteDatabase.execSQL(query);

        query = "CREATE TABLE IF NOT EXISTS "+ User +" ("+USER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ USER_FIRSTNAME + " VARCHAR(50), " + USER_LASTNAME + " VARCHAR(50), "+ USER_BIRTHDAY + " DATE, "
                + USER_EMAIL + " VARCHAR(50), " + USER_PASSWORD + " VARCHAR(50));";
        sqLiteDatabase.execSQL(query);

        query = "CREATE TABLE IF NOT EXISTS "+ Recipes +" ("+RECIPE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ RECIPES_NAME + " VARCHAR(50), " + RECIPES_IMG + " VARCHAR(255)," + RECIPES_DESC + " VARCHAR(255),"
                + RECIPES_ETA + " VARCHAR(25)," + RECIPES_GENRE + " VARCHAR(25));";
        sqLiteDatabase.execSQL(query);

        query = "CREATE TABLE IF NOT EXISTS "+ Snacks +" ("+SNACK_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ SNACK_NAME + " VARCHAR(25), " + SNACK_IMG + " VARCHAR(255)," + SNACK_PRICE + " DECIMAL(5,2)," + SNACK_GENRE + " VARCHAR(25));";
        sqLiteDatabase.execSQL( query );

        query = "CREATE TABLE IF NOT EXISTS "+ Movies +" ("+MOVIE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," + MOVIE_API_ID + " VARCHAR(255), " + MOVIE_SCREEN + " INTEGER, " + MOVIE_TIME + " VARCHAR(255));";
        sqLiteDatabase.execSQL( query );

        query = "CREATE TABLE IF NOT EXISTS " + User + " (" +
                USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_FIRSTNAME + " VARCHAR(255), " +
                USER_LASTNAME + " VARCHAR(255), " +
                USER_BIRTHDAY + " DATE, " +
                USER_EMAIL + " VARCHAR(255), " +
                USER_PASSWORD + " VARCHAR(255) " +
                ");";
        sqLiteDatabase.execSQL( query );

        query = "CREATE TABLE IF NOT EXISTS " + Snack_Cart + " (" +
                SNACK_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SNACK_CART_QUANTITY + " INTEGER, " +
                SNACK_CART_SNACK_ID + " INTEGER, " +
                SNACK_CART_CART_ID + " INTEGER, " +
                "FOREIGN KEY(" + SNACK_CART_SNACK_ID + ") REFERENCES " + Snacks + "(" + SNACK_ID + "), " +
                "FOREIGN KEY(" + SNACK_CART_CART_ID + ") REFERENCES " + Cart + "(" + CART_ID + ")" +
                ");";
        sqLiteDatabase.execSQL( query );

        query = "CREATE TABLE IF NOT EXISTS " + Cart + " (" +
                CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CART_QUANTITY + " INTEGER, " +
                CART_IS_PAID + " INTEGER, " + //no boolean in sqlite
                CART_MOVIE_TITLE + " VARCHAR(255), " +
                CART_MOVIE_ID + " INTEGER, " +
                CART_USER_ID + " INTEGER, " +
                "FOREIGN KEY(" + CART_MOVIE_ID + ") REFERENCES " + Cart + "(" + CART_ID + "), " +
                "FOREIGN KEY(" + CART_USER_ID + ") REFERENCES " + User + "(" + USER_ID + ")" +
                ");";
        sqLiteDatabase.execSQL( query );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "Drop table if exists " + ADMIN;
        sqLiteDatabase.execSQL(query);

        query = "Drop table if exists " + Recipes;
        sqLiteDatabase.execSQL(query);

        query = "Drop table if exists " + Snacks;
        sqLiteDatabase.execSQL(query);

        query = "Drop table if exists " + Movies;
        sqLiteDatabase.execSQL(query);

        query = "Drop table if exists " + User;
        sqLiteDatabase.execSQL(query);

        query = "Drop table if exists " + Snack_Cart;
        sqLiteDatabase.execSQL(query);

        query = "Drop table if exists " + Cart;
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

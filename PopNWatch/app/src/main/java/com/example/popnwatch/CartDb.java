package com.example.popnwatch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CartDb extends SQLiteOpenHelper {
    private static final String DB_NAME = "PopNWatch.db";
    private static final int DB_VERSION = 1;

    private static final String Snacks = "Snacks";
    private static final String SNACK_ID ="snack_id";
    private static final String SNACK_NAME="name";
    private static final String SNACK_IMG="img";
    private static final String SNACK_PRICE="price";
    private static final String SNACK_GENRE="genre";

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

    public CartDb(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        SQLiteDatabase db = getWritableDatabase();
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "Drop table if exists " + Snack_Cart;
        sqLiteDatabase.execSQL(query);

        query = "Drop table if exists " + Cart;
        sqLiteDatabase.execSQL(query);

        onCreate(sqLiteDatabase);
    }

//    //Cart operations
    public Cursor getCart(String userId) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        if (!checkExistingCart(userId)) {
            //creates empty cart if unpaid cart does not exist
            ContentValues contentValues = new ContentValues();
            contentValues.put(CART_QUANTITY, 0);
            contentValues.put(CART_MOVIE_ID, "");
            contentValues.put(CART_MOVIE_TITLE, "");
            contentValues.put(CART_USER_ID, userId); //this from user
            contentValues.put(CART_IS_PAID, 0); //cart is unpaid

            sqLiteDatabase.insert(Cart, null, contentValues);
        }

        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + Cart + " Where " + CART_IS_PAID + " = ? AND " + CART_USER_ID + " = ?", new String[] {"0", userId});

        return cursor;
    }

    //checking for unpaid cart
    public boolean checkExistingCart(String userId) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + Cart + " Where " + CART_IS_PAID + " = ? AND " + CART_USER_ID + " = ?", new String[] {"0", userId});

        if (cursor.getCount() == 0)
            return false; //false if they do not have
        return true; //true if they do have
    }

    //admin side
    public Cursor getAllCarts() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + Cart +
                " INNER JOIN " + Snack_Cart + " on " + Cart + "." + CART_ID + " = " + Snack_Cart + "." + SNACK_CART_CART_ID + " Where " + CART_IS_PAID + " = ?", new String[] {"1"});

        return cursor;
    }

    public Cursor getUserCarts(String userId) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + Cart +
                " INNER JOIN " + Snack_Cart + " on " + Cart + "." + CART_ID + " = " + Snack_Cart + "." + SNACK_CART_CART_ID +
                " Where " + CART_IS_PAID + " = ? AND " + CART_USER_ID + " = ?", new String[] {"1", userId});

        return cursor;
    }

    public boolean editCart(String id, String movieId, String movieTitle, int quantity, boolean isPaid) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CART_MOVIE_ID, movieId);
        contentValues.put(CART_MOVIE_TITLE, movieTitle);
        contentValues.put(CART_QUANTITY, quantity);
        contentValues.put(CART_IS_PAID, (isPaid) ? 1 : 0);

        long result = sqLiteDatabase.update(Cart, contentValues, CART_ID + " = ? ", new String[] {id});

        if (result == 1)
            return true;

        return false;
    }

    public boolean checkoutCart(String id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CART_IS_PAID, 1);

        long result = sqLiteDatabase.update(Cart, contentValues, CART_ID + " = ? ", new String[] {id});

        if (result == 1)
            return true;

        return false;
    }

    //Snack cart operations
    public Cursor getSnackCart(String cartId) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + Snack_Cart +
                " INNER JOIN " + Snacks + " on " + Snacks + "." + SNACK_ID + " = " + Snack_Cart + "." + SNACK_CART_SNACK_ID +
                " WHERE " + CART_ID + " = ?", new String[] {cartId});

        return cursor;
    }

    public boolean createSnackCart(String snackId, int quantity, String cartId) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SNACK_CART_SNACK_ID, snackId);
        contentValues.put(SNACK_CART_QUANTITY, quantity);
        contentValues.put(SNACK_CART_CART_ID, cartId);

        sqLiteDatabase.insert(Snack_Cart, null, contentValues);

        return true;
    }

    public boolean editSnackCart(String id, String snackId, int quantity, String cartId) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SNACK_CART_SNACK_ID, snackId);
        contentValues.put(SNACK_CART_QUANTITY, quantity);
        contentValues.put(SNACK_CART_CART_ID, cartId);

        long result = sqLiteDatabase.update(Snack_Cart, contentValues, SNACK_CART_ID + " = ? ", new String[] {id});

        if (result == 1)
            return true;

        return false;
    }

    public boolean deleteSnackCart(String id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        long result = sqLiteDatabase.delete(Snack_Cart, SNACK_CART_ID + " = ? ", new String[] {id});

        if (result == 1)
            return true;

        return false;
    }
}

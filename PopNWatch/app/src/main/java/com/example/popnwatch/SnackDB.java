package com.example.popnwatch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SnackDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "PopNWatch.db";
    private static final int DB_VERSION = 1;
    Context context;

    private static final String Snacks = "Snacks";
    private static final String ID ="snack_id";
    private static final String SNACK_NAME="name";
    private static final String SNACK_IMG="img";
    private static final String SNACK_PRICE="price";
    private static final String SNACK_GENRE="genre";

    public SnackDB(@Nullable Context context) {

        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        String query = "CREATE TABLE "+ Snacks +" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ SNACK_NAME + " VARCHAR(25), " + SNACK_IMG + " VARCHAR(255)," + SNACK_PRICE + " DECIMAL(5,2)," + SNACK_GENRE + " VARCHAR(25));";
//        sqLiteDatabase.execSQL( query );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "Drop table if exists " + Snacks;
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
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



    public void deleteData(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(Snacks, "name=?", new String[] {name});
        if(result == -1){
            Toast.makeText(context, "Failed",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Record deleted successfully",Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor retrieveData(){
        String query = "SELECT * FROM " + Snacks;
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}

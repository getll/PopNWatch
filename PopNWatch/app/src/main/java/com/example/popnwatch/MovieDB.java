package com.example.popnwatch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MovieDB extends SQLiteOpenHelper {
    private static final String DB_NAME = "PopNWatch.db";
    private static final int DB_VERSION = 1;
    Context context;

    private static final String Movies = "Movies";
    private static final String MOVIE_ID = "movie_id";
    private static final String MOVIE_API_ID = "api_id";
    private static final String MOVIE_SCREEN = "screen";
    private static final String MOVIE_TIME = "time";

    public MovieDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "Drop table if exists " + Movies;
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }

    public Cursor getMovies() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + Movies, null);

        return cursor;
    }

    public boolean addMovie(String apiId, int screen, String time) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MOVIE_API_ID, apiId);
        contentValues.put(MOVIE_SCREEN, screen);
        contentValues.put(MOVIE_TIME, time);

        long result = sqLiteDatabase.insert(Movies, null, contentValues);
        sqLiteDatabase.close();

        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean editMovie(String id, String apiId, int screen, String time) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MOVIE_API_ID, apiId);
        contentValues.put(MOVIE_SCREEN, screen);
        contentValues.put(MOVIE_TIME, time);

        long result = sqLiteDatabase.update(Movies, contentValues, MOVIE_ID + " = ? ", new String[] {id});
        sqLiteDatabase.close();

        if (result < 1)
            return false;
        else
            return true;
    }

    public int removeMovie(String id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(Movies, MOVIE_ID + " = ? ", new String[] {id});
    }
}

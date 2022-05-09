package com.example.popnwatch;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

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

    Context mContext;

    public UserDB(@Nullable Context context) {

        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
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

    public boolean addUser(String firstName, String lastName, String birthday, String email, String password) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_FIRSTNAME, firstName);
        contentValues.put(USER_LASTNAME, lastName);

        //birthday in yyyy-mm-dd
        contentValues.put(USER_BIRTHDAY, birthday);
        contentValues.put(USER_EMAIL, email);
        contentValues.put(USER_PASSWORD, password);
        database.insert("User",null,contentValues);

        return true;
    }

    public boolean verifyCredentials(String email, String password){

        String query = "SELECT * FROM " + USER + " WHERE " + USER_EMAIL + "='" + email + "' AND " + USER_PASSWORD+"='"+password+"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }

        if(cursor.getCount() == 0){
            Toast.makeText(mContext,"Either the user does not exist or password is incorrect", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                String userEmail = cursor.getString(4);
                String userPassword = cursor.getString(5);
                String userId = cursor.getString(0);
                if(userEmail.equals( email) && userPassword.equals(password))
                {
                    SharedPreferences sharedPreferences = mContext.getSharedPreferences("MY_APP_PREFERENCES", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userId", userId);
                    editor.commit();

                    return true;
                }
            }

        }

        return false;
    }
}

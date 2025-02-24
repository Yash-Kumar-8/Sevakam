package com.example.sevakam.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelperRegister extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "login.db";

    public DatabaseHelperRegister(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "create table users(name text, phone text, email text Primary Key, password text)";
        db.execSQL(query1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists users");
        onCreate(db);
    }

    public boolean insertData (String name, String phone, String email, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("phone", phone);
        cv.put("email", email);
        cv.put("password", password);
        long result = myDB.insert("users", null, cv);
        if (result == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean checkmail(String email){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where email = ?", new String[]{email});
        if (cursor.getCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean checkUser(String mail, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where email = ? and password = ?", new String[]{mail , password});
        if (cursor.getCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean updatePassword (String email, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("password", password);
        long result = myDB.update("users", cv, "email = ?", new String[] {email});
        if (result == -1){
            return false;
        }
        else {
            return true;
        }
    }
}

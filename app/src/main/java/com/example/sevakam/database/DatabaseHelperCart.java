package com.example.sevakam.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class DatabaseHelperCart extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "cart.db";
    private static int DATABASE_VERSION = 1;  // Increase version for schema change
    private static final String TABLE_NAME = "MyCart";
    private static final String COLUMN_ID = "cart_id";
    private static final String COLUMN_SERVICE_ID = "service_id";
    private static final String COLUMN_NAME = "service_name";
    private static final String COLUMN_COST = "service_cost";
    private static final String COLUMN_DETAIL = "service_detail";
    private static final String COLUMN_IMAGE = "service_image";
    private static final String COLUMN_USERMAIL = "user_mail";

    public DatabaseHelperCart(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_SERVICE_ID + " TEXT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_COST + " TEXT, "
                + COLUMN_DETAIL + " TEXT, "
                + COLUMN_IMAGE + " BLOB, "
                + COLUMN_USERMAIL + " TEXT);";
        db.execSQL(query);
    }

    public void addCart(String service_id , String name, String cost, String detail, Bitmap imageBitmap, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        if (service_id == null || service_id.trim().isEmpty() || name == null || name.trim().isEmpty() || cost == null || cost.trim().isEmpty() || detail == null || detail.isEmpty() || imageBitmap == null || email == null || email.isEmpty()) {
            Toast.makeText(context, "All fields including image are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert Bitmap to ByteArray
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imageBytes = stream.toByteArray();

        cv.put(COLUMN_SERVICE_ID, service_id);
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_COST, cost);
        cv.put(COLUMN_DETAIL, detail);
        cv.put(COLUMN_IMAGE, imageBytes);
        cv.put(COLUMN_USERMAIL, email);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }


    public Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor readSomeData(String userEmail) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERMAIL + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, new String[]{userEmail});
        }
        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

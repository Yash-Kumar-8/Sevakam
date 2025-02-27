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

public class DatabaseHelperService extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Service.db";
    private static int DATABASE_VERSION = 1;  // Increase version for schema change
    private static final String TABLE_NAME = "service";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "service_name";
    private static final String COLUMN_CATEGORY = "category_name";
    private static final String COLUMN_COST = "service_cost";
    private static final String COLUMN_DETAIL = "service_detail";
    private static final String COLUMN_IMAGE = "_image";

    public DatabaseHelperService(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_CATEGORY + " TEXT, "
                + COLUMN_COST + " TEXT, "
                + COLUMN_DETAIL + " TEXT, "
                + COLUMN_IMAGE + " BLOB);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addService(String name, String cost, String category, String detail, Bitmap imageBitmap) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        if (name == null || name.trim().isEmpty() || cost == null || cost.trim().isEmpty() || category == null || category.trim().isEmpty() || detail == null || detail.isEmpty() || imageBitmap == null) {
            Toast.makeText(context, "All fields including image are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert Bitmap to ByteArray
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imageBytes = stream.toByteArray();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_COST, cost);
        cv.put(COLUMN_CATEGORY, category);
        cv.put(COLUMN_DETAIL, detail);
        cv.put(COLUMN_IMAGE, imageBytes);

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

    public void updateData(String row_id, String name, String cost, String detail){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues CV = new ContentValues();
        CV.put(COLUMN_NAME, name);
        CV.put(COLUMN_COST, cost);
        CV.put(COLUMN_DETAIL, detail);

        long result = db.update(TABLE_NAME, CV, "_id=?", new String[]{row_id});

        if (result == -1){
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public String getServiceDetailById(int serviceId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_DETAIL + " FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?", new String[]{String.valueOf(serviceId)});

        if (cursor.moveToFirst()) {
            String detail = cursor.getString(0);
            cursor.close();
            return detail;
        } else {
            cursor.close();
            return "Service details not available";
        }
    }

}

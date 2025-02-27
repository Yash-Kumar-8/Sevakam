package com.example.sevakam.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelperCart extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "cart.db";
    private static int DATABASE_VERSION = 1;  // Increase version for schema change
    private static final String TABLE_NAME = "MyCart";
    private static final String COLUMN_ID = "cart_id";
    private static final String COLUMN_SERVICE_ID = "cart_id";
    private static final String COLUMN_NAME = "service_name";
    private static final String COLUMN_COST = "service_cost";
    private static final String COLUMN_DETAIL = "service_detail";
    private static final String COLUMN_IMAGE = "service_image";


    public DatabaseHelperCart(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

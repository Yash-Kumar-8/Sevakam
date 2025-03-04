package com.example.sevakam.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelperServiceOrderRequest extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "orders.db";
    private static int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "Orders";

    private static final String COLUMN_ID = "order_id";
    private static final String COLUMN_STATUS = "order_status";
    private static final String COLUMN_PERSON = "order_person";
    private static final String COLUMN_SERVICE_ID = "service_id";
    private static final String COLUMN_USERMAIL = "user_mail";
    private static final String COLUMN_NAME = "service_name";
    private static final String COLUMN_AREA = "area_name";
    private static final String COLUMN_LANDMARK = "landmark";

    public DatabaseHelperServiceOrderRequest(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_SERVICE_ID + " TEXT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_AREA + " TEXT, "
                + COLUMN_LANDMARK + " TEXT, "
                + COLUMN_USERMAIL + " TEXT, "
                + COLUMN_STATUS + " TEXT , "  // Default status
                + COLUMN_PERSON + " TEXT );";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void placeOrder(String id, String name, String area, String landmark, String email ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SERVICE_ID, id);
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_AREA, area);
        cv.put(COLUMN_LANDMARK, landmark);
        cv.put(COLUMN_USERMAIL, email);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1){
            Toast.makeText(context, "Failed Add Data", Toast.LENGTH_SHORT).show();
        }else{
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

    public void updateOrder(String orderId, String status, String person) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_STATUS, status);
        cv.put(COLUMN_PERSON, person);

        long result = db.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{orderId});
        if (result == -1) {
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
        }
    }

}

package com.jaspreet.lab2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String FOOD_ORDERS_TABLE = "FOOD_ORDERS_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_FOOD_TYPE = "FOOD_TYPE";
    public static final String COLUMN_QUANTITY = "QUANTITY";
    public static final String COLUMN_PICKUP_DATE = "PICKUP_DATE";
    public static final String COLUMN_PHONE_NUMBER = "PHONE_NUMBER";
    public static final String COLUMN_ADDRESS = "ADDRESS";
    public static final String COLUMN_LOCATION= "LOCATION";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "food_table.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + FOOD_ORDERS_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                " " + COLUMN_NAME + " TEXT, " + COLUMN_FOOD_TYPE + " TEXT, " + COLUMN_QUANTITY + " INTEGER, " + COLUMN_PICKUP_DATE +
                " TEXT, " + COLUMN_PHONE_NUMBER + " TEXT, " + COLUMN_ADDRESS +" TEXT, "+ COLUMN_LOCATION + " BLOB )";

        sqLiteDatabase.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public boolean addOne(ElementModel elementModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME,elementModel.getName());
        cv.put(COLUMN_FOOD_TYPE,elementModel.getFoodType());
        cv.put(COLUMN_QUANTITY,elementModel.getQuantity());
        cv.put(COLUMN_PICKUP_DATE,elementModel.getPickupDate());
        cv.put(COLUMN_PHONE_NUMBER,elementModel.getPhoneNumber());
        cv.put(COLUMN_ADDRESS,elementModel.getAddress());
        cv.put(COLUMN_LOCATION,ParcelableUtil.marshall(elementModel.getLocation()));
        long insert = db.insert(FOOD_ORDERS_TABLE,null,cv);
        if(insert==-1)
            return false;
        return true;
    }

    public boolean deleteOne(ElementModel elementModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + FOOD_ORDERS_TABLE + " WHERE " + COLUMN_ID + " = " + elementModel.getId();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst())
            return true;
        else
            return false;
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + FOOD_ORDERS_TABLE;
        db.execSQL(query);
    }

    public List<ElementModel> getAll(){
        List<ElementModel> returnList = new ArrayList<>();
        String query = "SELECT * FROM " + FOOD_ORDERS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()){
            do {
                int elementId = c.getInt(0);
                String name = c.getString(1);
                String type = c.getString(2);
                int qty = c.getInt(3);
                String date = c.getString(4);
                String number = c.getString(5);
                String address = c.getString(6);
                Location location = ParcelableUtil.unmarshall(c.getBlob(7), Location.CREATOR);

                ElementModel elementModel = new ElementModel(elementId,name,type,qty,date,number,address,location);
                returnList.add(elementModel);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return returnList;
    }
}

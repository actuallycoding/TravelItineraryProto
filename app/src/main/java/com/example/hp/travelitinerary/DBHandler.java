package com.example.hp.travelitinerary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    //Database Version
    private static final int DATABASE_VERSION = 1;
    //Database Name
    private static final String DATABASE_NAME = "database";
    //Table Name
    private static final String TABLE_ITEMS = "items";
    //Table Columns Names
    private static final String KEY_ID = "id";
    private static final String KEY_DAY = "day";
    private static final String KEY_NAME = "name";
    private static final String KEY_NOTES = "notes";
    private static final String KEY_IMAGE = "image";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_ITEMS + "( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_DAY + " INTEGER, " + KEY_NAME + " TEXT," + KEY_NOTES + " TEXT," +
                KEY_IMAGE + " BLOB " + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
// Creating tables again
        onCreate(db);
    }
// Insert Item

    public void insertItem(int day, String name, String description, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DAY, day);
        values.put(KEY_NAME, name);
        values.put(KEY_NOTES, description);
        values.put(KEY_IMAGE, image);
        db.insert(TABLE_ITEMS, null, values);
        db.close();
    }

    //Add new Item
    public void addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DAY, item.getDay());
        values.put(KEY_NAME, item.getItemName());
        values.put(KEY_NOTES, item.getItemDescription());
        values.put(KEY_IMAGE, item.getImage());
        db.insert(TABLE_ITEMS, null, values);
        db.close();
    }

    //apa ini get shit
    public Item getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ITEMS, new String[]{KEY_ID, KEY_DAY, KEY_NAME, KEY_NOTES, KEY_IMAGE}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Item it = new Item(Integer.parseInt(cursor.getString(0)),
                cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getBlob(4));
        //stupid shit return item thingy
        return it;
    }

    //wowe now it's everything else nice
    //getting all the items lmao
    public ArrayList<Item> getAllItems() {
        ArrayList<Item> itemList = new ArrayList<Item>();
        //Selecting all the bs
        String selectQuery = "SELECT * FROM " + TABLE_ITEMS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        //looping shit
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item(Integer.parseInt(cursor.getString(0)), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getBlob(4));
                //add shit to list
                itemList.add(item);

            } while (cursor.moveToNext());
        }
        return itemList;

    }

    public void editItem(int id, Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put(KEY_ID, id);
        data.put(KEY_DAY, item.getDay());
        data.put(KEY_NAME, item.getItemName());
        data.put(KEY_NOTES, item.getItemDescription());
        data.put(KEY_IMAGE, item.getImage());
        db.update(TABLE_ITEMS, data, KEY_ID + "=" + id, null);

    }

    public void deleteItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEMS, KEY_ID + " = ?",
                new String[]{String.valueOf(item.getId())});
        db.close();
    }

    public void removeAllItems() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_ITEMS);
        db.close();
    }

}


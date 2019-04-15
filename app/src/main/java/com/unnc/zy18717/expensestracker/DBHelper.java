package com.unnc.zy18717.expensestracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
        Log.d("ae3cw3", "DBHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("ae3cw3", "onCreate");
        // TODO Auto-generated method stub
        db.execSQL("CREATE TABLE expenses (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "date VARCHAR(128) NOT NULL," +
                "category VARCHAR(128) NOT NULL," +
                "amount VARCHAR(128) NOT NULL" +
                ");");
        Log.e("jiao", "ddddd");
        /*db.execSQL("CREATE TABLE animals (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "name VARCHAR(128) NOT NULL," +
                "kind VARCHAR(128) NOT NULL," +
                "food VARCHAR(128) NOT NULL" +
                ");");*/

        db.execSQL("INSERT INTO expenses (date, category, amount) VALUES ('Date', 'Category', 'Amount');");
//        db.execSQL("INSERT INTO expenses (date, category, amount) VALUES ('4', '5', '6');");

//        db.execSQL("INSERT INTO animals (name, kind, food) VALUES ('scribble', 'cat', 'whiskers');");
//        db.execSQL("INSERT INTO animals (name, kind, food) VALUES ('skippy', 'kangaroo', 'grass');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS expenses");
//        db.execSQL("DROP TABLE IF EXISTS animals");
//        db.execSQL("DROP TABLE IF EXISTS food");
        onCreate(db);
    }
}
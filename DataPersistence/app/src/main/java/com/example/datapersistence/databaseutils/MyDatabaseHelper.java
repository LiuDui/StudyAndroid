package com.example.datapersistence.databaseutils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private String SQLOnCreate[] = null;

    private Context mContext = null;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    public void setSQLOnCreate(String[] SQLOnCreate) {
        this.SQLOnCreate = SQLOnCreate;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (int i = 0; i < SQLOnCreate.length; i++) {
            db.execSQL(SQLOnCreate[i]);
            Log.d("MyDatabaseHelper SQL", "onCreate");
        }
        Log.d("MyDatabaseHelper SQL", "onCreate Success");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Book");
        db.execSQL("drop table if exists Category");
        Log.d("MyDatabaseHelper SQL", "drop Success");

        onCreate(db);
    }
}

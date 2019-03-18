package com.example.datapersistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.datapersistence.databaseutils.MyDatabaseHelper;

public class DatabaseActivity extends AppCompatActivity {

    private MyDatabaseHelper myDatabaseHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_layout);

        myDatabaseHelper = new MyDatabaseHelper(this, "BookStore.db", null, 3);
        String sql = "create table book(id integer primary key autoincrement, author text, price real, pages integer, name text)";
        String sqlCategory = "create table Category(id integer primary key autoincrement, category_name text, category_code integer)";
        myDatabaseHelper.setSQLOnCreate(new String[]{sql, sqlCategory});
        Button btnDatabaseCreate = findViewById(R.id.btn_database_create);
        Button btnDatabaseAddValue = findViewById(R.id.btn_database_addvalue);
        Button btnDatabaseUpdateValue = findViewById(R.id.btn_database_updatevalue);
        Button btnDatabaseQueryValue = findViewById(R.id.btn_database_queryevalue);
        Button btnDatabaseDeleteValue = findViewById(R.id.btn_database_del_value);

        btnDatabaseCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDatabaseHelper.getWritableDatabase();
            }
        });

        btnDatabaseAddValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", "The Da Vinci Code");
                contentValues.put("author", "Dan Brown");
                contentValues.put("pages", 454);
                contentValues.put("price", 16.9);
                db.insert("Book", null, contentValues);
                Log.d("DatabaseActivity SQL", "add value");
                // 插入第二条数据
                //contentValues.clear();
                //...
            }
        });

        btnDatabaseUpdateValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("price", 17);
                db.update("Book", contentValues, "name=?", new String[]{"The Da Vinci Code"});
                Log.d("DatabaseActivity SQL", "update value");
            }
        });

        btnDatabaseQueryValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
                Cursor cursor = db.query("Book", null, null, null, null, null, null);
                if (cursor.moveToFirst()){
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        String press = cursor.getString(cursor.getColumnIndex("press"));
                        Log.d("DatabaseActivity SQL", "name :" + name);
                        Log.d("DatabaseActivity SQL", "author :" + author);
                        Log.d("DatabaseActivity SQL", "pages :" + pages);
                        Log.d("DatabaseActivity SQL", "price :" + price);
                        Log.d("DatabaseActivity SQL", "press :" +  press);
                    }while (cursor.moveToNext());
                }
                cursor.close();
                Log.d("DatabaseActivity SQL", "query value");
            }
        });

        btnDatabaseDeleteValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
                db.delete("Book", "pages > ?", new String[]{"300"});
            }
        });
    }
}

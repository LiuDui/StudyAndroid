package com.example.datapersistence;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.datapersistence.dao.entity.Book;

import org.litepal.LitePal;

import java.util.List;

public class DatabaseActivityByLite extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_by_lite_layout);

        Button btnDatabaseCreate = findViewById(R.id.btn_database_create_lite);
        Button btnDatabaseAddValue = findViewById(R.id.btn_database_addvalue_lite);
        Button btnDatabaseUpdateValue = findViewById(R.id.btn_database_updatevalue_lite);
        Button btnDatabaseQueryValue = findViewById(R.id.btn_database_queryevalue_lite);
        Button btnDatabaseDeleteValue = findViewById(R.id.btn_database_del_value_lite);


        btnDatabaseCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.getDatabase();
                Log.d("Database Lite SQL", "create database ");
            }
        });

        btnDatabaseAddValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setName("海边的卡夫卡");
                book.setAuthor("村上春树");
                book.setPages(352);
                book.setPress("---");
                book.setPrice(30);

                book.save();
            }
        });

        btnDatabaseUpdateValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Book book = new Book();
//                book.setName("海边的卡发卡");
//                book.setAuthor("村上春树");
//                book.setPages(352);
//                book.setPress("---");
//                book.setPrice(30);
//                book.save();
//
//                book.setPress("UnKnow");
//                book.save();
                Book book = new Book();
                book.setPress("清华出版社");
                book.setName("海边的卡夫卡");
                book.updateAll("name = ? and author = ?", "海边的卡发卡", "村上春树");
//                book.setToDefault("price");
//                book.updateAll();
            }
        });

        btnDatabaseQueryValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Book> books = LitePal.findAll(Book.class);
                for (int i = 0; i < books.size(); i++) {
                    Log.d("DatabaseLite SQL", books.get(i).toString());
                }
            }
        });

        btnDatabaseDeleteValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.deleteAll(Book.class, "pages > ?", "100");
            }
        });
    }
}

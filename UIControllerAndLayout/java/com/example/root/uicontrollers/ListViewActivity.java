package com.example.root.uicontrollers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import entity.NumInCHEN;

public class ListViewActivity extends AppCompatActivity {
    private Integer[] data = {1,2,3,4,5,6,7,8,9,10};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_layout);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(
                ListViewActivity.this, android.R.layout.simple_list_item_1,data); // 第一个参数是上下文，第二个参数是内置的子项布局，第三个参数必须是类对象的数组
        ListView lv = findViewById(R.id.list_view_list);
        lv.setAdapter(adapter);

    }
}

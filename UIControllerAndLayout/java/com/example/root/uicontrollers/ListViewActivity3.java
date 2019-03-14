package com.example.root.uicontrollers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import Adapters.NumAdapter;
import entity.NumInCHEN;

public class ListViewActivity3 extends AppCompatActivity {
    private List<NumInCHEN> list = new ArrayList<>();
    private Integer[] data = {1,2,3,4,5,6,7,8,9,10};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_layout3);
        initNums();
        NumAdapter adapter = new NumAdapter(ListViewActivity3.this, R.layout.num_layout, list);
        ListView listView = findViewById(R.id.list_view_list3);
        listView.setAdapter(adapter);
        //NumAdapter adapter = new NumAdapter(ListViewActivity3.this, R.layout.num_layout, list);
        //ListView listView = findViewById(R.id.list_view_list3);
        //listView.setAdapter(adapter);

    }

    private void initNums() {
        list.add(new NumInCHEN(0, "zero"));
        list.add(new NumInCHEN(1, "one"));
        list.add(new NumInCHEN(2, "two"));
        list.add(new NumInCHEN(3, "three"));
        list.add(new NumInCHEN(4, "four"));
        list.add(new NumInCHEN(5, "give"));
        list.add(new NumInCHEN(6, "six"));
        list.add(new NumInCHEN(7, "seven"));
        list.add(new NumInCHEN(8, "eight"));
        list.add(new NumInCHEN(9, "nine"));
        list.add(new NumInCHEN(10, "ten"));
        list.add(new NumInCHEN(11, "eleven"));
        list.add(new NumInCHEN(12, "twelve"));

    }
}

package com.example.root.uicontrollers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import Adapters.NumAdapter;
import Adapters.NumAdapterForRecycle;
import entity.NumInCHEN;

public class RecyclerViewActivity extends AppCompatActivity {

    private List<NumInCHEN> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_layout);
        initNums();
        RecyclerView recyclerView = findViewById(R.id.recycleer_view);
        LinearLayoutManager layoutManger = new LinearLayoutManager(this);
        layoutManger.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManger);
        NumAdapterForRecycle adapterForRecycle = new NumAdapterForRecycle(list);
        recyclerView.setAdapter(adapterForRecycle);

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

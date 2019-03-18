package com.example.datapersistence;

import android.content.Context;
import android.os.strictmode.UnbufferedIoViolation;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FilePersistence extends AppCompatActivity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_persistence_layout);
        Log.d("onCreate", "onCreate FilePersistence");
        if (editText == null){
            editText = findViewById(R.id.et_input);
            Log.d("edit is null", "FilePersistence");
        }
        reloda();
    }

    private void reloda() {
        String data = loadFromFile();
        if(data != null){
            System.out.println(data);
            editText.setText(data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("destory", "on desdory ececute in FilePersistence.onDestory");
        String input = editText.getText().toString();

        save(input);
    }

    private String loadFromFile(){
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();

        try {
            in = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null){
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }
    private void save(String data){
        FileOutputStream out = null;
        BufferedWriter bf = null;
        try {
            out = openFileOutput("data", Context.MODE_PRIVATE);
            bf = new BufferedWriter(new OutputStreamWriter(out));
            bf.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bf != null){
                    bf.close();
                    bf = null;
                }
                if (out != null){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

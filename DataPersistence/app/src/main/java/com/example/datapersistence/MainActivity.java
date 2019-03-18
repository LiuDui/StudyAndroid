package com.example.datapersistence;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnFile = findViewById(R.id.btn_write_file);
        Button btnShared = findViewById(R.id.btn_shared_preference);
        Button btnDatabase = findViewById(R.id.btn_database);
        Button btnDatabaseToLite = findViewById(R.id.btn_database_lite);

        btnDatabase.setOnClickListener(this);
        btnFile.setOnClickListener(this);
        btnShared.setOnClickListener(this);
        btnDatabaseToLite.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_write_file:
                Intent intenFromMainToWriteFile = new Intent(MainActivity.this, FilePersistence.class);
                startActivity(intenFromMainToWriteFile);
                break;
            case R.id.btn_shared_preference:
                Intent intenFromMainToSharedPreference = new Intent(MainActivity.this, SharedPreferenceActivity.class);
                startActivity(intenFromMainToSharedPreference);
                break;
            case R.id.btn_database:
                Intent intenFromMainToDatabase = new Intent(MainActivity.this, DatabaseActivity.class);
                startActivity(intenFromMainToDatabase);
                break;
            case R.id.btn_database_lite:
                Intent intenFromMainToDatabaseLite = new Intent(MainActivity.this, DatabaseActivityByLite.class);
                startActivity(intenFromMainToDatabaseLite);
                break;
            default:
                break;
        }
    }
}

package com.example.netxmljson;

import android.Manifest;
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

        Button btnMainToNet = findViewById(R.id.btn_main_to_net_activity);
        Button btnHttpUrlConnection = findViewById(R.id.btn_main_to_httpurlconnectioin);
        Button btnOkHtpp = findViewById(R.id.btn_main_to_okhttp);

        btnOkHtpp.setOnClickListener(this);
        btnMainToNet.setOnClickListener(this);
        btnHttpUrlConnection.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_main_to_net_activity:
                Intent intentMainToWeb = new Intent(MainActivity.this, WebViewTest.class);
                startActivity(intentMainToWeb);
                break;
            case R.id.btn_main_to_httpurlconnectioin:
                Intent intentMainToHttpUrlConnnection = new Intent(MainActivity.this, HttpURLConnection.class);
                startActivity(intentMainToHttpUrlConnnection);
            case R.id.btn_main_to_okhttp:
                Intent intentMainToOkHttp = new Intent(MainActivity.this, OkHttp.class);
                startActivity(intentMainToOkHttp);
                break;
                default:
                    break;
        }
    }
}

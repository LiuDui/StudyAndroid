package com.example.netxmljson;

import android.icu.util.LocaleData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttp extends AppCompatActivity {

    TextView tvResponse = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ok_http_layout);

        Button btnSendMessage = findViewById(R.id.btn_sendRequest_in_Okhttp);
        tvResponse = findViewById(R.id.tv_response_text_inOkHttp);

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("net in okhttp", "build request in okhttp");
                sendMessageWithOkHttp();
            }
        });
    }

    private void sendMessageWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://www.baidu.com")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    showRespose(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void showRespose(final String data){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvResponse.setText(data);
            }
        });
    }
}

package com.example.netxmljson;

import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpURLConnection extends AppCompatActivity implements View.OnClickListener{

    TextView tvResponse = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.http_urlconnection_layout);

        Button sendRequest = findViewById(R.id.btn_send_request);
        tvResponse = findViewById(R.id.tv_response_text);

        sendRequest.setOnClickListener(this);
    }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_send_request:
                    sendRequestWithHtppURLConnection();
                    break;

                    default:
                        break;
            }
        }

    private void sendRequestWithHtppURLConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                java.net.HttpURLConnection connection = null;
                BufferedReader bf = null;

                try {
                    URL url = new URL("https://www.baidu.com");
                    connection = (java.net.HttpURLConnection) url.openConnection();

                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    bf = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line = "";

                    while ((line = bf.readLine()) != null){
                        sb.append(line);
                    }
                    showRespsonse(sb.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showRespsonse(final String respsons){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvResponse.setText(respsons);
            }
        });
    }
}

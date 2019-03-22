package com.example.downloadservicetest;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String tag = "download_test";
    private DownloadService.DownloadBinder downloadBinder;

    // 创建一个连接器，在绑定服务时连接
    private ServiceConnection connection =  new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(tag, "on service connect");
            downloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startDownload = findViewById(R.id.btn_start_download);
        Button pauseDownload = findViewById(R.id.btn_pause_download);
        Button cancelDownload = findViewById(R.id.btn_cancel_download);

        startDownload.setOnClickListener(this);
        pauseDownload.setOnClickListener(this);
        cancelDownload.setOnClickListener(this);

        Log.d(tag, "set onclick");
        Intent intentToStartDownloadService = new Intent(this, DownloadService.class);
        startService(intentToStartDownloadService);
        bindService(intentToStartDownloadService, connection, BIND_AUTO_CREATE);

        Log.d(tag, "bind service");

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        Log.d(tag, "permission");
    }

    @Override
    public void onClick(View v) {
        if (downloadBinder == null){
            Log.d(tag, "bind is null");
            return;
        }
        switch (v.getId()){
            case R.id.btn_start_download:
                Log.d(tag, "start download");
                String url = "https://atom-installer.github.com/v1.35.1/AtomSetup-x64.exe?s=1552477560&ext=.exe";
                downloadBinder.startDownload(url);
                break;
            case R.id.btn_pause_download:
                //TODO
                break;
            case R.id.btn_cancel_download:
                //TODO
                break;
                default:
                    break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}

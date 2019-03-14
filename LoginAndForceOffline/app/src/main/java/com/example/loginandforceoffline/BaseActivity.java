package com.example.loginandforceoffline;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.loginandforceoffline.Collector.ActicityCollector;

public class BaseActivity extends AppCompatActivity {
    ForceOfflineReciver reciver;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ActicityCollector.addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("pri.lr.broadcast.FORCE_LINE");
        reciver = new ForceOfflineReciver();
        registerReceiver(reciver, intentFilter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (reciver != null){
            unregisterReceiver(reciver);
            reciver = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActicityCollector.removeActivity(this);
    }

    class ForceOfflineReciver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, Intent intent) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("warning");
            builder.setMessage("you are forced to be offline.");
            builder.setCancelable(false); // 设置为true的话，用户可以通过back键返回，继续使用程序
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActicityCollector.finishAll();// 销毁所有活动
                    Intent intent1 = new Intent(context, LoginActivity.class);
                    context.startActivity(intent1);
                }
            });
            builder.show();
        }
    }
}

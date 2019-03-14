package com.example.broadcaststudy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("111111111111111111111111111111111111");
        Toast.makeText(context, "receive in myBroadcast", Toast.LENGTH_SHORT).show();
    }
}

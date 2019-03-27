package com.example.horserunlamp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int viewWidth; // 屏幕宽窄
    private int viewHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewWidth = this.getApplication().getResources().getDisplayMetrics().widthPixels; // 获取屏幕宽度
        viewHeight = this.getApplication().getResources().getDisplayMetrics().heightPixels; // 获取屏幕高度
    }

    @Override
    protected void onStart() {
        super.onStart();
//        TextView textView = findViewById(R.id.text);
//        TextView textView1 = findViewById(R.id.tv_text);
//        textView1.setSelected(true);
//        Animation translation = new TranslateAnimation(viewWidth,-(viewWidth + textView.getWidth()), 0, 0);
//        translation.setInterpolator(new LinearInterpolator());
//        translation.setDuration(5000);
//        translation.setRepeatCount(Animation.INFINITE);


//        // 播放
//        textView.setAnimation(translation);
    }
}

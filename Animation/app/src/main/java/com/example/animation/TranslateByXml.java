package com.example.animation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Toast;

public class TranslateByXml extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translate_by_xml_layout);
        Button btnTranslation = findViewById(R.id.btn_translate_by_xml);
//        //1. 创建需要设置动画的视图view
//        Animation translationAnimation = AnimationUtils.loadAnimation(this, R.anim.button_animation);
//        // 2. 创建动画对象 并传入设置的动画效果xml组件
//        btnTranslation.startAnimation(translationAnimation);
//        // 3. 播放动画
//


        Toast.makeText(this, "使用java代码设置", Toast.LENGTH_LONG).show();
        // 改用java代码设置
        //1 得到view
        // 2 创建平移动画对象：平移动画对应的Animation子类为TranslateAnimation

        Animation translation = new TranslateAnimation(0, 500, 0, 500);
        translation.setDuration(3000);
        // 播放
        btnTranslation.setAnimation(translation);
    }
}

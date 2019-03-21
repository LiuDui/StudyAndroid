package com.example.animation;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    AnimationDrawable animationDrawable ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnMainToTranslate = findViewById(R.id.btn_main_to_translte_by_xml);
        Button btnMainToScale = findViewById(R.id.btn_main_to_scale_by_xml);
        Button btnMainToRatate = findViewById(R.id.btn_main_to_rotate);

        btnMainToTranslate.setOnClickListener(this);
        btnMainToScale.setOnClickListener(this);
        btnMainToRatate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_main_to_translte_by_xml:
                Intent intentMainToTranlateByXml = new Intent(MainActivity.this, TranslateByXml.class);
                startActivity(intentMainToTranlateByXml);
                break;
            case R.id.btn_main_to_scale_by_xml:
                Intent intentMainToScale = new Intent(MainActivity.this, ScaleActivity.class);
                startActivity(intentMainToScale);
                break;
            case R.id.btn_main_to_rotate:
                Intent intentMainToRotate = new Intent(MainActivity.this, RotateActivity.class);
                startActivity(intentMainToRotate);
                break;
            default:
                break;
        }
    }
}

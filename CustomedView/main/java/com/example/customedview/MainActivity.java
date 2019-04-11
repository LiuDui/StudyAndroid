package com.example.customedview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnToTitleView = findViewById(R.id.btn_to_titleview_activity);
        Button btnToTitleImageView = findViewById(R.id.btn_to_imgtitleview_activity);
        Button btnToRoundTurnRoundView = findViewById(R.id.btn_to_roundturnround_activity);

        btnToTitleView.setOnClickListener(this);
        btnToTitleImageView.setOnClickListener(this);
        btnToRoundTurnRoundView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_to_titleview_activity:
                Intent intentMainToTitleView = new Intent(this, TitleViewActivity.class);
                startActivity(intentMainToTitleView);
                break;
            case R.id.btn_to_imgtitleview_activity:
                Intent intentMainToImgTitle = new Intent(this, ImageAndTitleActivity.class);
                startActivity(intentMainToImgTitle);
                break;
            case R.id.btn_to_roundturnround_activity:
                Intent intentMainToRoundTurn = new Intent(this, RoundTurnRoundActivity.class);
                startActivity(intentMainToRoundTurn);
                break;
            default:
                Log.e(TAG, "View Id Cant Found");
                break;
        }
    }
}

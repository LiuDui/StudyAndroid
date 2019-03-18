package com.example.datapersistence;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SharedPreferenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference);

        Button btnSaveData = findViewById(R.id.btn_sava_sharedpreferences);
        Button btnShowData = findViewById(R.id.btn_show_sharedpreferences);
        final TextView tvShowData = findViewById(R.id.tv_show_data);

        btnSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("data",
                        MODE_PRIVATE).edit();
                editor.putBoolean("gender", true);
                editor.putString("name", "tony");
                editor.apply();
            }
        });

        btnShowData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                StringBuilder sb = new StringBuilder();
                sb.append("gengder:" + pref.getBoolean("gengder", true));
                sb.append("name" + pref.getString("name", "no one"));

                if (sb.length() > 0 ){
                    tvShowData.setText(sb.toString());
                }
            }
        });

    }
}

package com.example.root.uicontrollers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainLayoutActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText editText;
    private ImageView imgview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null){
//            actionBar.hide();
//        }
        Button button1 = findViewById(R.id.button_1);

        Button buttonLinear = findViewById(R.id.button_linear_layout);
        Button buttonLinear2 = findViewById(R.id.button_linear_layout2);
        Button buttonRelative = findViewById(R.id.button_relative_layout);
        Button buttonFrame = findViewById(R.id.button_frame_layout);
        Button buttonPercentage = findViewById(R.id.button_percentage_layout);
        Button buttonWeght1 = findViewById(R.id.button_linear_weight1);
        Button buttonWeght2 = findViewById(R.id.button_linear_weight2);
        Button buttonListView = findViewById(R.id.button_list_view);
        Button buttonListView3 = findViewById(R.id.button_list_view3);
        Button buttonCycleView = findViewById(R.id.button_cycleView);

        button1.setOnClickListener(this);
        buttonLinear.setOnClickListener(this);
        buttonLinear2.setOnClickListener(this);
        buttonRelative.setOnClickListener(this);
        buttonFrame.setOnClickListener(this);
        buttonPercentage.setOnClickListener(this);
        buttonWeght1.setOnClickListener(this);
        buttonWeght2.setOnClickListener(this);
        buttonListView.setOnClickListener(this);
        buttonListView3.setOnClickListener(this);
        buttonCycleView.setOnClickListener(this);
        editText = findViewById(R.id.edit_text);
        imgview = findViewById(R.id.image_view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_1:
                Toast.makeText(MainLayoutActivity.this, "click here", Toast.LENGTH_SHORT).show();
                String input = editText.getText().toString();
                Toast.makeText(MainLayoutActivity.this, input, Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_linear_layout:
                Intent intent = new Intent(MainLayoutActivity.this, LinearLayoutShowActivity.class);
                startActivity(intent);
                break;
            case R.id.button_linear_layout2:
                Intent intentToLinear2 = new Intent(MainLayoutActivity.this, LinearLayoutShowActivity2.class);
                startActivity(intentToLinear2);
                break;
            case R.id.button_linear_weight1:
                Intent intent_weight1 = new Intent(MainLayoutActivity.this, LayoutWeightShowActivity.class);
                startActivity(intent_weight1);
                break;
            case R.id.button_linear_weight2:
                Intent intent_weight2 = new Intent(MainLayoutActivity.this, LayoutWeightShowActivity2.class);
                startActivity(intent_weight2);
                break;
            case R.id.button_relative_layout:
                Intent intentToRelative = new Intent(MainLayoutActivity.this, RelativeLayoutShowActivity.class);
                startActivity(intentToRelative);
                break;
            case R.id.button_frame_layout:
                Intent intentToFrame = new Intent(MainLayoutActivity.this, FrameLayoutShowActivity.class);
                startActivity(intentToFrame);
                break;
            case R.id.button_percentage_layout:
                Intent intentToPercentage = new Intent(MainLayoutActivity.this, PercentageLayoutShowActivity.class);
                startActivity(intentToPercentage);
                break;
            case R.id.button_list_view:
                Intent intentListView = new Intent(MainLayoutActivity.this, ListViewActivity.class);
                startActivity(intentListView);
                break;
            case R.id.button_list_view3:
                System.out.println(12345);
                Intent intentListView3 = new Intent(MainLayoutActivity.this, ListViewActivity3.class);
                startActivity(intentListView3);
                break;
            case R.id.button_cycleView:
                Intent intentCycleView = new Intent(MainLayoutActivity.this, RecyclerViewActivity.class);
                startActivity(intentCycleView);
                break;

            default:
                break;
        }
    }
}

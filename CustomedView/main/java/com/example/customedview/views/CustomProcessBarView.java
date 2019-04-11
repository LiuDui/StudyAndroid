package com.example.customedview.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.customedview.R;

public class CustomProcessBarView extends View {
    private int mFirstColor;
    private int mSecondColor;
    private int mCircleWidth;

    private Paint mPaint;
    private int mProgress = 0;
    private int mSpeed;

    private boolean isNext = false;


    private boolean goon = false;

    public CustomProcessBarView(Context context) {
        this(context,null);
    }

    public CustomProcessBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProcessBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomProgressBar, defStyleAttr, 0);
        int count = typedArray.getIndexCount();

        for (int i = 0; i < count; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.CustomProgressBar_firstColor:
                    mFirstColor = typedArray.getInt(attr, Color.GREEN);
                    break;
                case R.styleable.CustomProgressBar_secondColor:
                    mSecondColor = typedArray.getInt(attr, Color.RED);
                    break;
                case R.styleable.CustomProgressBar_speed:
                    mSpeed = typedArray.getInt(attr, 20);
                    break;
                case R.styleable.CustomProgressBar_circleWidth:
                    mCircleWidth = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
                    break;
            }
        }

        typedArray.recycle();
        mPaint = new Paint();

        new Thread(new Runnable() {
            @Override
            public void run() {
                goon = true;
                while (goon){
                    mProgress ++; // 进度长
                    if(mProgress == 360){
                        mProgress = 0;
                        if(!isNext){
                            isNext = true;
                        }else {
                            isNext = false;
                        }
                    }

                    postInvalidate(); // 刷新
                    try {
                        Thread.sleep(mSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //TODO
    }

    public void setGoon(boolean goon){
        this.goon = goon;
    }

    public void stop(){
        this.goon = false;
    }

}

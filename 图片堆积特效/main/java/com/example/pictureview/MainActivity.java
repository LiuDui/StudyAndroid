package com.example.pictureview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout frameLayout = findViewById(R.id.frame_layout_1);
        frameLayout.addView(new MyView(this));
    }

    class MyView extends View{
        private Bitmap bitmap;
        private int factor = 3; // 拉伸倍数

        private Bitmap sourceBitmap;
        private Bitmap bigBitMap;

        private int beginPotionOfBigBitMapLeft = 0;
        private int beginPotionOfBigBitMapTop = 0;

        private int beginWallX = 0;
        private int currentWallX = 0;

        private int viewWidth; // 屏幕宽窄
        private int viewHeight;


        private final double DEFAULE_MAX_FRSC_IN_SCREEN = 0.4;


        PropertyHolder holder = new PropertyHolder();

        ValueAnimator animator = null;

        public MyView(Context context) {
            super(context);
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test2);
            viewWidth = context.getResources().getDisplayMetrics().widthPixels; // 获取屏幕宽度
            viewHeight = context.getResources().getDisplayMetrics().heightPixels; // 获取屏幕高度
            // 方便观看，对图像进行缩放
            if(bitmap.getWidth() > viewWidth * DEFAULE_MAX_FRSC_IN_SCREEN){
                // 缩小并创建
                float frac = (float) ((viewWidth * DEFAULE_MAX_FRSC_IN_SCREEN)/(float)bitmap.getWidth());
                System.out.println(frac);
                Matrix m = new Matrix();
                m.setScale(frac, frac, 0, 0);
                sourceBitmap = Bitmap.createBitmap(bitmap, 0, 0,bitmap.getWidth(),
                        bitmap.getHeight(), m, false);
            }else {
                sourceBitmap = bitmap;
            }

            bigBitMap = Bitmap.createScaledBitmap(bitmap, sourceBitmap.getWidth() * factor,
                    sourceBitmap.getHeight(), true);

            animator = ValueAnimator.ofInt(beginPotionOfBigBitMapLeft , sourceBitmap.getWidth() - bigBitMap.getWidth() );
            animator.setStartDelay(500);
            animator.setRepeatCount(0);
            animator.setDuration(10000);
            holder.setBigMapLeftX(beginPotionOfBigBitMapLeft);

            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    holder.setBigMapLeftX((Integer) animation.getAnimatedValue());
                    System.out.println("===========" + holder.getBigMapLeftX());
                    invalidate();
                }
            });
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            if(holder.getBigMapLeftX() > currentWallX){
                // 假如没到那个位置，那就只画拉伸后的
                canvas.drawBitmap(bigBitMap,holder.getBigMapLeftX(),beginPotionOfBigBitMapTop, new Paint());
                System.out.println("------" + holder.getBigMapLeftX());
                if(!animator.isStarted()){
                    animator.start();
                }
            }else{
                if(!animator.isStarted()){
                    animator.start();
                }
                int distence = currentWallX - holder.getBigMapLeftX(); // 当前越过的距离
                int caculate = distence/factor;
                currentWallX = caculate + beginWallX;



                Rect srcOfSmall = new Rect(0, 0, currentWallX, bigBitMap.getHeight());
                Rect dstOfSmall = new Rect(beginWallX , 0, caculate, bigBitMap.getHeight());
                canvas.drawBitmap(sourceBitmap, srcOfSmall, dstOfSmall, null);
                Rect srcOfBig = new Rect(distence, 0, distence + sourceBitmap.getWidth()-currentWallX, bigBitMap.getHeight());
                Rect dstOfBig = new Rect(currentWallX, 0, sourceBitmap.getWidth(), bigBitMap.getHeight());
                canvas.drawBitmap(bigBitMap, srcOfBig, dstOfBig, null);


//
//
//                Rect srcOfBig = new Rect(distence, 0, bigBitMap.getWidth(), bigBitMap.getHeight());
//                Rect dstOfBig = new Rect(currentWallX, 0, currentWallX + bigBitMap.getWidth()-distence, bigBitMap.getHeight());
//                canvas.drawBitmap(bigBitMap, srcOfBig, dstOfBig, null);
//
//                Rect srcOfSmall = new Rect(0, 0, currentWallX, bigBitMap.getHeight());
//                Rect dstOfSmall = new Rect(beginWallX , 0, caculate, bigBitMap.getHeight());
//                canvas.drawBitmap(sourceBitmap, srcOfSmall, dstOfSmall, null);


//
//                holder.setCurrentWallX((wallX - holder.getX())/factor);
//
//                Rect srcOfBig = new Rect(holder.getCurrentWallX()-holder.getX(), 0, bigBitMap.getWidth(), bigBitMap.getHeight());
//                Rect dstOfBig = new Rect(holder.getCurrentWallX(), 0, bigBitMap.getWidth()-(holder.getCurrentWallX()-holder.getX()), bigBitMap.getHeight());
//                canvas.drawBitmap(bigBitMap, srcOfBig, dstOfBig, null);
//
//                Rect srcOfSource = new Rect(0, 0,sourceBitmap.getWidth(), sourceBitmap.getHeight());
//                Rect dstOfSource = new Rect(0, 500, sourceBitmap.getWidth(), 500 + sourceBitmap.getHeight());
//                canvas.drawBitmap(sourceBitmap, srcOfSource, dstOfSource, null);

                // 先画活动的，再画漏出来的,到这的时候，已经到达收缩的地方
//                Rect srcOfBig = new Rect(wallX + holder.getX(), 0, bigBitMap.getWidth(), bigBitMap.getHeight());
//                Rect dstOfBig = new Rect(wallX, 0, wallX + bigBitMap.getWidth()-holder.getMovelength(), bigBitMap.getHeight());
//                //canvas.drawBitmap(sourceBitmap, 0, 0, null);
//                canvas.drawBitmap(bigBitMap, srcOfBig, dstOfBig, null);
//                wallX += holder.getX()/factor;
            }

        }
    }
}

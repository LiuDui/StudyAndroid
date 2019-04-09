package com.example.customedview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

public class CustomImageView extends View {
    private static final String TAG = "CustomTitleView";
    private String mTitleText;
    private int mTitleTextColor;
    private int mTitleTextSize;

    private Bitmap mImage;
    private int mImageScaleType;

    private static final int IMAGE_SCALE_FITXY = 0;
    private static final int IMAGE_SCALE_CENTER = 1;


    private Context context ;
    private Rect rect; // 图像的位置
    /**
     * 控制文本绘制的范围
     */
    private Rect mTextBound;
    private Paint mPaint;

    public CustomImageView(Context context) {
        this(context, null);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomImageView, defStyleAttr, 0);
        int count = typedArray.getIndexCount();

        for (int i = 0; i < count; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr){
                case R.styleable.CustomImageView_imageScaleType:
                    mImageScaleType = typedArray.getInt(attr, 0);
                    break;
                case R.styleable.CustomImageView_image:
                    mImage = BitmapFactory.decodeResource(getResources(), typedArray.getResourceId(attr, 0));
                    break;
                case R.styleable.CustomImageView_titleText:
                    mTitleText = typedArray.getString(attr);
                    break;
                case R.styleable.CustomImageView_titleTextSize:
                    mTitleTextSize = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomImageView_titleTextColor:
                    mTitleTextColor = typedArray.getColor(attr, Color.BLACK);
                    break;
            }
        }

        typedArray.recycle();

        rect = new Rect();
        mPaint = new Paint();
        mTextBound = new Rect();
        mPaint.setTextSize(mTitleTextSize);

        // 计算描绘字体需要的范围
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mTextBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mWidth = 0;
        int mHeight = 0;

        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        if(specMode == MeasureSpec.EXACTLY){
            Log.i("CustomImageView", "EXACTLY");
            mWidth = specSize;
        }else {
            // 图片和文字都有各自的宽和高
            int desireByImg = getPaddingLeft() + getPaddingRight() + mImage.getWidth();
            int desireByTitle = getPaddingLeft() + getPaddingRight() + mTextBound.width();

            if(specMode == MeasureSpec.AT_MOST){
                int desire = Math.max(desireByImg, desireByTitle); // 两个都要容纳下
                mWidth = Math.min(desire, specSize);
                Log.i("CustomImageView", "AT_MOST");
            }
        }

        // 设置高度
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);

        if(specMode == MeasureSpec.EXACTLY){
            mHeight = specSize;
        }else{
            int desire = getPaddingTop() + getPaddingBottom() + mImage.getHeight() + mTextBound.height();
            if(specMode == MeasureSpec.AT_MOST){
                mHeight = Math.min(desire, specSize);
            }
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvassuper.onDraw(canvas);


        int viewWidth = 0;
        int viewHeight = 0;
        viewWidth = context.getResources().getDisplayMetrics().widthPixels; // 获取屏幕宽度
        viewHeight = context.getResources().getDisplayMetrics().heightPixels; // 获取屏幕高度

        System.out.println("--------------------------------------------------");
        System.out.println("屏幕宽度：" + viewWidth);
        System.out.println("屏幕高度：" + viewHeight);
        System.out.println("图片宽度：" + mImage.getWidth());
        System.out.println("图片高度：" + mImage.getHeight());
        System.out.println("测量宽度：" + getMeasuredWidth());
        System.out.println("测量高度：" + getMeasuredHeight());
        System.out.println("左Padding：" + getPaddingLeft());
        System.out.println("右Padding：" + getPaddingRight());
        System.out.println("上Padding：" + getPaddingTop());
        System.out.println("下Padding：" + getPaddingBottom());

        System.out.println("文字宽：" + mTextBound.width());
        System.out.println("文字高：" + mTextBound.height());

        mPaint.setStrokeWidth(4); // 设置边框
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.CYAN);
        canvas.drawRect(0,0,getMeasuredWidth(), getMeasuredHeight(), mPaint);

        rect.left = getPaddingLeft();
        rect.right = getMeasuredWidth() - getPaddingRight();
        rect.top = getPaddingTop();
        rect.bottom = getMeasuredHeight() - getPaddingBottom();

        mPaint.setColor(mTitleTextColor);
        mPaint.setStyle(Paint.Style.FILL);

        // 当字体设置的宽度小于字体需要的宽度时，将字体改为xxx...

        if(mTextBound.width() > getMeasuredWidth()){
            TextPaint paint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(mTitleText, paint, (float)getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg, getPaddingLeft(), getMeasuredHeight()-getPaddingBottom(), mPaint);
        }else{
            canvas.drawText(mTitleText, getMeasuredWidth()/2 - mTextBound.width()*1.0f / 2, getMeasuredHeight() - getPaddingBottom(), mPaint);
        }

        // 取消掉使用的块
        rect.bottom -= mTextBound.height();


        // 方便观看，对图像进行缩放
        if(mImage.getWidth() > viewWidth ){
            // 缩小并创建
            float frac = (float) ((viewWidth )/(float)mImage.getWidth());
            Matrix m = new Matrix();
            m.setScale(frac, frac, 0, 0);
            mImage = Bitmap.createBitmap(mImage, 0, 0,mImage.getWidth(),
                    mImage.getHeight(), m, false);
        }else {
        }

        if(mImageScaleType == IMAGE_SCALE_FITXY){
            canvas.drawBitmap(mImage, null, rect, mPaint);
        }else{
            rect.left = getMeasuredWidth() / 2 - mImage.getWidth() / 2;
            rect.right = getMeasuredWidth() / 2 + mImage.getWidth() / 2;

            rect.top = (getMeasuredHeight() - mTextBound.height())/2 - mImage.getHeight() / 2;
            rect.bottom = (getMeasuredHeight() - mTextBound.height())/2 + mImage.getHeight() / 2;

            canvas.drawBitmap(mImage, null, rect, mPaint);
        }
    }
}

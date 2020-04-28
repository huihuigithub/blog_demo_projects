package com.znh.customviewdemo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.znh.customviewdemo.R;

/**
 * Created by znh on 2017/8/14.
 * 自定义进度条控件
 */

public class CustomProgressBar extends View {

    //进度比
    private float mRate = 0.1f;

    //已经走过的进度条的颜色
    private int mProgressReachColor = Color.RED;

    //还没有走到的进度条的颜色
    private int mProgressUnreachColor = Color.BLUE;

    //绘制已经走过的进度条部分的画笔
    private Paint mReachPaint;

    //绘制还没有走到的进度条部分的画笔
    private Paint mUnReachPaint;

    //绘制边框的画笔
    private Paint mBorderPaint;

    //绘制已经走过的进度条部分的矩形
    private RectF mReachRectF;

    //绘制还没有走到的进度条部分的矩形
    private RectF mUnReachRectF;

    //绘制边框矩形
    private RectF mBorderRectF;

    //绘制图标矩形
    private RectF mIconRectF;

    //图标bitmap
    private Bitmap mIconBitmap;

    //动画值
    private int animatorValue;

    public CustomProgressBar(Context context) {
        super(context);
        init(context, null);
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 做一些初始化操作
     *
     * @param context 上下文
     * @param attrs   属性
     */
    public void init(Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.customProgressBar);
            if (typedArray != null) {
                mRate = typedArray.getFloat(R.styleable.customProgressBar_progress_bar_rate, mRate);
                mProgressReachColor = typedArray.getInt(R.styleable.customProgressBar_progress_bar_reach_color, mProgressReachColor);
                mProgressUnreachColor = typedArray.getInt(R.styleable.customProgressBar_progress_bar_unreach_color, mProgressUnreachColor);
                typedArray.recycle();
            }
        }

        mReachRectF = new RectF();
        mReachPaint = new Paint();
        mReachPaint.setColor(mProgressReachColor);

        mUnReachRectF = new RectF();
        mUnReachPaint = new Paint();
        mUnReachPaint.setColor(mProgressUnreachColor);

        mBorderRectF = new RectF();
        mBorderPaint = new Paint();
        mBorderPaint.setColor(Color.WHITE);
        mBorderPaint.setStrokeWidth(30);
        mBorderPaint.setStyle(Paint.Style.STROKE);

        mIconRectF = new RectF();

        ValueAnimator animator = ValueAnimator.ofInt(1, 80);
        animator.setDuration(300);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(Integer.MAX_VALUE);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                animatorValue = (int) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }


    /**
     * 测量大小
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(getDefaultWidthSize(widthSize), getDefaultHeightSize(heightSize));
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(getDefaultWidthSize(widthSize), heightSize);
        } else {
            setMeasuredDimension(widthSize, getDefaultHeightSize(heightSize));
        }
    }

    /**
     * 绘制
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制进度走过区域
        mReachRectF.set(0, 0, getWidth() * mRate, getHeight());
        canvas.drawRect(mReachRectF, mReachPaint);

        //绘制进度未到达区域
        mUnReachRectF.set(getWidth() * mRate, 0, getWidth(), getHeight());
        canvas.drawRect(mUnReachRectF, mUnReachPaint);

        //绘制圆角边框
        mBorderRectF.set(0, 0, getWidth(), getHeight());
        canvas.drawRoundRect(mBorderRectF, 30, 30, mBorderPaint);

        //绘制图标
        if (mIconBitmap == null) {
            mIconBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        }
        int iconLeft = (int) (getWidth() * mRate - mIconBitmap.getWidth() / 4);
        int iconRight = (int) (getWidth() * mRate + mIconBitmap.getWidth() / 4);
        //mIconRectF.set(iconLeft, 0, iconRight, getHeight());
        mIconRectF.set(iconLeft, -animatorValue, iconRight, getHeight() - animatorValue);
        canvas.drawBitmap(mIconBitmap, null, mIconRectF, null);
    }

    /**
     * 响应点击事件,变换颜色
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mReachPaint.setColor(mProgressUnreachColor);
                mUnReachPaint.setColor(mProgressReachColor);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                mReachPaint.setColor(mProgressReachColor);
                mUnReachPaint.setColor(mProgressUnreachColor);
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 获取默认宽度
     *
     * @param size
     * @return
     */
    public int getDefaultWidthSize(int size) {
        return Math.min(size, 200);
    }


    /**
     * 获取默认高度
     *
     * @param size
     * @return
     */
    public int getDefaultHeightSize(int size) {
        return Math.min(size, 120);
    }

    /**
     * 获取进度比
     *
     * @return
     */
    public float getmRate() {
        return mRate;
    }

    /**
     * 设置进度比
     *
     * @param mRate
     */
    public void setmRate(float mRate) {
        this.mRate = mRate;
        invalidate();
    }
}














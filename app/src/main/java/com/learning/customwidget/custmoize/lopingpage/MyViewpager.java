package com.learning.customwidget.custmoize.lopingpage;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by chengui.zhong
 * on 2022/4/21
 */
public class MyViewpager extends ViewPager {
    private static final String TAG = "MyViewpager";

    private long mDelayTime = 2000;

    private float downX;
    private float downY;
    private long downTime;
    private boolean isClick = false;

    public MyViewpager(@NonNull Context context) {
        this(context, null);
    }

    public void setDelayTime(long delayTime) {
        this.mDelayTime = delayTime;
    }

    public MyViewpager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getX();
                        downY = event.getY();
                        downTime = System.currentTimeMillis();
                        stopLooper();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        float dx = event.getX() - downX;
                        float dy = event.getY() - downY;
                        long dTime = System.currentTimeMillis() - downTime;
                        isClick = dx <= 5 && dy <= 5 && dTime <= 1000;
                        if (isClick){
                            if (mPageItemClickListener != null) {
                                mPageItemClickListener.onItemClick(getCurrentItem());
                            }
                        }
                        startLooper();
                        break;
                }
                return false;
            }
        });
    }

    private OnPagerItemClickListener mPageItemClickListener = null;

    public void setPagerItemClickListener(OnPagerItemClickListener listener){
        this.mPageItemClickListener = listener;
    }

    public interface OnPagerItemClickListener{
        void onItemClick(int position);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startLooper();
    }

    private void startLooper() {
        postDelayed(mRunnable, mDelayTime);
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            int currentItem = getCurrentItem();
            currentItem++;
            setCurrentItem(currentItem);
            postDelayed(this, mDelayTime);
        }
    };

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopLooper();
    }

    private void stopLooper() {
        removeCallbacks(mRunnable);
    }
}

package com.learning.customwidget.custmoize.lopingpage;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.learning.customwidget.R;

/**
 * Created by chengui.zhong
 * on 2022/4/20
 */
public class MyLoopingPager extends RelativeLayout {
    private static final String TAG = "MyLoopingPager";
    private MyViewpager mViewpager;
    private TextView mTitle;
    private LinearLayout mPointContainer;
    private BindTitleListener mBindTitleListener;
    private InnerAdapter mInnerPagerAdapter;
    private OnItemClickListener mOnItemClickListener = null;
    private boolean mIsTitleShow;
    private int mShowPagerCount;
    private int mSwitchTime;

    public MyLoopingPager(Context context) {
        this(context, null);
    }

    public MyLoopingPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyLoopingPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 载入view
        LayoutInflater.from(context).inflate(R.layout.looping_pager, this, true);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyLoopingPager);
        mIsTitleShow = ta.getBoolean(R.styleable.MyLoopingPager_is_title_show, true);
        mShowPagerCount = ta.getInteger(R.styleable.MyLoopingPager_show_pager_count, 3);
        mSwitchTime = ta.getInteger(R.styleable.MyLoopingPager_switch_time, -1);
        ta.recycle();
        initView();
        initEvent();
    }

    public void setData(InnerAdapter pagerAdapter, BindTitleListener bindTitleListener) {
        this.mBindTitleListener = bindTitleListener;
        this.mInnerPagerAdapter = pagerAdapter;
        mViewpager.setAdapter(mInnerPagerAdapter);
        mViewpager.setCurrentItem(mInnerPagerAdapter.getDataSize() * 100);
        if (mBindTitleListener != null) {
            mTitle.setText(mBindTitleListener.getTitle(0));
        }
        updateIndicatorPoint();
    }

    private void updateIndicatorPoint() {
        if (mInnerPagerAdapter != null && mBindTitleListener != null) {
            int count = mInnerPagerAdapter.getDataSize();
            mPointContainer.removeAllViews();
            for (int i = 0; i < count; i++) {
                View point = new View(getContext());
                if (mViewpager.getCurrentItem() % count == i) {
                    point.setBackground(getResources().getDrawable(R.drawable.shape_circle_red));
                } else {
                    point.setBackground(getResources().getDrawable(R.drawable.shape_circle_white));
                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
                params.setMargins(10, 0, 10, 0);
                point.setLayoutParams(params);
                mPointContainer.addView(point);
            }
        }
    }

    private void initEvent() {
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 页面改变时
                if (mBindTitleListener != null && mInnerPagerAdapter != null) {
                    int realPosition = position % mInnerPagerAdapter.getDataSize();
                    mTitle.setText(mBindTitleListener.getTitle(realPosition));
                }
                updateIndicatorPoint();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewpager.setPagerItemClickListener(new MyViewpager.OnPagerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mOnItemClickListener != null && mInnerPagerAdapter != null) {
                    int realPosition = position % mInnerPagerAdapter.getDataSize();
                    mOnItemClickListener.onItemClick(realPosition);
                }
            }
        });
    }

    public void setItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    public abstract static class InnerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            int realPosition = position % getDataSize();
            View itemView = getView(container, realPosition);
            container.addView(itemView);
            return itemView;
        }

        protected abstract int getDataSize();

        protected abstract View getView(ViewGroup container, int position);

    }

    ;

    private void initView() {
        mViewpager = this.findViewById(R.id.looper_pager_vp);
        if (mSwitchTime != -1) {
            mViewpager.setDelayTime(mSwitchTime);
        }
        if (mShowPagerCount == 3) {
            mViewpager.setOffscreenPageLimit(3);
            mViewpager.setPageMargin(50);
            RelativeLayout.LayoutParams params = new LayoutParams(mViewpager.getLayoutParams());
            params.setMargins(200, 0, 200, 0);
            mViewpager.setLayoutParams(params);
        }
        mTitle = this.findViewById(R.id.looper_pager_title_tv);
        if (!mIsTitleShow) {
            mTitle.setVisibility(GONE);
        }
        mPointContainer = this.findViewById(R.id.looper_pager_point_container);
    }


    public interface BindTitleListener {
        String getTitle(int position);
    }

}

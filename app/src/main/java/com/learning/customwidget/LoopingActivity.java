package com.learning.customwidget;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.learning.customwidget.custmoize.lopingpage.PagerItem;
import com.learning.customwidget.custmoize.lopingpage.MyLoopingPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chengui.zhong
 * on 2022/4/20
 */
public class LoopingActivity extends AppCompatActivity {

    private List<PagerItem> mData = new ArrayList<>();
    private MyLoopingPager mLooperPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looping);
        initData();
        mLooperPager = this.findViewById(R.id.looper_pager);
        mLooperPager.setData(mInnerAdapter, new MyLoopingPager.BindTitleListener() {
            @Override
            public String getTitle(int position) {
                return mData.get(position).getTitle();
            }
        });
        initEvent();
    }

    private void initEvent() {
        if (mInnerAdapter != null) {
            mLooperPager.setItemClickListener(new MyLoopingPager.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(LoopingActivity.this, "click " + position + " on item", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private MyLoopingPager.InnerAdapter mInnerAdapter = new MyLoopingPager.InnerAdapter() {
        @Override
        protected int getDataSize() {
            return mData.size();
        }

        @Override
        protected View getView(ViewGroup container, int position) {
            ImageView iv = new ImageView(container.getContext());
            iv.setImageResource(mData.get(position).getCoverResId());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            return iv;
        }

    };

    private void initData() {
        mData.add(new PagerItem("第一个", R.mipmap.pic0));
        mData.add(new PagerItem("第2个", R.mipmap.pic1));
        mData.add(new PagerItem("第三个", R.mipmap.pic2));
        mData.add(new PagerItem("第4个", R.mipmap.pic3));
    }
}

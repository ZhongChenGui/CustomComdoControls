package com.learning.customwidget.custmoize;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.learning.customwidget.R;

public class InputNumberView extends LinearLayout {
    private TextView mMinusBtn;
    private TextView mPlusBtn;
    private EditText mValue;

    private int mCurrentValue = 0;
    private OnNumberChangeListener mOnNumberChangeListener;

    public InputNumberView(Context context) {
        this(context, null);
    }

    public InputNumberView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InputNumberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 添加view
        initView(context);
        // 设置事件
        setUpEvent();
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.input_number_view, this);
        mMinusBtn = view.findViewById(R.id.minus_btn);
        mPlusBtn = view.findViewById(R.id.plus_btn);
        mValue = view.findViewById(R.id.value);
        updateValue();
    }

    private void setUpEvent() {
        mMinusBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentValue--;
                updateValue();
            }
        });
        mPlusBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentValue++;
                updateValue();
            }
        });
    }

    private void updateValue() {
        mValue.setText(String.valueOf(mCurrentValue));
        if (mOnNumberChangeListener != null) {
            mOnNumberChangeListener.onNumberChange(mCurrentValue);
        }
    }

    public void setOnNumberChangeListener(OnNumberChangeListener listener){
        this.mOnNumberChangeListener = listener;
    }

    public interface OnNumberChangeListener{
        void onNumberChange(int value);
    }
}

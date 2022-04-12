package com.learning.customwidget.custmoize;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.learning.customwidget.R;

public class InputNumberView extends LinearLayout {
    private static final String TAG = "InputNumberView";
    private TextView mMinusBtn;
    private TextView mPlusBtn;
    private EditText mValue;

    private int mCurrentValue = 0;
    private OnNumberChangeListener mOnNumberChangeListener;
    private int mMax;
    private int mMin;
    private int mStep;
    private int mDefaultValue;
    private boolean mDisable;
    private int mBtnBgRes;

    public InputNumberView(Context context) {
        this(context, null);
    }

    public InputNumberView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InputNumberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 初始化属性
        initAttrs(context, attrs);
        // 添加view
        initView(context);
        // 设置事件
        setUpEvent();
    }

    private void initAttrs(Context context, @Nullable AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.InputNumberView);
        mMax = ta.getInt(R.styleable.InputNumberView_max, 0);
        mMin = ta.getInt(R.styleable.InputNumberView_min, 0);
        mStep = ta.getInt(R.styleable.InputNumberView_step, 1);
        mDefaultValue = ta.getInt(R.styleable.InputNumberView_defaultValue, 0);
        this.mCurrentValue = mDefaultValue;
        mDisable = ta.getBoolean(R.styleable.InputNumberView_disable, false);
        mBtnBgRes = ta.getResourceId(R.styleable.InputNumberView_btnBackground, -1);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.input_number_view, this);
        mMinusBtn = view.findViewById(R.id.minus_btn);
        mPlusBtn = view.findViewById(R.id.plus_btn);
        mValue = view.findViewById(R.id.value);
        mMinusBtn.setEnabled(!mDisable);
        mPlusBtn.setEnabled(!mDisable);
        updateValue();
    }

    public int getBtnBgRes() {
        return mBtnBgRes;

    }

    public void setBtnBgRes(int btnBgRes) {
        mBtnBgRes = btnBgRes;
    }

    public int getMax() {
        return mMax;
    }

    public void setMax(int max) {
        mMax = max;
    }

    public int getMin() {
        return mMin;
    }

    public void setMin(int min) {
        mMin = min;
    }

    public int getStep() {
        return mStep;
    }

    public void setStep(int step) {
        mStep = step;
    }

    public int getDefaultValue() {
        return mDefaultValue;
    }

    public void setDefaultValue(int defaultValue) {
        mDefaultValue = defaultValue;
        mCurrentValue = mDefaultValue;
        updateValue();
    }

    public boolean isDisable() {
        return mDisable;
    }

    public void setDisable(boolean disable) {
        mDisable = disable;
    }

    public int getCurrentValue() {
        return mCurrentValue;
    }

    public void setCurrentValue(int currentValue) {
        mCurrentValue = currentValue;
    }

    private void setUpEvent() {
        mMinusBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentValue -= mStep;
                mPlusBtn.setEnabled(true);
                if (mCurrentValue <= mMin && mMin != 0){
                    mCurrentValue = mMin;
                    v.setEnabled(false);
                    if (mOnNumberChangeListener != null) {
                        mOnNumberChangeListener.onNumberMin(mMin);
                    }
                }
                updateValue();
            }
        });
        mPlusBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentValue += mStep;
                mMinusBtn.setEnabled(true);
                if (mCurrentValue >= mMax && mMax != 0){
                    mCurrentValue = mMax;
                    v.setEnabled(false);
                    if (mOnNumberChangeListener != null) {
                        mOnNumberChangeListener.onNumberMax(mMax);
                    }
                }
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

    public void setOnNumberChangeListener(OnNumberChangeListener listener) {
        this.mOnNumberChangeListener = listener;
    }

    public interface OnNumberChangeListener {
        void onNumberChange(int value);

        void onNumberMax(int maxValue);

        void onNumberMin(int minValue);
    }
}

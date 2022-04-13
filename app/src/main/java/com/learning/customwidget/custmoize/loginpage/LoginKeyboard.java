package com.learning.customwidget.custmoize.loginpage;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.learning.customwidget.R;

public class LoginKeyboard extends LinearLayout implements View.OnClickListener {
    private static final String TAG = "LoginKeyboard";
    private OnKeyPressListener mOnKeyPressListener = null;

    public LoginKeyboard(Context context) {
        this(context, null);
    }

    public LoginKeyboard(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoginKeyboard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 载入view
        LayoutInflater.from(context).inflate(R.layout.num_key_board_view, this);
        initView();
    }

    private void initView() {
        this.findViewById(R.id.number_0).setOnClickListener(this);
        this.findViewById(R.id.number_1).setOnClickListener(this);
        this.findViewById(R.id.number_2).setOnClickListener(this);
        this.findViewById(R.id.number_3).setOnClickListener(this);
        this.findViewById(R.id.number_4).setOnClickListener(this);
        this.findViewById(R.id.number_5).setOnClickListener(this);
        this.findViewById(R.id.number_6).setOnClickListener(this);
        this.findViewById(R.id.number_7).setOnClickListener(this);
        this.findViewById(R.id.number_8).setOnClickListener(this);
        this.findViewById(R.id.number_9).setOnClickListener(this);
        this.findViewById(R.id.back).setOnClickListener(this);
    }

    public void setOnKeyPressListener(OnKeyPressListener listener){
        this.mOnKeyPressListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnKeyPressListener == null){
            Log.d(TAG, "mOnKeyPressListener is null your need use setOnKeyPressListener set listener....");
            return;
        }
        if (v.getId() == R.id.back){
            mOnKeyPressListener.onBackPress();
        }else {
            String text = ((TextView) v).getText().toString();
            mOnKeyPressListener.onNumberPress(Integer.valueOf(text));
        }
    }

    public interface OnKeyPressListener{
        void onNumberPress(int value);

        void onBackPress();
    }

}

package com.learning.customwidget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.tv.TvView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.learning.customwidget.custmoize.loginpage.LoginKeyboardView;
import com.learning.customwidget.custmoize.numberinput.InputNumberView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private InputNumberView mInputNumberView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInputNumberView = findViewById(R.id.input_number);
        initEvent();
    }
    // 处理事件
    private void initEvent() {

        mInputNumberView.setOnNumberChangeListener(new InputNumberView.OnNumberChangeListener() {
            @Override
            public void onNumberChange(int value) {
                Log.d(TAG, "onNumberChange: value is  - > " + value);
            }

            @Override
            public void onNumberMax(int maxValue) {
                Log.d(TAG, "onNumberMax: max value is  -- > " + maxValue);
            }

            @Override
            public void onNumberMin(int minValue) {
                Log.d(TAG, "onNumberMin: min value is  - > " + minValue);
            }
        });
    }

    public void startLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void startLooping(View view) {
        startActivity(new Intent(this, LoopingActivity.class));
    }
}
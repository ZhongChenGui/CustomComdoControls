package com.learning.customwidget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.learning.customwidget.custmoize.loginpage.LoginKeyboard;
import com.learning.customwidget.custmoize.numberinput.InputNumberView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private InputNumberView mInputNumberView;
    private LoginKeyboard mLoginKeyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInputNumberView = findViewById(R.id.input_number);
        mLoginKeyboard = findViewById(R.id.num_key_board);
        initEvent();
    }
    // 处理时间
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
        mLoginKeyboard.setOnKeyPressListener(new LoginKeyboard.OnKeyPressListener() {
            @Override
            public void onNumberPress(int value) {
                Log.d(TAG, "onNumberPress: click value is   -- > " + value);
            }

            @Override
            public void onBackPress() {
                Log.d(TAG, "onBackPress: on click back.............");
            }
        });
    }
}
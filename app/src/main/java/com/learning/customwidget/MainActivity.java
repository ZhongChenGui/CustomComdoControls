package com.learning.customwidget;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.learning.customwidget.custmoize.InputNumberView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private InputNumberView mInputNumberView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInputNumberView = findViewById(R.id.input_number);
        mInputNumberView.setOnNumberChangeListener(new InputNumberView.OnNumberChangeListener() {
            @Override
            public void onNumberChange(int value) {
                Log.d(TAG, "onNumberChange: value is  - > " + value);
            }
        });
    }
}
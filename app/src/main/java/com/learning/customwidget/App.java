package com.learning.customwidget;

import android.app.Application;
import android.os.Handler;

public class App extends Application {
    private static Handler sHandler;
    @Override
    public void onCreate() {
        super.onCreate();
        sHandler = new Handler();
    }
    public static Handler getHandler() {
        return sHandler;
    }
}


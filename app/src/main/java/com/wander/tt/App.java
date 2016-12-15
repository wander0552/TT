package com.wander.tt;

import android.app.Application;

/**
 * Created by wander on 2016/11/27.
 */

public class App extends Application {
    private static  Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Application getInstance(){
        return instance;
    }
}

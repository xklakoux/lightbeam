package com.xklakoux.lamp.app;

import android.app.Application;

import com.orm.SugarContext;

/**
 * Created by artur on 05/05/16.
 */
public class LampApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }
}

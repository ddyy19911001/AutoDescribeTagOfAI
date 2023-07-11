package com.app.mybaseframwork.base;

import com.app.mybaseframwork.BuildConfig;
import com.dy.fastframework.application.SuperBaseApp;

public class BaseApp extends SuperBaseApp {
    public static BaseApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
    }

    @Override
    protected String setBaseUrl() {
        return BuildConfig.HOST_URL;
    }

    @Override
    public boolean closeDebugLog() {
        return false;
    }
}

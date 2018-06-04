package com.advancedtimecontrol.motivateyourself;

import android.app.Application;

import com.advancedtimecontrol.motivateyourself.util.TypefaceUtil;
import com.onesignal.OneSignal;

public class MyApplication extends Application {


    private static MyApplication mInstance;

    public MyApplication() {
        mInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .init();
        mInstance = this;
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/custom.ttf");
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }




}

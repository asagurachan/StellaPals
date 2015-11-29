package com.stella.pals.frontend.base;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by DJ on 11/11/15.
 * Project: Stella Pals
 */
public class BaseApplication extends Application {

    private static Context mContext;

    public static Context getAppContext() {
        return BaseApplication.mContext;
    }

    @Override
    public void onCreate() {
        mContext = getApplicationContext();

        super.onCreate();
        Fabric.with(this, new Crashlytics());
    }

}

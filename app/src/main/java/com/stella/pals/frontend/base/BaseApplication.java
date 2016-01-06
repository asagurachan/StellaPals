package com.stella.pals.frontend.base;

import android.app.Application;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.log.CustomLogger;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.stella.pals.frontend.global.Global;

import io.fabric.sdk.android.Fabric;

/**
 * Created by DJ on 11/11/15.
 * Project: Stella Pals
 */
public class BaseApplication extends Application {

    private static BaseApplication instance;
    private JobManager localJobManager;
    private JobManager networkJobManager;

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Fabric.with(this, new Crashlytics());
        FlowManager.init(this);
        configureJobManagers();
    }

    private void configureJobManagers() {
        Configuration.Builder configuration = new Configuration.Builder(this)
                .customLogger(new CustomLogger() {
                    private static final String TAG = "JOBS";
                    @Override
                    public boolean isDebugEnabled() {
                        return Global.PRODUCTION;
                    }

                    @Override
                    public void d(String text, Object... args) {
                        Log.d(TAG, String.format(text, args));
                    }

                    @Override
                    public void e(Throwable t, String text, Object... args) {
                        Log.e(TAG, String.format(text, args), t);
                    }

                    @Override
                    public void e(String text, Object... args) {
                        Log.e(TAG, String.format(text, args));
                    }
                })
                .minConsumerCount(1)
                .maxConsumerCount(3)
                .loadFactor(3)
                .consumerKeepAlive(120);

        localJobManager = new JobManager(this, configuration.id("local").build());
        networkJobManager = new JobManager(this, configuration.id("network").build());
    }

    public JobManager getLocalJobManager() {
        return localJobManager;
    }

    public JobManager getNetworkJobManager() {
        return networkJobManager;
    }
}

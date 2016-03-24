package com.stella.pals.views.base;

import android.app.Application;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.log.CustomLogger;
import com.squareup.leakcanary.LeakCanary;
import com.stella.pals.network.ApiService;
import com.stella.pals.views.global.Global;

import org.androidannotations.annotations.EApplication;

import java.io.IOException;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by DJ on 11/11/15.
 * Project: Stella Pals
 */
@EApplication
public class BaseApplication extends Application {

    private static BaseApplication instance;
    private JobManager localJobManager;
    private JobManager networkJobManager;
    private ApiService apiService;
    private ClearableCookieJar cookieJar;

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Fabric.with(this, new Crashlytics());
        LeakCanary.install(this);
        initConfigurations();
    }

    //region Configurations
    private void initConfigurations() {
        configureJobManagers();
        configureApiService();
        configureRealm();
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

    private void configureCookieJar() {
        cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this));
    }

    private void configureApiService() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        configureCookieJar();
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .addNetworkInterceptor(logging)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder().addHeader("Referer", "https://www.interpals.net/index.php").build();
                        return chain.proceed(request);
                    }
                })
                .build();

        apiService = new Retrofit.Builder()
                .baseUrl("https://www.interpals.net/")
                .client(client)
                .build().create(ApiService.class);
    }

    private void configureRealm() {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
//        Realm.deleteRealm(realmConfig);
        // Set the default Realm configuration at the beginning.
        Realm.setDefaultConfiguration(realmConfig);
    }
    //endregion

    //region Getters
    public JobManager getLocalJobManager() {
        if (localJobManager == null) {
            configureJobManagers();
        }
        return localJobManager;
    }

    public JobManager getNetworkJobManager() {
        if (networkJobManager == null) {
            configureJobManagers();
        }

        return networkJobManager;
    }

    public ApiService getApiService() {
        if (apiService == null) {
            configureCookieJar();
            configureApiService();
        }

        return apiService;
    }
    //endregion

    public void clearCookies() {
        configureCookieJar();
        cookieJar.clear();
        configureApiService();
    }
}

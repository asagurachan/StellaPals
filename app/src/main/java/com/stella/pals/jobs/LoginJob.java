package com.stella.pals.jobs;

import com.crashlytics.android.Crashlytics;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;
import com.stella.pals.events.FailedEvent;
import com.stella.pals.events.SuccessEvent;
import com.stella.pals.views.base.BaseApplication;
import com.stella.pals.views.global.Global;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DJ on 5/12/15.
 * Project: Stella Pals
 */
public class LoginJob extends Job {

    private String username;
    private String password;

    public LoginJob(String username, String password) {
        super(new Params(Global.JOB_PRORITY_1).requireNetwork().persist());
        this.username = username;
        this.password = password;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        BaseApplication.getInstance().getApiService().login(username, password, "1").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Document document = Jsoup.parse(response.body().string());
                    if (document.getElementById("topLogin") != null) {
                        // Invalid username or password
                        EventBus.getDefault().post(new FailedEvent(JobCodes.LOGIN));
                    } else {
                        // Logged in
                        EventBus.getDefault().post(new SuccessEvent(JobCodes.LOGIN));
                    }
                } catch (IOException e) {
                    Crashlytics.logException(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onCancel() {

    }
}

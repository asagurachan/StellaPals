package com.stella.pals.jobmanager;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;
import com.stella.pals.backend.api.APIConstants;
import com.stella.pals.backend.api.APIManager;
import com.stella.pals.backend.api.APIParams;
import com.stella.pals.frontend.global.Global;

import de.greenrobot.event.EventBus;

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
        new APIManager(getApplicationContext(), APIConstants.LOGIN,
                APIParams.login(username, password)) {
            @Override
            public void onPostTask() {
                EventBus.getDefault().post(mDocumentSoup);
            }

            @Override
            public void onPostFailTask() {
                EventBus.getDefault().post(false);
            }
        }.setShowOverlay(true)
                .execute();
    }

    @Override
    protected void onCancel() {

    }
}

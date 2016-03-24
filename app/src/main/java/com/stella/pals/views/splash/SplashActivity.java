package com.stella.pals.views.splash;

import com.stella.pals.R;
import com.stella.pals.events.IntentEvent;
import com.stella.pals.interfaces.MyPrefs_;
import com.stella.pals.jobs.LoginJob;
import com.stella.pals.views.MainActivity_;
import com.stella.pals.views.base.BaseActivity;
import com.stella.pals.views.base.BaseApplication;
import com.stella.pals.views.login.LoginActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.sharedpreferences.Pref;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {

    @App
    BaseApplication application;

    @Pref
    MyPrefs_ myPrefs;

    @AfterViews
    protected void endSplash() {
        if (myPrefs.lastUser().exists()) {
            application.getNetworkJobManager().addJob(new LoginJob(myPrefs.lastUser().get(), myPrefs.pass().get()));
            onIntentEvent(new IntentEvent(MainActivity_.intent(this), true));
        } else {
            onIntentEvent(new IntentEvent(LoginActivity_.intent(this), true));
        }
    }
}

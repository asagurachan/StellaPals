package com.stella.pals.frontend.splash;

import com.stella.pals.R;
import com.stella.pals.event.IntentEvent;
import com.stella.pals.frontend.MainActivity_;
import com.stella.pals.frontend.base.BaseActivity;
import com.stella.pals.frontend.login.LoginActivity_;
import com.stella.pals.interfaces.MyPrefs_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.sharedpreferences.Pref;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {

    @Pref
    MyPrefs_ myPrefs;

    @AfterViews
    protected void endSplash() {
        if (myPrefs.lastUser().exists()) {
            onIntentEvent(new IntentEvent(MainActivity_.intent(this), true));
        } else {
            onIntentEvent(new IntentEvent(LoginActivity_.intent(this), true));
        }
    }
}

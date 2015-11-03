package com.stella.pals.frontend.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.stella.pals.R;
import com.stella.pals.frontend.MainActivity;
import com.stella.pals.frontend.base.BaseActivity;
import com.stella.pals.frontend.global.Global;
import com.stella.pals.frontend.login.LoginActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Global.init(this);
        if (Global.COOKIES.isEmpty() || !Global.COOKIES.containsKey("last_user")) {
            Intent intent = new Intent(this, LoginActivity.class);
            ActivityCompat.startActivity(this, intent, null);
            ActivityCompat.finishAffinity(this);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            ActivityCompat.startActivity(this, intent, null);
            ActivityCompat.finishAffinity(this);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initListeners() {

    }
}

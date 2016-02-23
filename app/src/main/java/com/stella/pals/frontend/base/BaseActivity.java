package com.stella.pals.frontend.base;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.TextView;

import com.stella.pals.R;
import com.stella.pals.event.IntentEvent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by DJ on 13/8/15.
 * StellaPals
 */
@EActivity
public abstract class BaseActivity extends AppCompatActivity {

    public static final String TAG = BaseActivity.class.getSimpleName();
    protected Toolbar mToolbar;

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    protected void setActionBarIcon(int iconRes) {
        mToolbar.setNavigationIcon(iconRes);
    }

    @AfterViews
    protected void init() {
        initVariables();
//        initLayout(savedInstanceState);
        initListeners();
    }

    protected void initVariables() {}

    protected void initListeners() {}

    @AfterViews
    protected void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            setSupportActionBar(mToolbar);

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                mToolbar.setTitleTextColor(getResources().getColor(R.color.black));
            } else {
                mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black));
            }

            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary_dark));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary_dark));
        }
    }

    @Subscribe
    public void onIntentEvent(IntentEvent event) {
        event.intentBuilder.start();
        if (event.finishActivity) {
            finish();
        }
    }

    protected void showProgressOverlay(String message) {
        ViewGroup vg = (ViewGroup)getWindow().getDecorView().getRootView();
        LayoutInflater.from(this).inflate(R.layout.overlay_progress, vg, true);

        if (message != null) {
            TextView tvProgress = (TextView) findViewById(R.id.tv_progress_text);
            tvProgress.setText(message);
        }
    }

    protected void dismissProgressOverlay() {
        View overlayProgress = findViewById(R.id.overlay_progress_container);
        if (overlayProgress != null) {
            ((ViewManager)overlayProgress.getParent()).removeView(overlayProgress);
        }
    }

}

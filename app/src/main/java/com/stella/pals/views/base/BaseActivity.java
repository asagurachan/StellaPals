package com.stella.pals.views.base;

import android.app.ActivityOptions;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.TextView;

import com.stella.pals.R;
import com.stella.pals.events.FailedEvent;
import com.stella.pals.events.IntentEvent;
import com.stella.pals.events.SuccessEvent;

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

    //region Override Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupWindowAnimations();
    }

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
    //endregion

    protected void setActionBarIcon(int iconRes) {
        mToolbar.setNavigationIcon(iconRes);
    }

    //region Initialization
    @AfterViews
    protected void init() {
        initToolbar();
        initVariables();
        initListeners();
    }

    protected void initVariables() {}

    protected void initListeners() {}

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
            mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black));

            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary_dark));
        }
    }
    //endregion

    //region Events
    @Subscribe
    public void onIntentEvent(IntentEvent event) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            event.intentBuilder.withOptions(ActivityOptions.makeSceneTransitionAnimation(this).toBundle()).start();
            if (event.finishActivity) {
                finishAfterTransition();
            }
        } else {
            event.intentBuilder.start();
            if (event.finishActivity) {
                finish();
            }
        }
    }

    @Subscribe
    protected void onSuccessEvent(SuccessEvent event) {}

    @Subscribe
    protected void onFailedEvent(FailedEvent event) {}
    //endregion

    //region Overlay
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
    //endregion

    private void setupWindowAnimations() {
        // Only Lollipop and up works
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setAllowEnterTransitionOverlap(false);
//            getWindow().setAllowReturnTransitionOverlap(false);
            getWindow().setEnterTransition(new Slide(Gravity.END).setStartDelay(250));
            getWindow().setExitTransition(new Slide(Gravity.START));
        }
    }

}

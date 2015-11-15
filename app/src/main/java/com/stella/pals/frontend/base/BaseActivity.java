package com.stella.pals.frontend.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.stella.pals.R;

/**
 * Created by DJ on 13/8/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static final String TAG = BaseActivity.class.getSimpleName();
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);

            try {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            } catch (NullPointerException e) {
                Log.i(TAG, getString(R.string.em_unable_to_get_action_bar));
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                mToolbar.setTitleTextColor(getResources().getColor(R.color.black));
            } else {
                mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black));
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary_dark));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary_dark));
        }

        init(savedInstanceState);
    }

    protected abstract int getLayoutResource();

    protected void setActionBarIcon(int iconRes) {
        mToolbar.setNavigationIcon(iconRes);
    }

    protected void init(Bundle savedInstanceState) {
        initVariables();
        initLayout(savedInstanceState);
        initListeners();
    }

    protected void initLayout(Bundle savedInstanceState) {
    }

    protected abstract void initVariables();
    protected void initListeners() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}

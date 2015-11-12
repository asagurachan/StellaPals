package com.stella.pals.frontend.thread;

import android.os.AsyncTask;

import com.stella.pals.R;
import com.stella.pals.backend.api.APIConstants;
import com.stella.pals.backend.api.APIManager;
import com.stella.pals.backend.api.APIParams;
import com.stella.pals.custom.SwipeRefreshLayoutBottom;
import com.stella.pals.frontend.base.BaseActivity;


public class ThreadActivity extends BaseActivity {

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_thread;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initListeners() {
        final SwipeRefreshLayoutBottom swipeRefreshLayoutBottom = (SwipeRefreshLayoutBottom) findViewById(R.id.refresh_layout);

        swipeRefreshLayoutBottom.setOnRefreshListener(new SwipeRefreshLayoutBottom.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayoutBottom.setRefreshing(false);
            }
        });
    }

    private void getMessages(int page) {
        new APIManager(this, APIConstants.PM, APIParams.messages("", page), page == 1) {
            @Override
            public void onPostTask() {

                super.onPostTask();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}

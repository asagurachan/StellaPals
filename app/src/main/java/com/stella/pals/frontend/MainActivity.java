package com.stella.pals.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.stella.pals.R;
import com.stella.pals.frontend.adapter.MessageGroupAdapter;
import com.stella.pals.frontend.base.BaseActivity;
import com.stella.pals.frontend.global.Global;
import com.stella.pals.frontend.thread.ThreadActivity;

public class MainActivity extends BaseActivity {

    private ListView mLvMessageGroups;
    private MessageGroupAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariables() {
        mLvMessageGroups = (ListView) findViewById(R.id.lv_message_groups);
        mAdapter = new MessageGroupAdapter(this);

        mLvMessageGroups.setAdapter(mAdapter);
        Global.updateMessageGroups(1, mAdapter);
    }

    @Override
    protected void initListeners() {
        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "Refreshing");

                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        mLvMessageGroups.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount) {
                    Global.updateMessageGroups(Global.lastPage, mAdapter);
                }
            }
        });
        mLvMessageGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ThreadActivity.class);
                intent.putExtra("thread_id", Global.messageGroups.get(position).getUser().getId());
                ActivityCompat.startActivity(MainActivity.this, intent, null);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

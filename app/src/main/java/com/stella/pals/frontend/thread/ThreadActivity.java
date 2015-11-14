package com.stella.pals.frontend.thread;

import android.os.AsyncTask;
import android.widget.ListView;

import com.stella.pals.R;
import com.stella.pals.backend.api.APIConstants;
import com.stella.pals.backend.api.APIManager;
import com.stella.pals.backend.api.APIParams;
import com.stella.pals.backend.model.Message;
import com.stella.pals.custom.SwipeRefreshLayoutBottom;
import com.stella.pals.frontend.adapter.MessageAdapter;
import com.stella.pals.frontend.base.BaseActivity;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;


public class ThreadActivity extends BaseActivity {

    private String mThreadId;
    private boolean mLoadingMessages = false;
    private int mCurPage = 1;
    private SwipeRefreshLayoutBottom mSwipeRefreshLayoutBottom;
    private ArrayList<Message> mMessages = new ArrayList<Message>();
    private MessageAdapter mAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_thread;
    }

    @Override
    protected void initVariables() {
        mThreadId = getIntent().getExtras().getString("thread_id");

        ListView lvMessages = (ListView) findViewById(R.id.lv_messages);
        mAdapter = new MessageAdapter(this, mMessages);
        lvMessages.setAdapter(mAdapter);
    }

    @Override
    protected void initListeners() {
        mSwipeRefreshLayoutBottom = (SwipeRefreshLayoutBottom) findViewById(R.id.refresh_layout);

        mSwipeRefreshLayoutBottom.setOnRefreshListener(new SwipeRefreshLayoutBottom.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMessages();
            }
        });
    }

    private void getMessages() {
        if (!mLoadingMessages) {
            mLoadingMessages = true;
            new APIManager(this, APIConstants.PM, APIParams.messages(mThreadId, mCurPage), mCurPage == 1) {
                @Override
                public void onPostTask() {
                    mCurPage++;
                    mLoadingMessages = false;

                    Elements conversation = mDocumentSoup.getElementById("conversation").children();
                    int size = conversation.size();
                    for (int x = 0; x < size; x++) {
                        Element messageE = conversation.get(x);
                        String username = messageE.getElementsByClass("msg_username").get(0).ownText();
                        String fullMessage = messageE.getElementsByClass("msg_body").get(0).html();

                        Message message = new Message(fullMessage, false);
                        mMessages.add(mMessages.size(), message);
                    }
                    mAdapter.notifyDataSetChanged();
                    super.onPostTask();
                    mSwipeRefreshLayoutBottom.setRefreshing(false);
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }
}

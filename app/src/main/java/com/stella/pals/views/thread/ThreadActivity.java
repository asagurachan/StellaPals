package com.stella.pals.views.thread;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.stella.pals.R;
import com.stella.pals.models.Message;
import com.stella.pals.views.adapter.MessageAdapter;
import com.stella.pals.views.base.BaseActivity;
import com.stella.pals.views.custom.SwipeRefreshLayoutBottom;

import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;

@EActivity(R.layout.activity_thread)
public class ThreadActivity extends BaseActivity {

    private String mThreadId;
    private boolean mLoadingMessages = false;
    private int mCurPage = 1;
    private SwipeRefreshLayoutBottom mSwipeRefreshLayoutBottom;
    private ArrayList<Message> mMessages = new ArrayList<Message>();
    private MessageAdapter mAdapter;
    private ListView mLvMessages;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mThreadId = savedInstanceState.getString("thread_id");
        } else {
            mThreadId = getIntent().getExtras().getString("thread_id");
            getMessages();
        }
    }

    @Override
    protected void initVariables() {
        mLvMessages = (ListView) findViewById(R.id.lv_messages);
        mAdapter = new MessageAdapter(this, mMessages);
        mLvMessages.setAdapter(mAdapter);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        mSwipeRefreshLayoutBottom = (SwipeRefreshLayoutBottom) findViewById(R.id.refresh_layout);

        mSwipeRefreshLayoutBottom.setOnRefreshListener(new SwipeRefreshLayoutBottom.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMessages();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("thread_id", mThreadId);
        super.onSaveInstanceState(outState);
    }

    private void getMessages() {
        if (!mLoadingMessages) {
            mLoadingMessages = true;
//            new APIManager(this, APIConstants.PM, APIParams.messages(mThreadId, mCurPage), mCurPage == 1) {
//                @Override
//                public void onPostTask() {
//                    mCurPage++;
//                    mLoadingMessages = false;
//
//                    Elements conversation = documentSoup.getElementById("conversation").children();
//                    int size = conversation.size();
//                    for (int x = size - 1; x >= 0; x--) {
//                        Element messageE = conversation.get(x);
//                        Element messageBody = messageE.getElementsByClass("msg_body").get(0);
//                        String username = messageE.getElementsByClass("msg_username").get(0).ownText();
//                        String fullMessage = messageBody.html();
//                        String thumb = messageE.getElementsByClass("msg_user_thumb").get(0).getElementsByTag("img").get(0).attr("src");
//                        String time = messageE.getElementsByClass("pm_time").get(0).text();
//                        Elements emailProtection = messageBody.getElementsByClass("__cf_email__");
//
//                        if (!emailProtection.isEmpty()) {
//                            fullMessage = fullMessage.replace(emailProtection.get(0).parent().outerHtml(), StringUtil.removeEmailProtection(emailProtection.get(0).parent()));
//                        }
//
//                        if (thumb.isEmpty()) {
//                            thumb = messageE.getElementsByClass("msg_user_thumb").get(0).getElementsByTag("img").get(1).attr("src");
//                        }
//                        boolean unread = false;
//
//                        if (messageE.hasClass("pm_unread")) {
//                            unread = true;
//                        }
//
//                        Message message = new Message(fullMessage, thumb, Global.getUsername().equalsIgnoreCase(username), time, unread);
//                        mMessages.add(mMessages.size(), message);
//                    }
//                    mAdapter.notifyDataSetChanged();
//                    super.onPostTask();
//                    mSwipeRefreshLayoutBottom.setRefreshing(false);
//                    if (mCurPage == 2) {
//                        mLvMessages.setSelection(size - 1);
//                    }
//                }
//            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }
}

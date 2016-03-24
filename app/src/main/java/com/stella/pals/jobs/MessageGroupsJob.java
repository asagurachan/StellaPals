package com.stella.pals.jobs;

import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;
import com.stella.pals.models.MessageGroup;
import com.stella.pals.models.User;
import com.stella.pals.views.base.BaseApplication;
import com.stella.pals.views.base.BaseApplication_;
import com.stella.pals.views.global.Global;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import io.realm.Realm;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DJ on 5/12/15.
 * Project: Stella Pals
 */
public class MessageGroupsJob extends Job {

    private static final String TAG = MessageGroupsJob.class.getSimpleName();
    private int page;
    private boolean loadAll = false;

    public MessageGroupsJob(boolean loadAll) {
        super(new Params(Global.JOB_PRORITY_1).requireNetwork().persist());
        this.loadAll = loadAll;
        this.page = 1;
    }

    public MessageGroupsJob(int page) {
        super(new Params(Global.JOB_PRORITY_1).requireNetwork().persist());
        this.page = page;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        BaseApplication.getInstance().getApiService().getMessageGroups("paged", page).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Document document = Jsoup.parse(response.body().string());

                    Realm realm = Realm.getInstance(getApplicationContext());

                    Elements threads = document.getElementById("threads_left").children();
                    int size = threads.size();
                    for (int x = 0; x < size; x++) {
                        try {
                            Element currentThread = threads.get(x);
//                            Elements thumbnail = currentThread.getElementsByClass("th_user_thumb");
                            String id = currentThread.id().replace("thread_", "");

                            Elements userInfo = currentThread.getElementsByClass("tui_username");
                            String username = userInfo.get(0).getElementsByClass("tui_el").get(0).ownText();
                            String thumb = currentThread.getElementsByClass("th_user_thumb").get(0).getElementsByClass("thumb").get(0).attr("src");
                            if (thumb.isEmpty()) {
                                thumb = currentThread.getElementsByClass("th_user_thumb").get(0).getElementsByClass("thumb").get(1).attr("src");
                            }
                            if (thumb.startsWith("//")) {
                                thumb = "http:" + thumb;
                            }
                            String time = currentThread.getElementsByClass("tui_last_time").text();
                            String ageStr = userInfo.get(0).getElementsByClass("tui_age").get(0).text();
                            int age = 0;
                            if (!ageStr.trim().isEmpty()) {
                                age = Integer.parseInt(ageStr.replace(", ", ""));
                            }
                            int sex = currentThread.getElementsByClass("th_user_thumb").get(0).child(0).hasClass("female") ? User.FEMALE : User.MALE;
                            User user = new User(id, username, thumb, age, sex);
                            boolean newMessage = currentThread.hasClass("new");

                            String message = currentThread.getElementsByClass("th_snippet").get(0).ownText();

                            MessageGroup messageGroup = new MessageGroup(user, message, time, newMessage);
                            realm.beginTransaction();
                            realm.copyToRealmOrUpdate(messageGroup);
                            realm.commitTransaction();
                        } catch (NumberFormatException e) {
                            Log.e(TAG, e.getMessage(), e);
                        }
                    }

                    // Take the last page and add jobs from 2 till last page
                    if (loadAll) {
                        Elements elements = document.getElementsByClass("fa-forward");

                        int lastPage = 1;
                        if (elements.size() > 0) {
                            String href = elements.first().attr("title", "Last page").parent().attr("href");
                            int positionOfPageEq = href.indexOf("page=");
                            lastPage = Integer.parseInt(href.substring(positionOfPageEq + 5));

                            if (lastPage > 1) {
                                for (int i = 2; i < lastPage; i++) {
                                    BaseApplication_.getInstance().getNetworkJobManager().addJob(new MessageGroupsJob(i));
                                }
                            }
                        }
                    }
                } catch (IOException|NumberFormatException e) {
                    Crashlytics.logException(e);
                    Log.e(TAG, e.getMessage(), e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onCancel() {

    }
}

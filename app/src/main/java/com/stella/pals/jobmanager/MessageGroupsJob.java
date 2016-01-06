package com.stella.pals.jobmanager;

import android.util.Log;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;
import com.stella.pals.backend.api.APIConstants;
import com.stella.pals.backend.api.APIManager;
import com.stella.pals.backend.api.APIParams;
import com.stella.pals.backend.model.MessageGroup;
import com.stella.pals.backend.model.User;
import com.stella.pals.frontend.base.BaseApplication;
import com.stella.pals.frontend.global.Global;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.greenrobot.event.EventBus;

/**
 * Created by DJ on 5/12/15.
 * Project: Stella Pals
 */
public class MessageGroupsJob extends Job {

    private static final String TAG = MessageGroupsJob.class.getSimpleName();
    private boolean network;
    private int page;

    public MessageGroupsJob(boolean network, int page) {
        super(new Params(Global.JOB_PRORITY_1).setRequiresNetwork(network).persist());
        this.network = network;
        this.page = page;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        if (network) {
            new APIManager(BaseApplication.getInstance(), APIConstants.PM, APIParams.messageGroup(page), true) {
                @Override
                public void onPostTask() {
                    Elements threads = mDocumentSoup.getElementById("threads_left").children();
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
                            if (messageGroup.exists()) {
                                messageGroup.update();
                            } else {
                                messageGroup.save();
                            }
                            Global.messageGroups.add(messageGroup);
                        } catch (NumberFormatException e) {
                            Log.e(TAG, e.getMessage(), e);
                        }
                    }

                    EventBus.getDefault().post(true);

                    Global.lastPage++;
                    Global.updatingMessageGroups = false;

                    super.onPostTask();
                }
            }.execute();
        } else {
            BaseApplication.getInstance().getNetworkJobManager().addJob(new MessageGroupsJob(true, 1));
        }
    }

    @Override
    protected void onCancel() {

    }
}

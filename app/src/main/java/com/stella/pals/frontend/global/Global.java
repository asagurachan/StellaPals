package com.stella.pals.frontend.global;

import com.stella.pals.backend.model.MessageGroup;

import java.util.ArrayList;

/**
 * Created by DJ on 13/8/15.
 * Project: Stella Pals
 */
public class Global {

    private static final String TAG = Global.class.getSimpleName();

    public static final boolean PRODUCTION = false;
    public static ArrayList<MessageGroup> messageGroups = new ArrayList<MessageGroup>();
    public static boolean randomTest = false;

    // Job Priority
    public static final int JOB_PRORITY_1 = 1;


//    public static void updateMessageGroups(int page, final BaseAdapter adapter) {
//        if (!updatingMessageGroups) {
//            updatingMessageGroups = true;
//            new APIManager(BaseApplication.getInstance(), APIConstants.PM, APIParams.messageGroup(page), true) {
//                @Override
//                public void onPostTask() {
//                    Elements threads = documentSoup.getElementById("threads_left").children();
//                    int size = threads.size();
//                    for (int x = 0; x < size; x++) {
//                        try {
//                            Element currentThread = threads.get(x);
////                            Elements thumbnail = currentThread.getElementsByClass("th_user_thumb");
//                            String id = currentThread.id().replace("thread_", "");
//
//                            Elements userInfo = currentThread.getElementsByClass("tui_username");
//                            String username = userInfo.get(0).getElementsByClass("tui_el").get(0).ownText();
//                            String thumb = currentThread.getElementsByClass("th_user_thumb").get(0).getElementsByClass("thumb").get(0).attr("src");
//                            if (thumb.isEmpty()) {
//                                thumb = currentThread.getElementsByClass("th_user_thumb").get(0).getElementsByClass("thumb").get(1).attr("src");
//                            }
//                            String time = currentThread.getElementsByClass("tui_last_time").text();
//                            String ageStr = userInfo.get(0).getElementsByClass("tui_age").get(0).text();
//                            int age = 0;
//                            if (!ageStr.trim().isEmpty()) {
//                                age = Integer.parseInt(ageStr.replace(", ", ""));
//                            }
//                            int sex = currentThread.getElementsByClass("th_user_thumb").get(0).child(0).hasClass("female") ? User.FEMALE : User.MALE;
//                            User user = new User(id, username, thumb, age, sex);
//                            boolean newMessage = currentThread.hasClass("new");
//
//                            String message = currentThread.getElementsByClass("th_snippet").get(0).ownText();
//                            MessageGroup messageGroup = new MessageGroup(user, message, time, newMessage);
//                            messageGroups.add(messageGroup);
//                        } catch (NumberFormatException e) {
//                            Log.e(TAG, e.getMessage(), e);
//                        }
//                    }
//
//                    if (adapter != null) {
//                        adapter.notifyDataSetChanged();
//                    }
//                    Global.lastPage++;
//                    updatingMessageGroups = false;
//
//                    super.onPostTask();
//                }
//            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//        }
//    }

}

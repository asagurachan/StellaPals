package com.stella.pals.frontend.global;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.BaseAdapter;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.stella.pals.backend.api.APIConstants;
import com.stella.pals.backend.api.APIManager;
import com.stella.pals.backend.api.APIParams;
import com.stella.pals.backend.model.Cookie;
import com.stella.pals.backend.model.Cookie_Table;
import com.stella.pals.backend.model.MessageGroup;
import com.stella.pals.backend.model.User;
import com.stella.pals.frontend.base.BaseApplication;
import com.stella.pals.utils.SharedPreferencesUtil;
import com.stella.pals.utils.StringUtil;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by DJ on 13/8/15.
 * Project: Stella Pals
 */
public class Global {

    private static final String TAG = Global.class.getSimpleName();

    public static final boolean PRODUCTION = false;
    public static ImageLoader IMAGE_LOADER;
    public static ArrayList<MessageGroup> messageGroups = new ArrayList<MessageGroup>();
    public static int lastPage = 1;
    private static String username = "";
    public static boolean updatingMessageGroups = false;
    public static boolean randomTest = false;

    // Job Priority
    public static final int JOB_PRORITY_1 = 1;

    public static void init(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).build();
        ImageLoader.getInstance().init(config);
        IMAGE_LOADER = ImageLoader.getInstance();
        SharedPreferencesUtil.init(context);
    }

    public static String getUsername() {
        if (StringUtil.isEmpty(username)) {
            Cookie lastUser = SQLite.select().from(Cookie.class).where(Cookie_Table.key.eq("last_user")).querySingle();
            username = lastUser.getValue();
        }

        return username;
    }

    public static void setCookies(Map<String, String> cookies) {
        for (Map.Entry entry : cookies.entrySet()) {
            new Cookie(entry);
        }
    }

    public static void updateMessageGroups(int page, final BaseAdapter adapter) {
        if (!updatingMessageGroups) {
            updatingMessageGroups = true;
            new APIManager(BaseApplication.getInstance(), APIConstants.PM, APIParams.messageGroup(page), true) {
                @Override
                public void onPostTask() {
                    Elements threads = documentSoup.getElementById("threads_left").children();
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
                            messageGroups.add(messageGroup);
                        } catch (NumberFormatException e) {
                            Log.e(TAG, e.getMessage(), e);
                        }
                    }

                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                    Global.lastPage++;
                    updatingMessageGroups = false;

                    super.onPostTask();
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

}

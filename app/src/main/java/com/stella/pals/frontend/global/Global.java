package com.stella.pals.frontend.global;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.BaseAdapter;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.stella.pals.backend.api.APIConstants;
import com.stella.pals.backend.api.APIManager;
import com.stella.pals.backend.api.APIParams;
import com.stella.pals.backend.model.MessageGroup;
import com.stella.pals.backend.model.User;
import com.stella.pals.frontend.base.BaseApplication;
import com.stella.pals.utils.SharedPreferencesUtil;
import com.stella.pals.utils.StringUtil;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DJ on 13/8/15.
 * Project: Stella Pals
 */
public class Global {

    public static final boolean PRODUCTION = false;
    public static ImageLoader IMAGE_LOADER;
    public static Map<String, String> COOKIES;
    public static ArrayList<MessageGroup> messageGroups = new ArrayList<MessageGroup>();
    public static int lastPage= 1;
    private static boolean updatingMessageGroups = false;

    public static void init(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).build();
        ImageLoader.getInstance().init(config);
        IMAGE_LOADER = ImageLoader.getInstance();
        SharedPreferencesUtil.init(context);
        initCookiesFromPreferences();
    }

    public static void setCookies(Map<String, String> cookies) {
        COOKIES = cookies;
        SharedPreferencesUtil.putString("cookies", cookiesToString());
    }

    public static void initCookiesFromPreferences() {
        COOKIES = cookiesStringToMap();
    }

    private static String cookiesToString() {
        StringBuilder cookiesString = new StringBuilder();
        for (Map.Entry entry : COOKIES.entrySet()) {
            cookiesString.append(entry.getKey());
            cookiesString.append(":");
            cookiesString.append(entry.getValue());
            cookiesString.append(";");
        }

        return cookiesString.toString();
    }

    private static Map<String, String> cookiesStringToMap() {
        HashMap<String, String> cookies = new HashMap<String, String>();

        String cookiesString = SharedPreferencesUtil.getString("cookies");
        if (StringUtil.isNotEmpty(cookiesString)) {
            String[] cookiesSplit = cookiesString.split(";");

            for (String cur : cookiesSplit) {
                if (StringUtil.isNotEmpty(cur)) {
                    String[] keyValue = cur.split(":");
                    cookies.put(keyValue[0], keyValue[1]);
                }
            }
        }

        return cookies;
    }

    public static void updateMessageGroups(int page, final BaseAdapter adapter) {
        if (!updatingMessageGroups) {
            updatingMessageGroups = true;
            new APIManager(BaseApplication.getAppContext(), APIConstants.PM, APIParams.messageGroup(page), true) {
                @Override
                public void onPostTask() {
                    Elements threads = mDocumentSoup.getElementById("threads_left").children();
                    int size = threads.size();
                    for (int x = 0; x < size; x++) {
                        Element currentThread = threads.get(x);
                        Elements thumbnail = currentThread.getElementsByClass("th_user_thumb");
                        String id = currentThread.id().replace("thread_", "");

                        Elements userInfo = currentThread.getElementsByClass("tui_username");
                        String username = userInfo.get(0).getElementsByClass("tui_el").get(0).ownText();
                        String thumb = currentThread.getElementsByClass("th_user_thumb").get(0).getElementsByClass("thumb").get(0).attr("src");
                        if (thumb.isEmpty()) {
                            thumb = currentThread.getElementsByClass("th_user_thumb").get(0).getElementsByClass("thumb").get(1).attr("src");
                        }
                        int age = Integer.parseInt(userInfo.get(0).getElementsByClass("tui_age").get(0).text().replace(", ", ""));
                        byte sex = currentThread.getElementsByClass("th_user_thumb").get(0).child(0).hasClass("female") ? User.FEMALE : User.MALE;
                        User user = new User(id, username, thumb, age, sex);

                        String message = currentThread.getElementsByClass("th_snippet").get(0).ownText();
                        MessageGroup messageGroup = new MessageGroup(user, message);
                        messageGroups.add(messageGroup);
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

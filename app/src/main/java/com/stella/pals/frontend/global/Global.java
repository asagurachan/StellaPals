package com.stella.pals.frontend.global;

import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.stella.pals.utils.SharedPreferencesUtil;
import com.stella.pals.utils.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DJ on 13/8/15.
 */
public class Global {

    public static final boolean PRODUCTION = false;
    public static ImageLoader IMAGE_LOADER;
    public static Map<String, String> COOKIES;

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
            cookiesString.append(entry.getKey() + ":" + entry.getValue() + ";");
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

}

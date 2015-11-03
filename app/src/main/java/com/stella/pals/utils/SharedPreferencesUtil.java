package com.stella.pals.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by DJ on 20/8/15.
 * Project: Stella Pals
 */
public class SharedPreferencesUtil {

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public static void init(Context context) {
        preferences = context.getSharedPreferences("stella_pals_db", Context.MODE_PRIVATE);
    }

    public static void putString(String key, String value) {
        editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(String key) {
        return preferences.getString(key, "");
    }

    public static void putStringSet(String key, Set<String> value) {
        editor = preferences.edit();
        editor.putStringSet(key, value);
        editor.apply();
    }

    public static Set<String> getStringSet(String key) {
        return preferences.getStringSet(key, new HashSet<String>());
    }

    public static void putInteger(String key, int value) {
        editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInteger(String key) {
        return preferences.getInt(key, 0);
    }

    public static void putFloat(String key, float value) {
        editor = preferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static float getFloat(String key) {
        return preferences.getFloat(key, 0);
    }

    public static void putLong(String key, long value) {
        editor = preferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static long getLong(String key) {
        return preferences.getLong(key, 0);
    }

    public static void putBoolean(String key, boolean value) {
        editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

}


package com.stella.pals.backend.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DJ on 20/8/15.
 * Project: Stella Pals
 */
public class APIParams {

    public static final byte NOT_USED = -1;

    public static Map<String, String> login(String username, String password) {
        Map<String, String> values = new HashMap<String, String>(4);
        values.put("action", "login");
        values.put("login", username.trim());
        values.put("password", password);
        values.put("auto_login", "1");

        return values;
    }

    public static Map<String, String> signup(String username, String email, String password,
                                             String day, String month, String year, String country) {
        Map<String, String> values = new HashMap<String, String>(8);
        values.put("step", "1");
        values.put("username", username);
        values.put("email", email);
        values.put("password", password);
        values.put("day", day);
        values.put("month", month);
        values.put("year", year);
        values.put("country", country);

        return values;
    }

    public static Map<String, String> messageGroup(int page) {
        Map<String, String> values = new HashMap<String, String>(2);
        values.put("view", "paged");
        values.put("page", String.valueOf(page));

        return values;
    }

    public static Map<String, String> messages(String thread, int page) {
        Map<String, String> values = new HashMap<String, String>(2);
        values.put("thread_id", thread);
        values.put("page", String.valueOf(page));

        return values;
    }

}

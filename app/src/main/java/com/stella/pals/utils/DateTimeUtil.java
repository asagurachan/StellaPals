package com.stella.pals.utils;

import java.util.Date;

/**
 * Created by Asa on 2016/02/19.
 * StellaPals
 */
public class DateTimeUtil {

    public static Date convertTimeToDate(String time) {
        Date current = new Date();
        if (time.contains("hours")) {
            int startH = time.indexOf("h");
            int hours = Integer.valueOf(time.substring(0, startH).trim());
            long timeLong = 3600000 * hours;
            timeLong = current.getTime() - timeLong;
            return new Date(timeLong);
        } else if (time.contains("days")) {
            int startH = time.indexOf("d");
            int days = Integer.valueOf(time.substring(0, startH).trim());
            long timeLong = 86400000 * days;
            timeLong = current.getTime() - timeLong;
            return new Date(timeLong);
        } else if (time.contains("month")) {
            int startH = time.indexOf("month");
            int months = Integer.valueOf(time.substring(0, startH).trim());
            long timeLong = 2419200000L * months;
            timeLong = current.getTime() - timeLong;
            return new Date(timeLong);
        }

        return current;
    }

}

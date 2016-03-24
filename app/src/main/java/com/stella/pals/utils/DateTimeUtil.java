package com.stella.pals.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Asa on 2016/02/19.
 * StellaPals
 */
public class DateTimeUtil {

    public static final long MILLISECONDS_HOUR = 3600000;
    public static final long MILLISECONDS_DAY = 86400000;
    public static final long MILLISECONDS_MONTH = 2419200000L;
    public static final long MILLISECONDS_YEAR = 31536000000L;

    public static Date convertTimeToDate(String time) {
        Calendar current = Calendar.getInstance();
        current.setTime(new Date());
        current.set(Calendar.MILLISECOND, 0);
        current.set(Calendar.SECOND, 0);
        current.set(Calendar.MINUTE, 0);
        if (time.contains("hour")) {
            int startH = time.indexOf("hour");
            int hours = Integer.valueOf(time.substring(0, startH).trim());
            long timeLong = MILLISECONDS_HOUR * hours;
            timeLong = current.getTimeInMillis() - timeLong;
            return new Date(timeLong);
        } else if (time.contains("day")) {
            int startH = time.indexOf("day");
            int days = Integer.valueOf(time.substring(0, startH).trim());
            long timeLong = MILLISECONDS_DAY * days;
            timeLong = current.getTimeInMillis() - timeLong;
            return new Date(timeLong);
        } else if (time.contains("month")) {
            int startH = time.indexOf("month");
            int months = Integer.valueOf(time.substring(0, startH).trim());
            long timeLong = MILLISECONDS_MONTH * months;
            timeLong = current.getTimeInMillis() - timeLong;
            return new Date(timeLong);
        } else if (time.contains("year")) {
            int startH = time.indexOf("year");
            int years = Integer.valueOf((time.substring(0, startH).trim()));
            long timeLong = MILLISECONDS_YEAR * years;
            timeLong = current.getTimeInMillis() - timeLong;
            return new Date(timeLong);
        }

        return current.getTime();
    }

}

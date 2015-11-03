package com.stella.pals.utils;

/**
 * Created by DJ on 20/8/15.
 * Project: Stella Pals
 */
public class StringUtil {

    /**
     * @param str Stirng to be checked if it is empty
     * @return Returns true if |str| is empty or null
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * @param str String to be checked if it is not empty
     * @return Returns true if |str| is not empty or null
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }


}

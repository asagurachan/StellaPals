package com.stella.pals.utils;

import org.jsoup.nodes.Element;

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

    public static String removeEmailProtection(Element emailProtection) {

        String emailHash = emailProtection.children().get(0).attr("data-cfemail");
        String hash = emailProtection.children().get(1).attr("data-cfhash");
        String email = "";
        int r = Integer.parseInt(emailHash.substring(0, 2), 16);
        for (int n = 2; n < emailHash.length(); n+=2) {
            int letter = Integer.parseInt(emailHash.substring(n, n+2), 16);
            int v = letter^r;
            email+= (char)v;
        }

        return email;
    }

}

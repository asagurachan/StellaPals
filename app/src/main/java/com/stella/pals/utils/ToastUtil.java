package com.stella.pals.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by DJ on 20/8/15.
 * Project: Stella Pals
 */
public class ToastUtil {

    private static Toast toast;

    public static void makeShortToast(Context context, String message) {
        resetToast();
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void makeLongToast(Context context, String message) {
        resetToast();
        toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.show();
    }

    private static void resetToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

}

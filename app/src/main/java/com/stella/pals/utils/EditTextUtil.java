package com.stella.pals.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by DJ on 28/8/15.
 * Project: Stella Pals
 */
public class EditTextUtil {

    public static void setEditTextColours(Context context, EditText et, int colour, boolean clearFilter) {
//        int[][] states = new int[][] {
//                new int[] { android.R.attr.state_pressed}, // pressed
//                new int[] { android.R.attr.state_focused}, // focused
//                new int[] { android.R.attr.state_window_focused} // enabled
//        };
//
//        int[] colors = new int[] {
//                colour, // green
//                colour, // blue
//                colour  // red
//        };
//
//        ColorStateList list = new ColorStateList(states, colors);
////        et.setTextColor(list);
//        et.setBackgroundTintList(list);
//        et.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);

        et.setTextColor(colour);
        et.setHighlightColor(colour);
        setEditTextCursor(context, et, colour);
        if (clearFilter) {
            et.getBackground().clearColorFilter();
        } else {
            et.getBackground().setColorFilter(colour,
                    PorterDuff.Mode.SRC_IN);
        }
    }

    public static void setEditTextCursor(Context context, EditText et, int colour) {
        try {
            Field fCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            fCursorDrawableRes.setAccessible(true);
            int mCursorDrawableRes = fCursorDrawableRes.getInt(et);
            Field fEditor = TextView.class.getDeclaredField("mEditor");
            fEditor.setAccessible(true);
            Object editor = fEditor.get(et);
            Class<?> clazz = editor.getClass();
            Field fCursorDrawable = clazz.getDeclaredField("mCursorDrawable");
            fCursorDrawable.setAccessible(true);
            Drawable[] drawables = new Drawable[2];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                drawables[0] = ContextCompat.getDrawable(context, mCursorDrawableRes);
                drawables[1] = ContextCompat.getDrawable(context, mCursorDrawableRes);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawables[0] = context.getResources().getDrawable(mCursorDrawableRes, null);
                drawables[1] = context.getResources().getDrawable(mCursorDrawableRes, null);
            } else {
                drawables[0] = context.getResources().getDrawable(mCursorDrawableRes);
                drawables[1] = context.getResources().getDrawable(mCursorDrawableRes);
            }
            drawables[0].setColorFilter(colour, PorterDuff.Mode.SRC_IN);
            drawables[1].setColorFilter(colour, PorterDuff.Mode.SRC_IN);
            fCursorDrawable.set(editor, drawables);
        } catch (final Throwable ignored) {
        }
    }

}

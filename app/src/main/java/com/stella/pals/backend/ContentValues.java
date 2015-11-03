package com.stella.pals.backend;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

/**
 * Created by DJ on 20/8/15.
 * Project: Stella Pals
 */
public class ContentValues implements Parcelable {
    private static final String TAG = ContentValues.class.getSimpleName();

    private HashMap<String, Object> mValues;

    public ContentValues() {
        mValues = new HashMap<String, Object>(8);
    }

    public ContentValues(int size) {
        mValues = new HashMap<String, Object>(size);
    }

    public ContentValues(HashMap<String, Object> from) {
        mValues = from;
    }

    public ContentValues(ContentValues from) {
        mValues = new HashMap<String, Object>(from.mValues);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ContentValues)) {
            return false;
        }
        return mValues.equals(((ContentValues) object).mValues);
    }

    @Override
    public int hashCode() {
        return mValues.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeMap(mValues);
    }

    public static final Parcelable.Creator<ContentValues> CREATOR =
            new Parcelable.Creator<ContentValues>() {
                public ContentValues createFromParcel(Parcel in) {
                    HashMap<String, Object> values = in.readHashMap(null);
                    return new ContentValues(values);
                }

                public ContentValues[] newArray(int size) {
                    return new ContentValues[size];
                }
            };

    public void putObject(String key, Object object) {
        mValues.put(key, object);
    }

    public void putString(String key, String value) {
        mValues.put(key, value);
    }

    public Object get(String key) {
        return mValues.get(key);
    }

    public String getAsString(String key) {
        Object value = mValues.get(key);

        if (value != null) {
            if (value instanceof String) {
                return (String) value;
            } else {
                return String.valueOf(value);
            }
        }
        return null;
    }

    public String toUrlString() {
        StringBuilder sb = new StringBuilder();
        for (String key : mValues.keySet()) {
            String value = getAsString(key);
            if (sb.length() > 0) {
                sb.append('&');
            }
            sb.append(key);
            sb.append("=");
            sb.append(value);
        }
        return sb.toString();
    }
}

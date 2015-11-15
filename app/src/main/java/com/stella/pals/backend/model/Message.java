package com.stella.pals.backend.model;

import java.util.Date;

/**
 * Created by DJ on 13/11/15.
 * Project: Stella Pals
 */
public class Message {

    private String mMessage;
    private String mProfile;
    private String mTime;
    private boolean mOwnMessage;
    private boolean mUnread;
    private Date mPostDate;

    public Message(String message, String profile, boolean ownMessage, String time, boolean unread) {
        mMessage = message;
        mProfile = profile;
        mOwnMessage = ownMessage;
        mTime = time;
        mUnread = unread;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getProfile() {
        return mProfile;
    }

    public boolean isOwnMessage() {
        return mOwnMessage;
    }

    public String getTime() {
        return mTime;
    }

    public boolean isUnread() {
        return mUnread;
    }
}

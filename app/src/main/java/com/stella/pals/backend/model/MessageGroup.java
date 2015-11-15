package com.stella.pals.backend.model;

/**
 * Created by DJ on 3/11/15.
 * Project: Stella Pals
 */
public class MessageGroup {

    private User mUser;
    private String mSneakMessage;
    private String mTime;
    private boolean mNew;

    public MessageGroup(User user, String sneakMessage, String time, boolean newMessage) {
        mUser = user;
        mSneakMessage = sneakMessage;
        mTime = time;
        mNew = newMessage;
    }

    public String getSneakMessage() {
        return mSneakMessage;
    }

    public User getUser() {
        return mUser;
    }

    public String getTime() {
        return mTime;
    }

    public boolean isNew() {
        return mNew;
    }
}

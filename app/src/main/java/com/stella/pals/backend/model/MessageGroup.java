package com.stella.pals.backend.model;

/**
 * Created by DJ on 3/11/15.
 * Project: Stella Pals
 */
public class MessageGroup {

    private User mUser;
    private String mSneakMessage;

    public MessageGroup(User user, String sneakMessage) {
        mUser = user;
        mSneakMessage = sneakMessage;
    }

    public String getSneakMessage() {
        return mSneakMessage;
    }

    public User getUser() {
        return mUser;
    }
}

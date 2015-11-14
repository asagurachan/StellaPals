package com.stella.pals.backend.model;

import java.util.Date;

/**
 * Created by DJ on 13/11/15.
 * Project: Stella Pals
 */
public class Message {

    private String mMessage;
    private boolean mOwnMessage;
    private Date mPostDate;

    public Message(String message, boolean ownMessage) {
        mMessage = message;
        mOwnMessage = ownMessage;
    }

    public String getMessage() {
        return mMessage;
    }

    public boolean isOwnMessage() {
        return mOwnMessage;
    }
}

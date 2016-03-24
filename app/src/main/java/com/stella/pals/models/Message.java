package com.stella.pals.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by DJ on 13/11/15.
 * Project: Stella Pals
 */
public class Message extends RealmObject {

    @PrimaryKey
    private int id;
    private String message;
    private String profile;
    private String time;
    private boolean ownMessage;
    private boolean unread;
    private Date postDate;

    public Message() {}

    public Message(String message, String profile, boolean ownMessage, String time, boolean unread) {
        this.message = message;
        this.profile = profile;
        this.ownMessage = ownMessage;
        this.time = time;
        this.unread = unread;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getProfile() {
        return profile;
    }

    public Date getPostDate() {
        return postDate;
    }

    public boolean isOwnMessage() {
        return ownMessage;
    }

    public String getTime() {
        return time;
    }

    public boolean isUnread() {
        return unread;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setOwnMessage(boolean ownMessage) {
        this.ownMessage = ownMessage;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }
}

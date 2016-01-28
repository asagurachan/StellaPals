package com.stella.pals.backend.model;

import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.stella.pals.backend.PalsDatabase;

import java.util.Date;

/**
 * Created by DJ on 13/11/15.
 * Project: Stella Pals
 */
@Table(database = PalsDatabase.class, allFields = true)
public class Message extends BaseModel {

    @PrimaryKey(autoincrement = true)
    int id;
    String message;
    String profile;
    String time;
    boolean ownMessage;
    boolean unread;
    Date postDate;

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

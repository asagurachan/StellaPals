package com.stella.pals.backend.model;

import com.stella.pals.utils.DateTimeUtil;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by DJ on 3/11/15.
 * Project: Stella Pals
 */
public class MessageGroup extends RealmObject {

    @PrimaryKey
    private String id;
    private User user;
    private String sneakMessage;
    private String time;
    @Index
    private Date date;
    private boolean newMessage;

    public MessageGroup() {}

    public MessageGroup(User user, String sneakMessage, String time, boolean newMessage) {
        this.id = user.getId();
        this.user = user;
        this.sneakMessage = sneakMessage;
        this.time = time;
        this.newMessage = newMessage;
        this.date = DateTimeUtil.convertTimeToDate(time);
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getSneakMessage() {
        return sneakMessage;
    }

    public User getUser() {
        return user;
    }

    public String getTime() {
        return time;
    }

    public Date getDate() {
        return date;
    }

    public boolean isNewMessage() {
        return newMessage;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSneakMessage(String sneakMessage) {
        this.sneakMessage = sneakMessage;
    }

    public void setTime(String time) {
        this.time = time;
        this.date = DateTimeUtil.convertTimeToDate(time);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setNewMessage(boolean newMessage) {
        this.newMessage = newMessage;
    }

}

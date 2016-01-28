package com.stella.pals.backend.model;

import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;
import com.stella.pals.backend.PalsDatabase;

import java.util.Date;

/**
 * Created by DJ on 3/11/15.
 * Project: Stella Pals
 */
@Table(database = PalsDatabase.class, allFields = true)
public class MessageGroup extends BaseModel {

    @PrimaryKey(autoincrement = true)
    int id;
    @ForeignKey(saveForeignKeyModel = false)
    ForeignKeyContainer<User> userForeignKeyContainer;
    String sneakMessage;
    String time;
    Date date;
    boolean newMessage;

    public MessageGroup() {}

    public MessageGroup(User user, String sneakMessage, String time, boolean newMessage) {
        associateUser(user);
        this.sneakMessage = sneakMessage;
        this.time = time;
        this.newMessage = newMessage;
        convertTimeToDate(time);
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getSneakMessage() {
        return sneakMessage;
    }

    public User getUser() {
        return userForeignKeyContainer.load();
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
    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        associateUser(user);
    }

    public void setSneakMessage(String sneakMessage) {
        this.sneakMessage = sneakMessage;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setNewMessage(boolean newMessage) {
        this.newMessage = newMessage;
    }

    private void convertTimeToDate(String time) {
        Date current = new Date();
        if (time.contains("hours")) {
            int startH = time.indexOf("h");
            int hours = Integer.valueOf(time.substring(0, startH));
            long timeLong = 3600000 * hours;
            timeLong = current.getTime() - timeLong;
            date = new Date(timeLong);
        } else if (time.contains("days")) {
            int startH = time.indexOf("d");
            int days = Integer.valueOf(time.substring(0, startH));
            long timeLong = 86400000 * days;
            timeLong = current.getTime() - timeLong;
            date = new Date(timeLong);
        }
    }

    public void associateUser(User user) {
        userForeignKeyContainer = FlowManager.getContainerAdapter(User.class).toForeignKeyContainer(user);
    }
}

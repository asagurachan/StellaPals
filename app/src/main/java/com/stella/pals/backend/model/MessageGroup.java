package com.stella.pals.backend.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.stella.pals.backend.PalsDatabase;

import java.util.Date;

/**
 * Created by DJ on 3/11/15.
 * Project: Stella Pals
 */
@Table(databaseName = PalsDatabase.NAME)
public class MessageGroup extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    private int id;
    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "user_id",
                            columnType = String.class,
                            foreignColumnName = "id",
                            fieldIsPrivate = true)},
            saveForeignKeyModel = false
    )
    private User user;
    @Column
    private String sneakMessage;
    @Column
    private String time;
    @Column
    private Date date;
    @Column
    private boolean newMessage;

    public MessageGroup() {}

    public MessageGroup(User user, String sneakMessage, String time, boolean newMessage) {
        this.user = user;
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
        return user;
    }

    public String getTime() {
        return time;
    }

    public Date getDate() {
        return date;
    }

    public boolean getNewMessage() {
        return newMessage;
    }

    // Setters
    public void setId(int id) {
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
}

package com.stella.pals.models;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by DJ on 3/11/15.
 * Project: Stella Pals
 */
public class User extends RealmObject {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            MALE,
            FEMALE
    })
    public @interface SexDef {}
    public static final int MALE = 1;
    public static final int FEMALE = 2;

    @PrimaryKey
    private String id;
    private String username;
    private String name;
    private String thumb;
    private int age;
    private int sex;

    public User() {}

    public User(String id, String username, String thumb, int age, @SexDef int sex) {
        this.id = id;
        this.username = username;
//        thumb = thumb.replace("thumbs/50x50", "photos");
        this.thumb = thumb.replace("50x50", "180x180");
        this.age = age;
        this.sex = sex;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getThumb() {
        return thumb;
    }

    public int getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public void setAge(@SexDef int age) {
        this.age = age;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}

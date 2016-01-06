package com.stella.pals.backend.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.stella.pals.backend.PalsDatabase;

/**
 * Created by DJ on 3/11/15.
 * Project: Stella Pals
 */
@Table(databaseName = PalsDatabase.NAME)
public class User extends BaseModel {

    public static final int MALE = 1;
    public static final int FEMALE = 2;

    @Column
    @PrimaryKey
    private String id;
    @Column
    private String username;
    @Column
    private String name;
    @Column
    private String thumb;
    @Column
    private int age;
    @Column
    private int sex;

    public User() {}

    public User(String id, String username, String thumb, int age, int sex) {
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

    public void setAge(int age) {
        this.age = age;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}

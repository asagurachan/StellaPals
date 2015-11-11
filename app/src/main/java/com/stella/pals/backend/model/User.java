package com.stella.pals.backend.model;

/**
 * Created by DJ on 3/11/15.
 * Project: Stella Pals
 */
public class User {

    public static final byte MALE = 1;
    public static final byte FEMALE = 2;

    private String mId;
    private String mUsername;
    private String mName;
    private String mThumb;
    private int mAge;
    private byte mSex;

    public User(String id, String username, String thumb, int age, byte sex) {
        mId = id;
        mUsername = username;
        mThumb = thumb.replace("50x50", "180x180");
        mAge = age;
        mSex = sex;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getmUsername() {
        return mUsername;
    }

    public String getThumb() {
        return mThumb;
    }

    public byte getmSex() {
        return mSex;
    }
}

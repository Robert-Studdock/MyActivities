package com.bignerdranch.android.myactivities.user_classes;

import java.util.UUID;

/**
 * Created by Robert on 21/09/2017.
 */

public class User {
    private UUID mId;
    private String mFName;
    private String mLName;
    private int mGender;
    private String mEmail;
    private String mComment;

    public User() {
        this(UUID.randomUUID());
    }

    public User(UUID id) {
        mId = id;

    }

    public UUID getId() {
        return mId;
    }

    public String getFName() {
        return mFName;
    }

    public void setFName(String FName) {
        mFName = FName;
    }

    public String getLName() {
        return mLName;
    }

    public void setLName(String LName) {
        mLName = LName;
    }

    public int getGender() {
        return mGender;
    }

    public void setGender(int gender) {
        mGender = gender;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }
}

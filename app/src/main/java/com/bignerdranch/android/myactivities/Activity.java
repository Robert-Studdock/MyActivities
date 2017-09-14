package com.bignerdranch.android.myactivities;

import android.widget.Spinner;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Robert on 13/09/2017.
 */

public class Activity {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private String mLocation;
    private String mComment;
    private String mDurationHours;
    private String mDurationMinutes;
    private int mType;


    public Activity() {
        this(UUID.randomUUID());
    }

    public Activity(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getComment() {
        return mComment;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public String getDurationHours() {
        return mDurationHours;
    }

    public void setDurationHours(String duration) {
        mDurationHours = duration;
    }

    public String getDurationMinutes() {
        return mDurationMinutes;
    }

    public void setDurationMinutes(String duration) {
        mDurationMinutes = duration;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }
}


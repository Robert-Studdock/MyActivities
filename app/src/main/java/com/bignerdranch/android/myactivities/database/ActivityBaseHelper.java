package com.bignerdranch.android.myactivities.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bignerdranch.android.myactivities.database.ActivitiesDbScehma.ActivityTable;
import com.bignerdranch.android.myactivities.database.ActivitiesDbScehma.UserTable;
import com.bignerdranch.android.myactivities.user_classes.User;

import java.util.UUID;

/**
 * Created by Robert on 13/09/2017.
 */

public class ActivityBaseHelper extends SQLiteOpenHelper {
    private static  final int VERSION = 1;
    private static final String DATABASE_NAME = "activityBase.db";

    public ActivityBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ActivityTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                ActivityTable.Cols.UUID + ", " +
                ActivityTable.Cols.TITLE + ", " +
                ActivityTable.Cols.DATE + ", " +
                ActivityTable.Cols.LOCATION + ", " +
                ActivityTable.Cols.COMMENT + ", " +
                ActivityTable.Cols.DURATIONHOURS + ", " +
                ActivityTable.Cols.DURATIONMINUTES + ", " +
                ActivityTable.Cols.TYPE +
                ")"
        );
        db.execSQL("create table " + ActivitiesDbScehma.UserTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                UserTable.Cols.UUID + ", " +
                UserTable.Cols.FNAME + ", " +
                UserTable.Cols.LNAME + ", " +
                UserTable.Cols.GENDER + ", " +
                UserTable.Cols.EMAIL + ", " +
                UserTable.Cols.COMMENT + ")"
        );
        db.execSQL("insert into profiles (uuid) values " +
                "('" + UUID.randomUUID() + "')" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

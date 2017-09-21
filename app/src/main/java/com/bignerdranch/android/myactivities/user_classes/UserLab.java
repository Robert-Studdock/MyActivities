package com.bignerdranch.android.myactivities.user_classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bignerdranch.android.myactivities.Activity;
import com.bignerdranch.android.myactivities.ActivityLab;
import com.bignerdranch.android.myactivities.database.ActivitiesDbScehma;
import com.bignerdranch.android.myactivities.database.ActivityBaseHelper;
import com.bignerdranch.android.myactivities.database.ActivityCursorWrapper;
import com.bignerdranch.android.myactivities.database.UserCursorWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Robert on 21/09/2017.
 */

public class UserLab {
    private static UserLab sUserLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;


    public static UserLab get(Context context) {
        if (sUserLab == null) {
            sUserLab = new UserLab(context);
        }
        return sUserLab;
    }

    private UserLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new ActivityBaseHelper(mContext).getWritableDatabase();
    }


    public void addUser(User a) {
        ContentValues values = getContentValues(a);
        mDatabase.insert(ActivitiesDbScehma.UserTable.NAME, null, values);
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        UserCursorWrapper cursor = queryUsers();

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                users.add(cursor.getUser());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return users;
    }

    public User getUser() {
        UserCursorWrapper cursor = queryUsers();

        try {
            if (cursor.getCount() == 0) {
                Log.d("User Test", "No user found");
                return null;
            }
            cursor.moveToFirst();
            Log.d("User Test", "user found: " + cursor.getUser().getId().toString());
            return cursor.getUser();
        } finally {
            cursor.close();
        }
    }

    public void updateUser(User user, UUID id) {
        String uuidString = user.getId().toString();
        ContentValues values = getContentValues(user);

        mDatabase.update(ActivitiesDbScehma.UserTable.NAME, values,
                ActivitiesDbScehma.UserTable.Cols.UUID + " = ?",
                new String [] {id.toString()});
                Log.d("update test", "updating table where UUID = " + id);
    }


    private static ContentValues getContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(ActivitiesDbScehma.UserTable.Cols.UUID, user.getId().toString());
        values.put(ActivitiesDbScehma.UserTable.Cols.FNAME, user.getFName());
        values.put(ActivitiesDbScehma.UserTable.Cols.LNAME, user.getLName());
        values.put(ActivitiesDbScehma.UserTable.Cols.GENDER, user.getGender());
        values.put(ActivitiesDbScehma.UserTable.Cols.EMAIL, user.getEmail());
        values.put(ActivitiesDbScehma.UserTable.Cols.COMMENT, user.getComment());


        return values;
    }

    private UserCursorWrapper queryUsers() {
        Cursor cursor = mDatabase.query(
                ActivitiesDbScehma.UserTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        return new UserCursorWrapper(cursor);
    }
}

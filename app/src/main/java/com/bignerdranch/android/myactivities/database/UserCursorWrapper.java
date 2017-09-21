package com.bignerdranch.android.myactivities.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.myactivities.user_classes.User;

import java.util.UUID;

/**
 * Created by Robert on 21/09/2017.
 */

public class UserCursorWrapper extends CursorWrapper {
    public UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public User getUser() {
        String uuidString = getString(getColumnIndex(ActivitiesDbScehma.UserTable.Cols.UUID));
        String fname = getString(getColumnIndex(ActivitiesDbScehma.UserTable.Cols.FNAME));
        String lname = getString(getColumnIndex(ActivitiesDbScehma.UserTable.Cols.LNAME));
        Integer gender = getInt(getColumnIndex(ActivitiesDbScehma.UserTable.Cols.GENDER));
        String email = getString(getColumnIndex(ActivitiesDbScehma.UserTable.Cols.EMAIL));
        String comment = getString(getColumnIndex(ActivitiesDbScehma.UserTable.Cols.COMMENT));

        User user = new User(UUID.fromString(uuidString));
        user.setFName(fname);
        user.setLName(lname);
        user.setGender(gender);
        user.setEmail(email);
        user.setComment(comment);

        return user;
    }
}

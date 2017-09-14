package com.bignerdranch.android.myactivities.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.myactivities.Activity;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Robert on 13/09/2017.
 */

public class ActivityCursorWrapper extends CursorWrapper {
    public ActivityCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Activity getActivity() {
        String uuidString = getString(getColumnIndex(ActivitiesDbScehma.ActivityTable.Cols.UUID));
        String title = getString(getColumnIndex(ActivitiesDbScehma.ActivityTable.Cols.TITLE));
        long date = getLong(getColumnIndex(ActivitiesDbScehma.ActivityTable.Cols.DATE));
        String location = getString(getColumnIndex(ActivitiesDbScehma.ActivityTable.Cols.LOCATION));
        String comment = getString(getColumnIndex(ActivitiesDbScehma.ActivityTable.Cols.COMMENT));
        String durationHours = getString(getColumnIndex(ActivitiesDbScehma.ActivityTable.Cols.DURATIONHOURS));
        String durationMinutes = getString(getColumnIndex(ActivitiesDbScehma.ActivityTable.Cols.DURATIONMINUTES));
        Integer type = getInt(getColumnIndex(ActivitiesDbScehma.ActivityTable.Cols.TYPE));

        Activity activity = new Activity(UUID.fromString(uuidString));
        activity.setTitle(title);
        activity.setDate(new Date(date));
        activity.setLocation(location);
        activity.setComment(comment);
        activity.setDurationHours(durationHours);
        activity.setDurationMinutes(durationMinutes);
        activity.setType(type);

        return activity;
    }
}

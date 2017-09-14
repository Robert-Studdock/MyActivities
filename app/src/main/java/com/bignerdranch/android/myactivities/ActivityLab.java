package com.bignerdranch.android.myactivities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.myactivities.database.ActivitiesDbScehma;
import com.bignerdranch.android.myactivities.database.ActivityBaseHelper;
import com.bignerdranch.android.myactivities.database.ActivityCursorWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Robert on 13/09/2017.
 */

public class ActivityLab {
    private static ActivityLab sActivityLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;


    public static ActivityLab get(Context context) {
        if (sActivityLab == null) {
            sActivityLab = new ActivityLab(context);
        }
        return sActivityLab;
    }

    private ActivityLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new ActivityBaseHelper(mContext).getWritableDatabase();
    }

    public void addActivity(Activity a) {
        ContentValues values = getContentValues(a);
        mDatabase.insert(ActivitiesDbScehma.ActivityTable.NAME, null, values);
    }

    public List<Activity> getActivities() {
        List<Activity> activities = new ArrayList<>();
        ActivityCursorWrapper cursor = queryActivities(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                activities.add(cursor.getActivity());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return activities;
    }

    public Activity getActivity(UUID id) {
        ActivityCursorWrapper cursor = queryActivities(
                ActivitiesDbScehma.ActivityTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getActivity();
        } finally {
            cursor.close();
        }
    }

    public void updateActivity(Activity activity) {
        String uuidString = activity.getId().toString();
        ContentValues values = getContentValues(activity);

        mDatabase.update(ActivitiesDbScehma.ActivityTable.NAME, values,
                ActivitiesDbScehma.ActivityTable.Cols.UUID + " = ?",
                new String [] { uuidString});
    }


    private static ContentValues getContentValues(Activity activity) {
        ContentValues values = new ContentValues();
        values.put(ActivitiesDbScehma.ActivityTable.Cols.UUID, activity.getId().toString());
        values.put(ActivitiesDbScehma.ActivityTable.Cols.TITLE, activity.getTitle());
        values.put(ActivitiesDbScehma.ActivityTable.Cols.DATE, activity.getDate().getTime());
        values.put(ActivitiesDbScehma.ActivityTable.Cols.LOCATION, activity.getLocation());
        values.put(ActivitiesDbScehma.ActivityTable.Cols.COMMENT, activity.getComment());
        values.put(ActivitiesDbScehma.ActivityTable.Cols.DURATION, activity.getDuration());
        values.put(ActivitiesDbScehma.ActivityTable.Cols.TYPE, activity.getType());

        return values;
    }

    private ActivityCursorWrapper queryActivities(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ActivitiesDbScehma.ActivityTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new ActivityCursorWrapper(cursor);
    }
}

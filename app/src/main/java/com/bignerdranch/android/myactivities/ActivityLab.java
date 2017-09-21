package com.bignerdranch.android.myactivities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.widget.Toast;

import com.bignerdranch.android.myactivities.database.ActivitiesDbScehma;
import com.bignerdranch.android.myactivities.database.ActivityBaseHelper;
import com.bignerdranch.android.myactivities.database.ActivityCursorWrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.jar.Attributes;

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

    public void deleteActivity(UUID id) {
        //mDatabase.execSQL("delete from activities where uuid = " + id ,null);
        //Toast.makeText(mContext = mContext.getApplicationContext(), "Delete Pressed with ID :" + id, Toast.LENGTH_SHORT).show();
       // mDatabase.delete(tableName,whereClause, whereArgs);
        this.mDatabase.delete("activities", "uuid=?", new String[] { String.valueOf(id) });
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
        values.put(ActivitiesDbScehma.ActivityTable.Cols.DURATIONHOURS, activity.getDurationHours());
        values.put(ActivitiesDbScehma.ActivityTable.Cols.DURATIONMINUTES, activity.getDurationMinutes());
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

    public File getPhotoFile(Activity activity) {
        File externalFilesDir = mContext
                .getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (externalFilesDir == null) {
            return null;
        }
        return new File(externalFilesDir, activity.getPhotoFilename());
    }
}

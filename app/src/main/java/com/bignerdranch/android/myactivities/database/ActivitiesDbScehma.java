package com.bignerdranch.android.myactivities.database;

/**
 * Created by Robert on 13/09/2017.
 */

public class ActivitiesDbScehma {
    public static final class ActivityTable {
        public static final String NAME = "activities";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String LOCATION = "location";
            public static final String COMMENT = "comment";
            public static final String DURATION = "duration";
            public static final String TYPE = "type";
        }
    }
}

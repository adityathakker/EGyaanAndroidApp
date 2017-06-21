package com.androidsphere.aditya.egyaan.database;

import android.provider.BaseColumns;

/**
 * Created by aditya9172 on 17/12/15.
 */
public class DatabaseNames {

    public abstract class Timetable implements BaseColumns {
        public static final String TABLE_NAME = "timetable";
        public static final String TT_ID = "tt_id";
        public static final String DAY_NAME = "day_name";
        public static final String TT_TIME = "tt_time";
        public static final String TT_SUBJECT = "tt_subject";
    }
}

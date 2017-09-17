package com.adityathakker.egyaan.utils;

import android.provider.BaseColumns;

/**
 * Created by fireion on 27/6/17.
 */

public class DatabaseColumns {
    public abstract class Columns implements BaseColumns {
        //Student Table Columns
        public static final String ROLE_ID = "role_id";
        public static final String USER_ID = "user_id";
        public static final String FIRST_NAME = "firstname";
        public static final String LAST_NAME = "lastname";
        public static final String EMAIL = "email";
        public static final String STUDENT_PASSWORD = "student_passwd";
        public static final String GENDER = "gender";
        public static final String MOBILE = "mobile";
        public static final String STUDENT_PROFILE_PHOTO = "student_profile_photo";
        public static final String PARENT_PROFILE_PHOTO = "parent_profile_photo";
        public static final String BATCH_ID = "batch_id";
        public static final String BRANCH_ID = "branch_id";
        public static final String PARENT_NAME = "parent_name";
        public static final String PARENT_EMAIL = "parent_email";
        public static final String PARENT_PASSWORD = "parent_password";
        public static final String PARENT_MOBILE = "parent_mobile";
        public static final String TABLE_NAME_STUDENT = "egn_student";
        //Student Table Columns

        //Timetable Table Columns
        public static final String DAY_ID = "day_id";
        public static final String TIME = "time";
        public static final String TEACHER_NAME = "teacher";
        public static final String COURSE_NAME = "course";
        public static final String COMMENT = "comment";
        public static final String TABLE_NAME_TIMETABLE = "egn_timetable";
        //Timetable Table Columns
    }
}

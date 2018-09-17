package com.adityathakker.egyaan.utils;

import android.provider.BaseColumns;

/**
 * Created by fireion on 27/6/17.
 */

class DatabaseColumns {
    abstract class Columns implements BaseColumns {

        //Start Student Table Columns
        static final String ROLE_ID = "role_id";
        static final String USER_ID = "user_id";
        static final String FIRST_NAME = "firstname";
        static final String LAST_NAME = "lastname";
        static final String EMAIL = "email";
        static final String STUDENT_PASSWORD = "student_passwd";
        static final String GENDER = "gender";
        static final String MOBILE = "mobile";
        static final String STUDENT_PROFILE_PHOTO = "student_profile_photo";
        static final String PARENT_PROFILE_PHOTO = "parent_profile_photo";
        static final String BATCH_ID = "batch_id";
        static final String BRANCH_ID = "branch_id";
        static final String PARENT_NAME = "parent_name";
        static final String PARENT_EMAIL = "parent_email";
        static final String PARENT_PASSWORD = "parent_password";
        static final String PARENT_MOBILE = "parent_mobile";
        static final String TABLE_NAME_STUDENT = "egn_student";
        //End Student Table Columns

        //Start Timetable Table Columns
        static final String DAY_ID = "day_id";
        static final String TIME = "time";
        static final String TEACHER_NAME = "teacher";
        static final String COURSE_NAME_TIMETABLE = "course";
        static final String COMMENT = "comment";
        static final String TABLE_NAME_TIMETABLE = "egn_timetable";
        //End Timetable Table Columns

        //Start Course Table Columns
        static final String COURSE_ID = "course_id";
        static final String COURSE_NAME = "course_name";
        static final String COURSE_USER_ID = "course_user_id";
        static final String TABLE_NAME_COURSES = "egn_courses";
        //End Course Table Columns

        //Start Notes Table Columns
        static final String NOTES_ID = "notes_id";
        static final String NOTES_TEACHER_NAME = "notes_teacher_name";
        static final String NOTES_TITLE = "notes_title";
        static final String NOTES_FILE = "notes_file";
        static final String NOTES_SIZE = "notes_size";
        static final String NOTES_TYPE = "notes_type";
        static final String NOTES_PAGES = "notes_pages";
        static final String NOTES_COURSE_ID = "notes_course_id";
        static final String TABLE_NAME_NOTES = "egn_notes";
        //End Notes Table Columns

        //Start Test Table Columns
        static final String TESTS_ID = "tests_id";
        static final String TESTS_TITLE = "tests_title";
        static final String TESTS_DATE = "tests_date";
        static final String TESTS_MARKS = "tests_marks";
        static final String TESTS_TOTAL_MARKS = "tests_total_marks";
        static final String TESTS_TYPE = "tests_type";
        static final String TESTS_USER_ID = "tests_user_id";
        static final String TABLE_NAME_TESTS = "egn_tests";
        //End Test Table Columns

        //Start Home Timetable Columns
        static final String DAY_ID_HOME = "day_id";
        static final String TIME_HOME = "time";
        static final String COURSE_NAME_TIMETABLE_HOME = "course";
        static final String TABLE_NAME_TIMETABLE_HOME = "egn_timetable_home";
        //End Home Timetable Columns

        //Start Home Tests Columns
        static final String TESTS_ID_HOME = "tests_id";
        static final String TESTS_TITLE_HOME = "tests_title";
        static final String TESTS_DATE_HOME = "tests_date";
        static final String TESTS_MARKS_HOME = "tests_marks";
        static final String TESTS_TOTAL_MARKS_HOME = "tests_total_marks";
        static final String TESTS_TYPE_HOME = "tests_type";
        static final String TESTS_USER_ID_HOME = "tests_user_id";
        static final String TABLE_NAME_TESTS_HOME = "egn_tests_home";
        //End Home TestsColumns
    }
}

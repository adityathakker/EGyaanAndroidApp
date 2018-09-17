package com.adityathakker.egyaan.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.adityathakker.egyaan.models.CourseDataModel;
import com.adityathakker.egyaan.models.Details;
import com.adityathakker.egyaan.models.NotesModel;
import com.adityathakker.egyaan.models.TestsModel;
import com.adityathakker.egyaan.models.TimetableData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fireion on 27/6/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String TAG = DatabaseHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE_STUDENT = "CREATE TABLE " + DatabaseColumns.Columns.TABLE_NAME_STUDENT +
            " ( id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DatabaseColumns.Columns.ROLE_ID + " INTEGER,"
            + DatabaseColumns.Columns.USER_ID + " VARCHAR(10),"
            + DatabaseColumns.Columns.FIRST_NAME + " VARCHAR(20),"
            + DatabaseColumns.Columns.LAST_NAME + " VARCHAR(20),"
            + DatabaseColumns.Columns.EMAIL + " VARCHAR(20),"
            + DatabaseColumns.Columns.STUDENT_PASSWORD + " VARCHAR(20),"
            + DatabaseColumns.Columns.GENDER + " VARCHAR(20),"
            + DatabaseColumns.Columns.MOBILE + " VARCHAR(20),"
            + DatabaseColumns.Columns.STUDENT_PROFILE_PHOTO + " VARCHAR(20),"
            + DatabaseColumns.Columns.PARENT_PROFILE_PHOTO + " VARCHAR(20),"
            + DatabaseColumns.Columns.BATCH_ID + " VARCHAR(10),"
            + DatabaseColumns.Columns.BRANCH_ID + " VARCHAR(10),"
            + DatabaseColumns.Columns.PARENT_NAME + " VARCHAR(20),"
            + DatabaseColumns.Columns.PARENT_EMAIL + " VARCHAR(20),"
            + DatabaseColumns.Columns.PARENT_PASSWORD + " VARCHAR(20),"
            + DatabaseColumns.Columns.PARENT_MOBILE + " VARCHAR(20))";

    private static final String CREATE_TABLE_TIMETABLE = "CREATE TABLE " + DatabaseColumns.Columns.TABLE_NAME_TIMETABLE +
            " ( id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DatabaseColumns.Columns.DAY_ID + " VARCHAR(10),"
            + DatabaseColumns.Columns.TIME + " VARCHAR(20),"
            + DatabaseColumns.Columns.TEACHER_NAME + " VARCHAR(20),"
            + DatabaseColumns.Columns.COURSE_NAME_TIMETABLE + " VARCHAR(20),"
            + DatabaseColumns.Columns.COMMENT + " VARCHAR(255))";

    private static final String CREATE_TABLE_COURSES = "CREATE TABLE " + DatabaseColumns.Columns.TABLE_NAME_COURSES +
            " (" + DatabaseColumns.Columns.COURSE_ID + " VARCHAR(10),"
            + DatabaseColumns.Columns.COURSE_NAME + " VARCHAR(255),"
            + DatabaseColumns.Columns.COURSE_USER_ID + " VARCHAR(10))";

    private static final String CREATE_TABLE_NOTES = "CREATE TABLE " + DatabaseColumns.Columns.TABLE_NAME_NOTES +
            " (" + DatabaseColumns.Columns.NOTES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DatabaseColumns.Columns.NOTES_TEACHER_NAME + " VARCHAR(255),"
            + DatabaseColumns.Columns.NOTES_TITLE + " VARCHAR(255),"
            + DatabaseColumns.Columns.NOTES_FILE + " VARCHAR(255),"
            + DatabaseColumns.Columns.NOTES_SIZE + " VARCHAR(255),"
            + DatabaseColumns.Columns.NOTES_TYPE + " VARCHAR(255),"
            + DatabaseColumns.Columns.NOTES_PAGES + " VARCHAR(255),"
            + DatabaseColumns.Columns.NOTES_COURSE_ID + " VARCHAR(255))";

    private static final String CREATE_TABLE_TESTS = "CREATE TABLE " + DatabaseColumns.Columns.TABLE_NAME_TESTS +
            " (" + DatabaseColumns.Columns.TESTS_ID + " INTEGER PRIMARY KEY,"
            + DatabaseColumns.Columns.TESTS_TITLE + " VARCHAR(255),"
            + DatabaseColumns.Columns.TESTS_DATE + " VARCHAR(255),"
            + DatabaseColumns.Columns.TESTS_MARKS + " VARCHAR(255),"
            + DatabaseColumns.Columns.TESTS_TOTAL_MARKS + " VARCHAR(255),"
            + DatabaseColumns.Columns.TESTS_TYPE + " VARCHAR(255),"
            + DatabaseColumns.Columns.TESTS_USER_ID + " VARCHAR(255))";

    private static final String CREATE_TABLE_TIMETABLE_HOME = "CREATE TABLE " + DatabaseColumns.Columns.TABLE_NAME_TIMETABLE_HOME +
            " ( id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DatabaseColumns.Columns.DAY_ID_HOME + " VARCHAR(10),"
            + DatabaseColumns.Columns.TIME_HOME + " VARCHAR(20),"
            + DatabaseColumns.Columns.COURSE_NAME_TIMETABLE_HOME + " VARCHAR(20))";

    private static final String CREATE_TABLE_TESTS_HOME = "CREATE TABLE " + DatabaseColumns.Columns.TABLE_NAME_TESTS_HOME +
            " (" + DatabaseColumns.Columns.TESTS_ID_HOME + " INTEGER,"
            + DatabaseColumns.Columns.TESTS_TITLE_HOME + " VARCHAR(255),"
            + DatabaseColumns.Columns.TESTS_DATE_HOME + " VARCHAR(255),"
            + DatabaseColumns.Columns.TESTS_MARKS_HOME + " VARCHAR(255),"
            + DatabaseColumns.Columns.TESTS_TOTAL_MARKS_HOME + " VARCHAR(255),"
            + DatabaseColumns.Columns.TESTS_TYPE_HOME + " VARCHAR(255),"
            + DatabaseColumns.Columns.TESTS_USER_ID_HOME + " VARCHAR(255))";

    private static final String DROP_TABLE_STUDENT = "DROP TABLE IF EXISTS " + DatabaseColumns.Columns.TABLE_NAME_STUDENT;

    private static final String DROP_TABLE_TIMETABLE = "DROP TABLE IF EXISTS " + DatabaseColumns.Columns.TABLE_NAME_TIMETABLE;

    private static final String DROP_TABLE_COURSES = "DROP TABLE IF EXISTS " + DatabaseColumns.Columns.TABLE_NAME_COURSES;

    private static final String DROP_TABLE_NOTES = "DROP TABLE IF EXISTS " + DatabaseColumns.Columns.TABLE_NAME_NOTES;

    private static final String DROP_TABLE_TESTS = "DROP TABLE IF EXISTS " + DatabaseColumns.Columns.TABLE_NAME_TESTS;

    private static final String DROP_TABLE_TIMETABLE_HOME = "DROP TABLE IF EXISTS " + DatabaseColumns.Columns.TABLE_NAME_TIMETABLE_HOME;

    private static final String DROP_TABLE_TESTS_HOME = "DROP TABLE IF EXISTS " + DatabaseColumns.Columns.TABLE_NAME_TESTS_HOME;

    public DatabaseHandler(Context context) {
        super(context, AppConst.Extras.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STUDENT);
        db.execSQL(CREATE_TABLE_TIMETABLE);
        db.execSQL(CREATE_TABLE_COURSES);
        db.execSQL(CREATE_TABLE_NOTES);
        db.execSQL(CREATE_TABLE_TESTS);
        db.execSQL(CREATE_TABLE_TIMETABLE_HOME);
        db.execSQL(CREATE_TABLE_TESTS_HOME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_STUDENT);
        db.execSQL(DROP_TABLE_TIMETABLE);
        db.execSQL(DROP_TABLE_COURSES);
        db.execSQL(DROP_TABLE_NOTES);
        db.execSQL(DROP_TABLE_TESTS);
        db.execSQL(DROP_TABLE_TIMETABLE_HOME);
        db.execSQL(DROP_TABLE_TESTS_HOME);
        onCreate(db);
    }

    public boolean insertStudent(Integer roleId, String userId, String firstname, String lastname, String email,
                                 String studentPasswd, String gender, String mobile, String studentProfilePhoto,
                                 String parentProfilePhoto, String batchId, String branchId, String parentName,
                                 String parentEmail, String parentPasswd, String parentMobile) {
        Log.d(TAG, "insertStudent: Database Name " + getDatabaseName());
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseColumns.Columns.ROLE_ID, roleId);
        contentValues.put(DatabaseColumns.Columns.USER_ID, userId);
        contentValues.put(DatabaseColumns.Columns.FIRST_NAME, firstname);
        contentValues.put(DatabaseColumns.Columns.LAST_NAME, lastname);
        contentValues.put(DatabaseColumns.Columns.EMAIL, email);
        contentValues.put(DatabaseColumns.Columns.STUDENT_PASSWORD, studentPasswd);
        contentValues.put(DatabaseColumns.Columns.GENDER, gender);
        contentValues.put(DatabaseColumns.Columns.MOBILE, mobile);
        contentValues.put(DatabaseColumns.Columns.STUDENT_PROFILE_PHOTO, studentProfilePhoto);
        contentValues.put(DatabaseColumns.Columns.PARENT_PROFILE_PHOTO, parentProfilePhoto);
        contentValues.put(DatabaseColumns.Columns.BATCH_ID, batchId);
        contentValues.put(DatabaseColumns.Columns.BRANCH_ID, branchId);
        contentValues.put(DatabaseColumns.Columns.PARENT_NAME, parentName);
        contentValues.put(DatabaseColumns.Columns.PARENT_EMAIL, parentEmail);
        contentValues.put(DatabaseColumns.Columns.PARENT_PASSWORD, parentPasswd);
        contentValues.put(DatabaseColumns.Columns.PARENT_MOBILE, parentMobile);
        long result = sqLiteDatabase.insert(DatabaseColumns.Columns.TABLE_NAME_STUDENT, null, contentValues);

        if (result == -1) {
            //Not Inserted
            sqLiteDatabase.close();
            return false;
        } else {
            //Inserted
            sqLiteDatabase.close();
            return true;
        }
    }

    public Details getStudent(String email) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Details details = new Details();
        String selectData = "SELECT * FROM " + DatabaseColumns.Columns.TABLE_NAME_STUDENT + " WHERE email = '" + email + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(selectData, null);

        if (cursor != null && cursor.moveToFirst()) {
            details.setRoleId(cursor.getInt(1));
            details.setUserId(cursor.getString(2));
            details.setFirstname(cursor.getString(3));
            details.setLastname(cursor.getString(4));
            details.setEmail(cursor.getString(5));
            details.setStudentPasswd(cursor.getString(6));
            details.setGender(cursor.getString(7));
            details.setMobile(cursor.getString(8));
            details.setStudentProfilePhoto(cursor.getString(9));
            details.setParentProfilePhoto(cursor.getString(10));
            details.setBatchId(cursor.getString(11));
            details.setBranchId(cursor.getString(12));
            details.setParentName(cursor.getString(13));
            details.setParentEmail(cursor.getString(14));
            details.setParentPasswd(cursor.getString(15));
            details.setParentMobile(cursor.getString(16));
            cursor.close();
        }
        return details;
    }

    public boolean deleteStudent(String email) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long result = sqLiteDatabase.delete(DatabaseColumns.Columns.TABLE_NAME_STUDENT,
                DatabaseColumns.Columns.EMAIL + " = ?", new String[]{email});

        if (result == -1) {
            sqLiteDatabase.close();
            return false;
        } else {
            sqLiteDatabase.close();
            return true;
        }
    }

//    public boolean deleteStudentDataTable() {
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        long student = sqLiteDatabase.delete(DatabaseColumns.Columns.TABLE_NAME_STUDENT, null, null);
//
//        if (student == -1) {
//            sqLiteDatabase.close();
//            return false;
//        } else {
//            sqLiteDatabase.close();
//            return true;
//        }
//    }

    public boolean deleteDatabaseTable() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long student = sqLiteDatabase.delete(DatabaseColumns.Columns.TABLE_NAME_STUDENT, null, null);
        long timetable = sqLiteDatabase.delete(DatabaseColumns.Columns.TABLE_NAME_TIMETABLE, null, null);
        long tests = sqLiteDatabase.delete(DatabaseColumns.Columns.TABLE_NAME_TESTS, null, null);
        long notes = sqLiteDatabase.delete(DatabaseColumns.Columns.TABLE_NAME_NOTES, null, null);
        long courses = sqLiteDatabase.delete(DatabaseColumns.Columns.TABLE_NAME_COURSES, null, null);
        long testsHome = sqLiteDatabase.delete(DatabaseColumns.Columns.TABLE_NAME_TESTS_HOME, null, null);
        long timetableHome = sqLiteDatabase.delete(DatabaseColumns.Columns.TABLE_NAME_TIMETABLE_HOME, null, null);

        if (student == -1 && timetable == -1 && tests == -1 && notes == -1
                && courses == -1 && testsHome == -1 && timetableHome == -1) {
//            Log.d(TAG, "deleteDatabaseTable: " + student + " " + timetable + " " + tests
//                    + " " + notes + " " + courses + " " + testsHome + " " + timetableHome);
            sqLiteDatabase.close();
            return false;
        } else {
//            Log.d(TAG, "deleteDatabaseTable: " + student + " " + timetable + " " + tests
//                    + " " + notes + " " + courses + " " + testsHome + " " + timetableHome);
            sqLiteDatabase.close();
            return true;
        }
    }

    public boolean deleteTimetableDataTable(String day_id, String contextNumber) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long timetable;

        if (contextNumber.equals("-2")) {
            timetable = sqLiteDatabase.delete(DatabaseColumns.Columns.TABLE_NAME_TIMETABLE_HOME, DatabaseColumns.Columns.DAY_ID + " = '" + day_id + "'", null);
        } else {
            timetable = sqLiteDatabase.delete(DatabaseColumns.Columns.TABLE_NAME_TIMETABLE, DatabaseColumns.Columns.DAY_ID + " = '" + day_id + "'", null);
        }

        if (timetable == -1) {
            sqLiteDatabase.close();
            return false;
        } else {
            sqLiteDatabase.close();
            return true;
        }
    }

    public boolean deleteCoursesDataTable() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long courses = sqLiteDatabase.delete(DatabaseColumns.Columns.TABLE_NAME_COURSES, null, null);

        if (courses == -1) {
            sqLiteDatabase.close();
            return false;
        } else {
            sqLiteDatabase.close();
            return true;
        }
    }

    public boolean deleteNotesDataTable() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long notes = sqLiteDatabase.delete(DatabaseColumns.Columns.TABLE_NAME_NOTES, null, null);

        if (notes == -1) {
            sqLiteDatabase.close();
            return false;
        } else {
            sqLiteDatabase.close();
            return true;
        }
    }

    public boolean deleteTestsDataTable(String contextValue) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long tests;

        if (contextValue.equals("-2")) {
            tests = sqLiteDatabase.delete(DatabaseColumns.Columns.TABLE_NAME_TESTS_HOME, null, null);
        } else {
            tests = sqLiteDatabase.delete(DatabaseColumns.Columns.TABLE_NAME_TESTS, null, null);
        }

        if (tests == -1) {
            sqLiteDatabase.close();
            return false;
        } else {
            sqLiteDatabase.close();
            return true;
        }
    }

    public boolean insertTimetable(String dayId, String time, String teacherName, String courseName,
                                   String comment) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseColumns.Columns.DAY_ID, dayId);
        contentValues.put(DatabaseColumns.Columns.TIME, time);
        contentValues.put(DatabaseColumns.Columns.TEACHER_NAME, teacherName);
        contentValues.put(DatabaseColumns.Columns.COURSE_NAME_TIMETABLE, courseName);
        contentValues.put(DatabaseColumns.Columns.COMMENT, comment);
        long result = sqLiteDatabase.insert(DatabaseColumns.Columns.TABLE_NAME_TIMETABLE, null, contentValues);

        if (result == -1) {
            //Not Inserted
            sqLiteDatabase.close();
            return false;
        } else {
            //Inserted
            sqLiteDatabase.close();
            return true;
        }
    }

    public List<TimetableData> getTimetable(String dayId) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        List<TimetableData> timetableDataList = new ArrayList<>();
        String selectData = "SELECT * FROM " + DatabaseColumns.Columns.TABLE_NAME_TIMETABLE + " WHERE day_id = '" + dayId + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(selectData, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                TimetableData timetableData = new TimetableData();
                timetableData.setDayId(cursor.getString(1));
                timetableData.setTime(cursor.getString(2));
                timetableData.setTeacher(cursor.getString(3));
                timetableData.setCourse(cursor.getString(4));
                timetableData.setComment(cursor.getString(5));
                timetableDataList.add(timetableData);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return timetableDataList;
    }

    public boolean insertCourses(String courseId, String courseName, String courseUserId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseColumns.Columns.COURSE_ID, courseId);
        contentValues.put(DatabaseColumns.Columns.COURSE_NAME, courseName);
        contentValues.put(DatabaseColumns.Columns.COURSE_USER_ID, courseUserId);
//        Log.d(TAG, "insertCourses: " + courseId + " " + courseName + " " + courseUserId);
        long result = sqLiteDatabase.insert(DatabaseColumns.Columns.TABLE_NAME_COURSES, null, contentValues);
//        Log.d(TAG, "insertCourses: " + result);

        if (result == -1) {
            //Not Inserted
            sqLiteDatabase.close();
            return false;
        } else {
            //Inserted
            sqLiteDatabase.close();
            return true;
        }
    }

    public List<CourseDataModel> getCourses(String userId) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        List<CourseDataModel> courseDataModelList = new ArrayList<>();
        String selectData = "SELECT * FROM " + DatabaseColumns.Columns.TABLE_NAME_COURSES + " WHERE course_user_id = '" + userId + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(selectData, null);
//        Log.d(TAG, "getCourses: " + cursor);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                CourseDataModel courseDataModel = new CourseDataModel();
                courseDataModel.setId(cursor.getString(0));
                courseDataModel.setName(cursor.getString(1));
                courseDataModelList.add(courseDataModel);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return courseDataModelList;
    }

    public boolean insertNotes(String notesTeacherName, String notesTitle,
                               String notesFile, String notesSize, String notesType,
                               String notesPages, String notesCourseId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseColumns.Columns.NOTES_TEACHER_NAME, notesTeacherName);
        contentValues.put(DatabaseColumns.Columns.NOTES_TITLE, notesTitle);
        contentValues.put(DatabaseColumns.Columns.NOTES_FILE, notesFile);
        contentValues.put(DatabaseColumns.Columns.NOTES_SIZE, notesSize);
        contentValues.put(DatabaseColumns.Columns.NOTES_TYPE, notesType);
        contentValues.put(DatabaseColumns.Columns.NOTES_PAGES, notesPages);
        contentValues.put(DatabaseColumns.Columns.NOTES_COURSE_ID, notesCourseId);
//        Log.d(TAG, "insertNotes: " + contentValues);
        long result = sqLiteDatabase.insert(DatabaseColumns.Columns.TABLE_NAME_NOTES, null, contentValues);

        if (result == -1) {
            //Not Inserted
            sqLiteDatabase.close();
            return false;
        } else {
            //Inserted
            sqLiteDatabase.close();
            return true;
        }
    }

    public List<NotesModel> getNotes(String courseId) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        List<NotesModel> notesModelList = new ArrayList<>();
        String selectData = "SELECT * FROM " + DatabaseColumns.Columns.TABLE_NAME_NOTES + " WHERE notes_course_id = '" + courseId + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(selectData, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                NotesModel notesModel = new NotesModel();
                notesModel.setName(cursor.getString(1));
                notesModel.setTitle(cursor.getString(2));
                notesModel.setFile(cursor.getString(3));
                notesModel.setSize(cursor.getString(4));
                notesModel.setType(cursor.getString(5));
                notesModel.setPages(cursor.getString(6));
                notesModelList.add(notesModel);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return notesModelList;
    }

    public boolean insertTests(String testId, String testTitle,
                               String testDate, String testMarks, String testTotalMarks,
                               String testType, String testUserId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseColumns.Columns.TESTS_ID, testId);
        contentValues.put(DatabaseColumns.Columns.TESTS_TITLE, testTitle);
        contentValues.put(DatabaseColumns.Columns.TESTS_DATE, testDate);
        contentValues.put(DatabaseColumns.Columns.TESTS_MARKS, testMarks);
        contentValues.put(DatabaseColumns.Columns.TESTS_TOTAL_MARKS, testTotalMarks);
        contentValues.put(DatabaseColumns.Columns.TESTS_TYPE, testType);
        contentValues.put(DatabaseColumns.Columns.TESTS_USER_ID, testUserId);
//        Log.d(TAG, "insertNotes: " + contentValues);
        long result = sqLiteDatabase.insert(DatabaseColumns.Columns.TABLE_NAME_TESTS, null, contentValues);

        if (result == -1) {
            //Not Inserted
            sqLiteDatabase.close();
            return false;
        } else {
            //Inserted
            sqLiteDatabase.close();
            return true;
        }
    }

    public List<TestsModel> getTests(String userId) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        List<TestsModel> testsModelList = new ArrayList<>();
        String selectData = "SELECT * FROM " + DatabaseColumns.Columns.TABLE_NAME_TESTS + " WHERE tests_user_id = '" + userId + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(selectData, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                TestsModel testsModel = new TestsModel();
                testsModel.setTestId(cursor.getString(0));
                testsModel.setTitle(cursor.getString(1));
                testsModel.setDateOfTest(cursor.getString(2));
                testsModel.setMarks(cursor.getString(3));
                testsModel.setTotalMarks(cursor.getString(4));
                testsModel.setType(cursor.getString(5));
                testsModelList.add(testsModel);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return testsModelList;
    }

    public boolean insertTimetableHome(String dayId, String time, String courseName) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseColumns.Columns.DAY_ID_HOME, dayId);
        contentValues.put(DatabaseColumns.Columns.TIME_HOME, time);
        contentValues.put(DatabaseColumns.Columns.COURSE_NAME_TIMETABLE_HOME, courseName);
        long result = sqLiteDatabase.insert(DatabaseColumns.Columns.TABLE_NAME_TIMETABLE_HOME, null, contentValues);

        if (result == -1) {
            //Not Inserted
            sqLiteDatabase.close();
            return false;
        } else {
            //Inserted
            sqLiteDatabase.close();
            return true;
        }
    }

    public List<TimetableData> getTimetableHome(String dayId) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        List<TimetableData> timetableDataList = new ArrayList<>();
        String selectData = "SELECT * FROM " + DatabaseColumns.Columns.TABLE_NAME_TIMETABLE_HOME + " WHERE day_id = '" + dayId + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(selectData, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                TimetableData timetableData = new TimetableData();
                timetableData.setDayId(cursor.getString(1));
                timetableData.setTime(cursor.getString(2));
                timetableData.setCourse(cursor.getString(3));
                timetableDataList.add(timetableData);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return timetableDataList;
    }

    public boolean insertTestsHome(String testId, String testTitle,
                                   String testDate, String testMarks, String testTotalMarks,
                                   String testType, String testUserId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseColumns.Columns.TESTS_ID_HOME, testId);
        contentValues.put(DatabaseColumns.Columns.TESTS_TITLE_HOME, testTitle);
        contentValues.put(DatabaseColumns.Columns.TESTS_DATE_HOME, testDate);
        contentValues.put(DatabaseColumns.Columns.TESTS_MARKS_HOME, testMarks);
        contentValues.put(DatabaseColumns.Columns.TESTS_TOTAL_MARKS_HOME, testTotalMarks);
        contentValues.put(DatabaseColumns.Columns.TESTS_TYPE_HOME, testType);
        contentValues.put(DatabaseColumns.Columns.TESTS_USER_ID_HOME, testUserId);
//        Log.d(TAG, "insertNotes: " + contentValues);
        long result = sqLiteDatabase.insert(DatabaseColumns.Columns.TABLE_NAME_TESTS_HOME, null, contentValues);

        if (result == -1) {
            //Not Inserted
            sqLiteDatabase.close();
            return false;
        } else {
            //Inserted
            sqLiteDatabase.close();
            return true;
        }
    }

    public List<TestsModel> getTestsHome(String userId) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        List<TestsModel> testsModelList = new ArrayList<>();
        String selectData = "SELECT * FROM " + DatabaseColumns.Columns.TABLE_NAME_TESTS_HOME + " WHERE tests_user_id = '" + userId + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(selectData, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                TestsModel testsModel = new TestsModel();
                testsModel.setTestId(cursor.getString(0));
                testsModel.setTitle(cursor.getString(1));
                testsModel.setDateOfTest(cursor.getString(2));
                testsModel.setMarks(cursor.getString(3));
                testsModel.setTotalMarks(cursor.getString(4));
                testsModel.setType(cursor.getString(5));
                testsModelList.add(testsModel);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return testsModelList;
    }
}
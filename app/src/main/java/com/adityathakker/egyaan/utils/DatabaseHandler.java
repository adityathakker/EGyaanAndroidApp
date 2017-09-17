package com.adityathakker.egyaan.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.adityathakker.egyaan.models.Details;
import com.adityathakker.egyaan.models.TimetableData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fireion on 27/6/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String TAG = DatabaseHandler.class.getSimpleName();
    public static final int DATABASE_VERSION = 1;
    public static final String CREATE_TABLE_STUDENT = "CREATE TABLE " + DatabaseColumns.Columns.TABLE_NAME_STUDENT +
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

    public static final String CREATE_TABLE_TIMETABLE = "CREATE TABLE " + DatabaseColumns.Columns.TABLE_NAME_TIMETABLE +
            " ( id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DatabaseColumns.Columns.DAY_ID + " VARCHAR(10),"
            + DatabaseColumns.Columns.TIME + " VARCHAR(20),"
            + DatabaseColumns.Columns.TEACHER_NAME + " VARCHAR(20),"
            + DatabaseColumns.Columns.COURSE_NAME + " VARCHAR(20),"
            + DatabaseColumns.Columns.COMMENT + " VARCHAR(255))";

    public static final String DROP_TABLE_STUDENT = "DROP TABLE IF EXISTS " + DatabaseColumns.Columns.TABLE_NAME_STUDENT;

    public static final String DROP_TABLE_TIMETABLE = "DROP TABLE IF EXISTS " + DatabaseColumns.Columns.TABLE_NAME_TIMETABLE;

    public DatabaseHandler(Context context) {
        super(context, AppConst.Extras.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STUDENT);
        db.execSQL(CREATE_TABLE_TIMETABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_STUDENT);
        db.execSQL(DROP_TABLE_TIMETABLE);
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

    public boolean deleteDatabaseTable() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long student = sqLiteDatabase.delete(DatabaseColumns.Columns.TABLE_NAME_STUDENT, null, null);
        long timetable = sqLiteDatabase.delete(DatabaseColumns.Columns.TABLE_NAME_TIMETABLE, null, null);

        if (student == -1 && timetable == -1) {
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
        contentValues.put(DatabaseColumns.Columns.COURSE_NAME, courseName);
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
}
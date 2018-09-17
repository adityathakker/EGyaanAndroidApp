package com.androidsphere.aditya.egyaan.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by aditya9172 on 17/12/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "EGyaan";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TIMETABLE_TABLE = "CREATE TABLE " + DatabaseNames.Timetable.TABLE_NAME +
            "(" + DatabaseNames.Timetable.TT_ID + " INTEGER AUTO INCREMENT PRIMARY KEY,"
            + DatabaseNames.Timetable.DAY_NAME + " VARCHAR(255),"
            + DatabaseNames.Timetable.TT_TIME + " VARCHAR(255),"
            + DatabaseNames.Timetable.TT_SUBJECT + " VARCHAR(255))";

    private static final String DROP_TIMETABLE_TABLE = "DROP TABLE IF EXISTS " + DatabaseNames.Timetable.TABLE_NAME;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TIMETABLE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TIMETABLE_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void insertIntoTimeTable(ArrayList<HashMap<String, String>> timetable) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        for (int i = 0; i < timetable.size(); i++) {
            HashMap<String, String> tempHashMap = timetable.get(i);
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseNames.Timetable.DAY_NAME, tempHashMap.get("day"));
            contentValues.put(DatabaseNames.Timetable.TT_TIME, tempHashMap.get("time"));
            contentValues.put(DatabaseNames.Timetable.TT_SUBJECT, tempHashMap.get("subject"));
            sqLiteDatabase.insert(DatabaseNames.Timetable.TABLE_NAME, null, contentValues);
        }
        sqLiteDatabase.close();
    }

    public ArrayList<HashMap<String, String>> getTimeTable(String day) {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor timetable = sqLiteDatabase.query(DatabaseNames.Timetable.TABLE_NAME, new String[]{DatabaseNames.Timetable.TT_TIME, DatabaseNames.Timetable.TT_SUBJECT}, DatabaseNames.Timetable.DAY_NAME + "=?", new String[]{day}, null, null, null);
        if (timetable != null) {
            while (timetable.moveToNext()) {
                HashMap<String, String> temp = new HashMap<>();
                temp.put("time", timetable.getString(0));
                temp.put("subject", timetable.getString(1));
                arrayList.add(temp);
            }
        }
        timetable.close();
        sqLiteDatabase.close();
        return arrayList;
    }

}

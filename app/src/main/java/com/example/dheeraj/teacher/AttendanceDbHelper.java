package com.example.dheeraj.teacher;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.dheeraj.teacher.AttendanceContract.AttendanceContractEntry.LOGIN_PASSWORD;
import static com.example.dheeraj.teacher.AttendanceContract.AttendanceContractEntry.LOGIN_TABLE;
import static com.example.dheeraj.teacher.AttendanceContract.AttendanceContractEntry.LOGIN_USERNAME;

/**
 * Created by jitendra on 6/9/17.
 */

public class AttendanceDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="attendance1.db";

    String LOGIN_TABLE_QUERY="CREATE TABLE "+LOGIN_TABLE+
                                                    " ("+LOGIN_USERNAME+" TEXT NOT NULL,"+ LOGIN_PASSWORD+" TEXT NOT NULL);";


    public AttendanceDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(LOGIN_TABLE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

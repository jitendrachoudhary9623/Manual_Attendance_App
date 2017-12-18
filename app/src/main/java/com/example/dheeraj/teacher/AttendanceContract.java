package com.example.dheeraj.teacher;

import android.provider.BaseColumns;

/**
 * Created by jitendra on 6/9/17.
 */

public final class AttendanceContract
{

    public static  abstract class AttendanceContractEntry implements BaseColumns
    {
        public static final String LOGIN_TABLE="login";
        public static final String STUDENT_TABLE="student";
        public static final String ATTENDANCE_TABLE="attendance_table";


//TODO this are columns of login table
        public static final String LOGIN_USERNAME="username";
        public static final String LOGIN_PASSWORD="password";



        //TODO this are colums of Student Table





    }



}

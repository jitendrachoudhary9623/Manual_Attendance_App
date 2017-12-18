package com.example.dheeraj.teacher;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class SignUP extends AppCompatActivity {


    public void Register(View view) {
        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);

        String u, p;
        u = username.getText().toString();
        p = password.getText().toString();

        AttendanceDbHelper helper = new AttendanceDbHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AttendanceContract.AttendanceContractEntry.LOGIN_USERNAME, u);
        values.put(AttendanceContract.AttendanceContractEntry.LOGIN_PASSWORD, p);

        long rowInserted = db.insert(AttendanceContract.AttendanceContractEntry.LOGIN_TABLE, null, values);

        if(u.equals(""))
        {
            Toasty.info(getApplicationContext(),"Username cannot be blank",Toast.LENGTH_SHORT).show();
        }
       else if(p.equals(""))
        {
            Toasty.info(getApplicationContext(),"Enter a Valid Password",Toast.LENGTH_SHORT).show();
        }

        else if (rowInserted != -1) {
            Toasty.success(this, "Account Created", Toast.LENGTH_LONG).show();
            finish();
        }
        else
        {


                Toasty.error(getApplicationContext()," Something Went Wrong ",Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



    }
}

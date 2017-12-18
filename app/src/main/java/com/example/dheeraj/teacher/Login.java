package com.example.dheeraj.teacher;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yasic.library.particletextview.MovingStrategy.BidiHorizontalStrategy;
import com.yasic.library.particletextview.Object.ParticleTextViewConfig;
import com.yasic.library.particletextview.View.ParticleTextView;

import es.dmoral.toasty.Toasty;

import static com.example.dheeraj.teacher.AttendanceContract.AttendanceContractEntry.LOGIN_PASSWORD;
import static com.example.dheeraj.teacher.AttendanceContract.AttendanceContractEntry.LOGIN_TABLE;
import static com.example.dheeraj.teacher.AttendanceContract.AttendanceContractEntry.LOGIN_USERNAME;


public class Login extends AppCompatActivity {
    AttendanceDbHelper helper;

  //  ParticleTextView particleTextView;


    public void Register(View view)
    {
        Intent intent=new Intent(this,SignUP.class);
        startActivity(intent);
    }

    public void login(View view) {
        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);
        String u=null;
        u = username.getText().toString();
        String p =null;
        p= password.getText().toString();


        // type1= Integer.parseInt(type.getItemAtPosition(type.getSelectedItemPosition()).toString());
        AttendanceDbHelper helper1 = new AttendanceDbHelper(getApplicationContext());
        SQLiteDatabase db = helper1.getReadableDatabase();

        String[] col = {LOGIN_USERNAME, LOGIN_PASSWORD};

        String where = LOGIN_USERNAME + "='" + u + "' AND " + LOGIN_PASSWORD + "='" + p+"'";

        Cursor cur = db.query(LOGIN_TABLE, col, where, null, null, null, null);



        int count = cur.getCount();

        if (count >0) {
            if(u.isEmpty())
            {
                Toasty.error(getApplicationContext(),"Username cannot be blank",Toast.LENGTH_SHORT).show();
            }
            else if(p.isEmpty())
            {
                Toasty.warning(getApplicationContext(),"Enter a Valid Password",Toast.LENGTH_SHORT).show();
            }
            else if(cur!=null) {
                while (cur.moveToNext()) {
                    //   enter();


                    Intent intent =new Intent(Login.this,AppBase.class);
                    startActivity(intent);
                    //Toast.makeText(getApplicationContext(), "Welcome " + u, Toast.LENGTH_SHORT).show();
                    Toasty.success(getApplicationContext(), "Welcome " + u, Toast.LENGTH_LONG).show();
                }
                cur.close();

            }
            db.close();
        }
        else
        {

                Toasty.error(getApplicationContext(),"Wrong Password or UserName , Please Check your Details ",Toast.LENGTH_SHORT).show();

        }
    }
    public void enter()
    {
        Intent intent=new Intent(this,SignUP.class);
        startActivity(intent);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



    }
    @Override
    protected void onStart() {
        super.onStart();
        //   displayDatabaseInfo();
    }
    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = helper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                LOGIN_USERNAME
        };

        // Perform a query on the pets tablecd
        Cursor cursor = db.query(
                AttendanceContract.AttendanceContractEntry.LOGIN_TABLE,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        //  TextView displayView = (TextView) findViewById(R.id.textView);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The pets table contains <number of rows in Cursor> pets.
            // _id - name - breed - gender - weight
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // displayView.setText("The pets table contains " + cursor.getCount() + " pets.\n\n");
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }
}

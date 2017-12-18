package com.example.dheeraj.teacher;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dheeraj on 5/9/17.
 */

public class sms_activity extends AppCompatActivity{

    databaseHandler handler = AppBase.handler;
    Activity profileActivity = this;
    ListView listView;

    ArrayList<String> dates;
    ArrayList<String> datesALONE;
    ArrayList<Integer> hourALONE;
    ArrayList<Boolean> atts;
    Activity activity =this;
    private View v;
    String message="";
    String Phone="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        dates = new ArrayList<>();
        datesALONE = new ArrayList<>();
        hourALONE = new ArrayList<>();
        atts = new ArrayList<>();


        Button sendsms = (Button) findViewById(R.id.sendsms);
        assert sendsms != null;
        sendsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra("address" ,Phone);
                intent.putExtra(Intent.EXTRA_TEXT,message);
                startActivity(intent);
            }
        });




        Button find_Button = (Button)findViewById(R.id.search_find);
        assert find_Button != null;
        find_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                find_3(v);
            }
        });
    }

    public void find_3(View view) {
        dates.clear();
        atts.clear();
        EditText editText = (EditText)findViewById(R.id.PRN1);
        TextView textView = (TextView)findViewById(R.id.defaulterContentView);
        String reg = editText.getText().toString();
        String qu = "SELECT * FROM STUDENT WHERE regno = '" + reg.toUpperCase() + "'";
        String qc = "SELECT * FROM ATTENDANCE WHERE register = '" + reg.toUpperCase() + "';";
        String qd = "SELECT * FROM ATTENDANCE WHERE register = '" + reg.toUpperCase() + "' AND isPresent = 1";
        Cursor cursor = handler.execQuery(qu);
        //Start Count Here

        float att = 0f;
        Cursor cur = handler.execQuery(qc);
        Cursor cur1 = handler.execQuery(qd);
        if(cur==null)
        {
            Log.d("profile","cur null");
        }
        if(cur1==null)
        {
            Log.d("profile","cur1 null");
        }
        if(cur!=null&&cur1!=null)
        {
            cur.moveToFirst();
            cur1.moveToFirst();
            try{
                att = ((float) cur1.getCount()/cur.getCount())*100;
                if(att<=0)
                    att = 0f;
                Log.d("profile_activity","Total = " + cur.getCount() + " avail = "+cur1.getCount() + " per " + att);
            }catch (Exception e){att = -1;}
        }


        if(cursor==null||cursor.getCount()==0)
        {
            assert textView != null;
            textView.setText("No Data Available");
        }else
        {
            String attendance = "";
            if(att<0)
            {
                attendance = "Attendance Not Available";
            }else
                attendance = " Attendance " + att + " %";
            cursor.moveToFirst();
            String buffer = "";
           // buffer += "Admission No : " + cursor.getString(2)+ "\n";
            buffer += "Name : " + cursor.getString(0) + "\n";
            buffer += "Roll Number : " + cursor.getInt(4)+ "\n";
            buffer += "Semester : " + cursor.getString(1)+ "\n";

            Phone=cursor.getString(3);
           // buffer += "Phone Number" + cursor.getString(3)+ "\n";
            String status="";


            buffer += "" + attendance+ "\n";
            if(att<75)
            {
                status="Defaulter";
                buffer +="Attendance Status : "+status;
            }

            textView.setText(buffer);

            message=buffer;

            }
        }
    }



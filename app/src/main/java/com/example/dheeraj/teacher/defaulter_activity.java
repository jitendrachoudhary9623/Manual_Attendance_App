package com.example.dheeraj.teacher;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;


public class defaulter_activity extends AppCompatActivity {

    ListView listView;
    defaulterlistadapter adapter;
    ArrayAdapter<String> adapterSpinner;
    ArrayList<String> names;
    ArrayList<String> registers;
    ArrayList<String> defaultlist;
    ArrayList<String> defaultlist_name;
    Activity defaulter_activity = this;
    Spinner spinner;
    float at;

    Cursor[] cur=new Cursor[100000];
    Cursor[] cur1=new Cursor[100000];
    Cursor[] cu=new Cursor[100000];

    public static String time,period;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defaulter);

        //time = getIntent().getStringExtra("DATE");
        //period = getIntent().getStringExtra("PERIOD");

       // Log.d("Attendance", time + " -- " + period);
        listView = (ListView) findViewById(R.id.defalterlistview);
        names = new ArrayList<>();
        registers = new ArrayList<>();

        defaultlist=new ArrayList<>();
        defaultlist_name=new ArrayList<>();
        Button btn = (Button)findViewById(R.id.loadButton1);
        assert btn != null;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadListView1(v);
            }
        });

        Button sms=(Button) findViewById(R.id.sms);
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(defaulter_activity,sms_activity.class);
                startActivity(intent);
            }
        });

        spinner = (Spinner) findViewById(R.id.attendanceSpinner1);
        adapterSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, AppBase.divisions);
        assert spinner != null;
        spinner.setAdapter(adapterSpinner);


    }

    public void loadListView1(View view) {
        names.clear();
        registers.clear();
        defaultlist.clear();
        defaultlist_name.clear();
        String qu = "SELECT * FROM STUDENT WHERE cl = '" + spinner.getSelectedItem().toString() + "' " +
                "ORDER BY ROLL";

        Cursor cursor = AppBase.handler.execQuery(qu);

        if(cursor==null||cursor.getCount()==0)
        {

        }else
        {
            int ctr = 0;
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                names.add(cursor.getString(0) + " (" + cursor.getInt(4) + ')');
                registers.add(cursor.getString(2));
                cursor.moveToNext();
                ctr++;
            }
            if(ctr==0)
            {
               // Toast.makeText(getBaseContext(),"No Students Found",Toast.LENGTH_LONG).show();
                 Toasty.error(getBaseContext(),"No Students Found",Toast.LENGTH_LONG).show();

            }
            Log.d("Attendance", "Got " + ctr + " students");
        }
//        String qn = "SELECT * FROM NOTES WHERE cl = '" + spinner.getSelectedItem().toString() + "'";
//        String title = "";
//        String note = "";
//        Cursor cursor1 = AppBase.handler.execQuery(qn);
//        cursor1.moveToFirst();
//        while (!cursor1.isAfterLast())
//        {
//            title+= cursor1.getString(0) + "\n";
//            note+= cursor1.getString(1) + "\n";
//        }
//
//        // display notification
//        Log.d("notify","place the notification here" + title + "and" + note);


       for(int i=0;i<registers.size();i++) {
            String qc = "SELECT * FROM ATTENDANCE WHERE register = '" + registers.get(i).toString().toUpperCase() + "';";
            String qd = "SELECT * FROM ATTENDANCE WHERE register = '" + registers.get(i).toString().toUpperCase() + "' AND isPresent = 1";

            at = 0f;


           cur[i]= AppBase.handler.execQuery(qc);
            cur1[i]= AppBase.handler.execQuery(qd);
            if(cur[i]==null)
            {
                Log.d("profile","cur null");
            }
            if(cur1[i]==null)
            {
                Log.d("profile","cur1 null");
            }
            if(cur[i]!=null&&cur1[i]!=null)
            {
                cur[i].moveToFirst();
                cur1[i].moveToFirst();
                try{
                    at = ((float) cur1[i].getCount()/cur[i].getCount())*100;

                    cur1[i].close();
                    cur[i].close();
                    if(at<75.00)
                    {
                        defaultlist.add(registers.get(i));

                    }
                    else break;

                }catch (Exception e){at = -1;}
            }

            //defaulter_activity.this.finish();

        }



        for(int j=0;j<defaultlist.size();j++) {
            String qu1 = "SELECT * FROM STUDENT WHERE regno = '" + defaultlist.get(j).toString().toUpperCase() + "' " ;
            cu[j]=AppBase.handler.execQuery(qu1);
            cu[j].moveToFirst();

                defaultlist_name.add(cu[j].getString(0) + " (" + cu[j].getInt(4) + ')');

        }

        adapter = new defaulterlistadapter(defaulter_activity.this,names,registers,defaultlist_name);
        listView.setAdapter(adapter);
    }
}



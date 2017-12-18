package com.example.dheeraj.teacher;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dheeraj on 5/9/17.
 */

public class defaulterlistadapter extends BaseAdapter {
    ArrayList<String> nameList;
    ArrayList<String> registers;

    ArrayList<String> defaultlist_name;


    Activity activity;

    ArrayList<Boolean> attendanceList;
    public defaulterlistadapter(Activity activity,ArrayList<String> nameList,ArrayList<String> reg,ArrayList<String> def) {
        this.nameList = nameList;
        this.activity = activity;
        this.registers = reg;
        attendanceList = new ArrayList<>();

        this.defaultlist_name=def;

    }

    @Override
    public int getCount() {
        return defaultlist_name.size();
    }

    @Override
    public Object getItem(int position) {
        return defaultlist_name.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View v, ViewGroup parent) {
        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(activity);
            v = vi.inflate(R.layout.defaultlist_ele, null);
        }
        final int pos = position;


        TextView textView = (TextView) v.findViewById(R.id.attendanceName1);
        textView.setText(defaultlist_name.get(position));

        return v;
    }

    public void saveAll()
    {
        for(int i=0; i<nameList.size(); i++)
        {
            int sts = 1;
            if(attendanceList.get(i))
                sts = 1;
            else sts = 0;
            String qu = "INSERT INTO ATTENDANCE VALUES('" +attendanceActivity.time + "',"+
                    "" + attendanceActivity.period + ","+
                    "'" + registers.get(i) + "',"+
                    "" + sts + ");";
            AppBase.handler.execAction(qu);
            activity.finish();
        }
    }
}

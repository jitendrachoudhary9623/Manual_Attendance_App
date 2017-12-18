package com.example.dheeraj.teacher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;

import java.util.ArrayList;

public class AppBase extends AppCompatActivity {

    ArrayList<String> basicFields;
    gridAdapter adapter;
    public static ArrayList<String> divisions ;
    GridView gridView;
    public static databaseHandler handler;
    public static Activity activity;
    FloatingActionButton fab_menu,fab_setting,fab_about;
    Animation FabOpen,FabClose,FabRClockwise,FabRantiClockwise;
    boolean isOpen=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);
        basicFields = new ArrayList<>();
        handler = new databaseHandler(this);
        activity = this;
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().show();
        divisions = new ArrayList();
        divisions.add("SEM-1 ");
        divisions.add("SEM-2 ");
        divisions.add("SEM-3 ");
        divisions.add("SEM-4 ");
        divisions.add("SEM-5 ");
        divisions.add("SEM-6 ");
        divisions.add("SEM-7 ");
        divisions.add("SEM-8 ");
        gridView = (GridView)findViewById(R.id.grid);
        basicFields.add("TAKE ATTENDANCE");
        basicFields.add("SCHEDULER");
        basicFields.add("ADD SUBJECT\n");
        basicFields.add("SEARCH / ADD STUDENT");
        basicFields.add("CGPA CALCULATOR\n");
        basicFields.add("GENERATE DEFAULTER LIST");
        basicFields.add("GENERATE PDF");
        adapter = new gridAdapter(this,basicFields);
        gridView.setAdapter(adapter);

        fab_menu=(FloatingActionButton)findViewById(R.id.fab_menu);
        fab_setting=(FloatingActionButton)findViewById(R.id.fab_setting);
        fab_about=(FloatingActionButton)findViewById(R.id.fab_about);

        FabOpen= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        FabClose= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        FabRClockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rclockwise);
        FabRantiClockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.ranticlockwise);

        fab_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOpen)
                {
                    fab_setting.startAnimation(FabClose);
                    fab_about.startAnimation(FabClose);
                    fab_menu.startAnimation(FabRantiClockwise);
                    fab_setting.setClickable(false);
                    fab_about.setClickable(false);
                    isOpen=false;

                }
                else {

                    fab_setting.startAnimation(FabOpen);
                    fab_about.startAnimation(FabOpen);
                    fab_menu.startAnimation(FabRClockwise);
                    fab_setting.setClickable(true);
                    fab_about.setClickable(true);
                    isOpen=true;

                    fab_about.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent launchIntent = new Intent(AppBase.this,About.class);
                            startActivity(launchIntent);
                        }
                    });

                    fab_setting.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent launchIntent = new Intent(AppBase.this,SettingsActivity.class);
                            startActivity(launchIntent);
                        }
                    });

                }
            }
        });


    }

}

package com.example.dheeraj.teacher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yasic.library.particletextview.MovingStrategy.RandomMovingStrategy;
import com.yasic.library.particletextview.Object.ParticleTextViewConfig;
import com.yasic.library.particletextview.View.ParticleTextView;

/**
 * Created by dheeraj on 9/9/17.
 */

public class Main_activity extends Activity {

    ParticleTextView particleTextView;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        particleTextView= (ParticleTextView) findViewById(R.id.first_view);

        ParticleTextViewConfig config=new ParticleTextViewConfig.Builder()
                .setRowStep(7)
                .setColumnStep(7)
                .setTargetText("Attendance APP")
                .setReleasing(0.2)
                .setParticleRadius(4)
                .setMiniDistance(0.1)
                .setTextSize(120)
                .setMovingStrategy(new RandomMovingStrategy())
                .instance();
        particleTextView.setConfig(config);


        particleTextView.startAnimation();

        particleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Main_activity.this,Login.class);
                startActivity(i);

            }
        });



    }
}

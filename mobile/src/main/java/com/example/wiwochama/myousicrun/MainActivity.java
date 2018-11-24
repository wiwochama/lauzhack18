package com.example.wiwochama.myousicrun;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int heartRate = 60;
    private int stepPerMin = 100;
    private int speed = 10;
    private int pace = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume(){
        super.onResume();
        FakeRun fakeRun = new FakeRun(MainActivity.this);
        fakeRun.startRun();
    }

    public void setHeartRate(int heartRate){
        this.heartRate = heartRate;
        final TextView textView = findViewById(R.id.valueBPM);
        textView.setText(String.valueOf(heartRate));
    }

    public void setStepPerMin(int stepPerMin){
        this.stepPerMin = stepPerMin;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public int getHeartRate(){
        return this.heartRate;
    }

    public int getStepPerMin(){
        return this.stepPerMin;
    }

    public int getSpeed(){
        return this.speed;
    }

    public void increasePace(){
        if (this.pace < 10){
            this.pace++;
        }
    }

    public void decreasePace(){
        if (this.pace > -10){
            this.pace--;
        }
    }
}

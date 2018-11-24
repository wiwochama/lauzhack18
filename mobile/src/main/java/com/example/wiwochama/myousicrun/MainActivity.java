package com.example.wiwochama.myousicrun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private double heartRate = 60;
    private double stepPerMin = 100;
    private double speed = 10;
    private int pace = 0;

    private int stepPerMinBase = 160;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FakeRun fakeRun = new FakeRun(MainActivity.this);
        fakeRun.startRun();
    }

    public void setHeartRate(double heartRate){
        if(0<=heartRate && heartRate<=280) {
            this.heartRate = heartRate;
        }
        else{
            //you die !!!;
            throw new RuntimeException("You died: invalid heart rate");
        }
    }

    public void setStepPerMin(double stepPerMin){
        this.stepPerMin = stepPerMin;
    }

    public void setSpeed(double speed){
        this.speed = speed;
    }

    public double getHeartRate(){
        return this.heartRate;
    }

    public double getStepPerMin(){
        return this.stepPerMin;
    }

    public double getStepObjective(){
        return this.pace;
    }

    public double getSpeed(){
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

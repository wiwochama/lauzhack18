package com.example.wiwochama.myousicrun;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private double heartRate = 60;
    private double stepPerMin = 100;
    private double speed = 10;
    private int pace = 0;
    private boolean streaming = true;

    private final int stepPerMinBase = 130; // initial base stepPerMin for the session (if pace==0);
    private final int stepPerMinMax = 280; // maximum stepPerMin defined for the session

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

    public void setHeartRate(double heartRate){
            if(0<=heartRate && heartRate<=280) {
                this.heartRate = heartRate;
            }
            else{
                //you die !!!;
                throw new RuntimeException("You died: invalid heart rate");
            }
        final TextView textView = findViewById(R.id.valueBPM);
        textView.setText(String.valueOf(heartRate));
    }

    public void setStepPerMin(double stepPerMin){
        this.stepPerMin = stepPerMin;
        final TextView textView = findViewById(R.id.textStepsPerMin);
        textView.setText(String.valueOf(stepPerMin));
    }

    public void setSpeed(double speed){
        this.speed = speed;
        final TextView textView = findViewById(R.id.textSpeed);
        textView.setText(String.valueOf(speed));
    }

    public double getHeartRate(){
        return this.heartRate;
    }

    public double getStepPerMin(){
        return this.stepPerMin;
    }

    public double getStepObjective(){
        // linear interpolation
        return stepPerMinBase + this.pace*(stepPerMinMax-stepPerMinBase);
    }

    public double getSpeed(){
        return this.speed;
    }

    public double getStepPerMinMax(){
        return this.stepPerMinMax;
    }
    public double getStepPerMinBase(){
        return this.stepPerMinBase;
    }

    public void increasePace(View view){
        if (this.pace < 10){
            this.pace++;
            final TextView textView = findViewById(R.id.textPace);
            textView.setText(String.valueOf(this.pace));
        }
    }

    public void decreasePace(View view){
        if (this.pace > -10){
            this.pace--;
            final TextView textView = findViewById(R.id.textPace);
            textView.setText(String.valueOf(this.pace));
        }
    }

    public void playPause(View view){
        final ImageButton imageButton = findViewById(R.id.imageButtonPlayPause);
        if (streaming){
            imageButton.setImageResource(R.drawable.ic_play_arrow_black_86dp);
        } else {
            imageButton.setImageResource(R.drawable.ic_pause_black_86dp);
        }
        this.streaming = !this.streaming;
    }
}

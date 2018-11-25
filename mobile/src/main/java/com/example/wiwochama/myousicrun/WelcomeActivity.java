package com.example.wiwochama.myousicrun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {
    private int distance;
    private int time;
    private static final double STRIDE = 1.25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void startRunning(View view) {
        int stepPerMinute = (int)(((distance * 1000) / (time)) * (1.0 / STRIDE));
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        intent.putExtra("stepPerMinute", String.valueOf(stepPerMinute));
        startActivity(intent);
    }

    public void increaseKilometers(View view){
        this.distance += 1;
        final TextView textView = findViewById(R.id.textView5);
        textView.setText(String.valueOf(this.distance));
    }

    public void decreaseKilometers(View view){
        this.distance -= 1;
        final TextView textView = findViewById(R.id.textView5);
        textView.setText(String.valueOf(this.distance));
    }

    public void increaseTime(View view){
        this.time += 15;
        final TextView textView = findViewById(R.id.textView4);
        textView.setText(getStringFromTime(this.time));
    }

    public void decreaseTime(View view){
        this.time -= 15;
        final TextView textView = findViewById(R.id.textView4);
        textView.setText(getStringFromTime(this.time));
    }

    private String getStringFromTime(int time){
        int hours = time / 60;
        int minutes = time % 60;
        return (String.format("%02d", hours) + ":" + String.format("%02d", minutes));
    }
}

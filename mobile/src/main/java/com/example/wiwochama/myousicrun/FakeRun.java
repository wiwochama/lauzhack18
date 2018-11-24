package com.example.wiwochama.myousicrun;

import android.os.Handler;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class FakeRun {

    private MainActivity activity;

    public FakeRun(MainActivity act){
        this.activity = act;
    }

    public void startRun(){
        increaseHeartRate(63, 2000);
    }

    private void increaseHeartRate(final int hrLevel, int delay){
        if (hrLevel > activity.getHeartRate()) {
            final Handler handler = new Handler();
            Runnable runnableCode = new Runnable() {
                int hr = activity.getHeartRate();
                @Override
                public void run() {
                    // Do something here on the main thread
                    activity.setHeartRate(hr);
                    hr++;
                    // Repeat this the same runnable code block again another 2 seconds
                    // 'this' is referencing the Runnable object
                    handler.postDelayed(this, 2000);
                    if (hr >= hrLevel){
                        handler.removeCallbacks(this);
                    }
                }
            };
            handler.post(runnableCode);
        }
    }
}

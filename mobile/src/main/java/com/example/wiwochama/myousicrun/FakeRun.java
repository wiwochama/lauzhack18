package com.example.wiwochama.myousicrun;

import java.util.concurrent.TimeUnit;

public class FakeRun {
    private MainActivity activity;

    public FakeRun(MainActivity act){
        this.activity = act;
    }

    public void startRun(){
        increaseHeartRate(80, 2);
    }

    private void increaseHeartRate(int hrLevel, int delay){
        if (hrLevel > activity.getHeartRate()) {
            for (int hr = activity.getHeartRate(); hr < hrLevel; hr++) {
                activity.setHeartRate(hr);
                try {
                    TimeUnit.SECONDS.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

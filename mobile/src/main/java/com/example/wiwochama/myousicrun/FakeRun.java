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

    private void increaseHeartRate(double hrLevel, int delay){
        if (hrLevel > activity.getHeartRate()) {
            for (double hr = activity.getHeartRate(); hr < hrLevel; hr++) {
                activity.setHeartRate(hr);
                try {
                    TimeUnit.SECONDS.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void respondToMusic(double integrationStep, double l){
        // pas'(t) = l*(getStepsPace(t) - Pas(t))
        activity.setStepPerMin(activity.getStepPerMin()*(1-l*integrationStep)
                        + l*integrationStep*activity.getStepsPace());

        activity.setHeartRate(1600/(activity.getStepPerMin()-280));
    }

}

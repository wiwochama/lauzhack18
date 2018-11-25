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

    private void increaseHeartRate(final double hrLevel, double delay){
        if (hrLevel > activity.getHeartRate()) {
            final Handler handler = new Handler();
            Runnable runnableCode = new Runnable() {
                double hr = activity.getHeartRate();
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

    private void respondToMusic(double integrationStep, double l){
        // pas'(t) = l*(getStepsPace(t) - Pas(t))
        activity.setStepPerMin(activity.getStepPerMin()*(1-l*integrationStep)
                        + l*integrationStep*activity.getStepObjective());

        activity.setHeartRate(getHeartRateFromStepPerMin(activity.getStepPerMin()));
    }

    private double getStepPerMinFromHeartRate(double heartRate){
        double c= getHeartRateFromStepPerMin(100)*(100-activity.getStepPerMinMax()); // the value 100 is arbitrary
        return activity.getStepPerMinMax()- c/heartRate;
    }

    private double getHeartRateFromStepPerMin(double stepPerMin){
        // aim for the sweetspot heartRate activity.getStepPerMinBase() at 180
        double c = activity.getStepPerMinBase()*(activity.getStepPerMinMax()-180);
        return c/(stepPerMin-280);
    }
}

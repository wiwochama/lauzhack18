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

    public void startRunActiveResponse(){
        activity.setStepPerMin(100);
        final double integrationStep = 1;
        final Handler handler = new Handler();
        Runnable runnableCode = new Runnable(){
            @Override
                    public void run(){
                respondToMusic(integrationStep, 0.1);
                handler.postDelayed(this, (long) (integrationStep*1000));
            }
        };
        handler.post(runnableCode);
    }

    private void respondToMusic(double integrationStep, double l){
        // pas'(t) = l*(getStepsPace(t) - Pas(t))
        double delta = activity.getStepObjective()-activity.getStepPerMin();
        //activity.setStepPerMin(activity.getStepPerMin()+l*integrationStep*delta);
        if (activity.getStepPerMin() < activity.getStepObjective()) {

            activity.setStepPerMin(activity.getStepPerMin()+3);
            activity.setHeartRate(activity.getHeartRateModel().getHeartRateFromStepPerMin(activity.getStepPerMin()));
        }

        if (activity.getStepPerMin() > activity.getStepObjective()) {
            double old_steps = activity.getStepPerMin();
            double delta_ = old_steps-activity.getStepObjective();

            activity.setHeartRate(activity.getHeartRate()-activity.getHeartRateModel().getHeartRateFromStepPerMin_dec(activity.getStepPerMin(),delta_)*activity.getHeartRate());

            activity.setStepPerMin(activity.getStepPerMin()-1);
        }

    }
}

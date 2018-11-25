package com.example.wiwochama.myousicrun;

public class HeartRateModel {
    private final double stepPerMinMax;
    private final double stepPerMinBase;
    private final double heartRateBase;

    public HeartRateModel(double stepPerMinBase, double stepPerMinMax, double heartRateBase) {
        this.stepPerMinBase = stepPerMinBase;
        this.stepPerMinMax = stepPerMinMax;
        this.heartRateBase = heartRateBase;
    }

    public double getStepPerMinMax() {
        return this.stepPerMinMax;
    }

    public double getStepPerMinBase() {
        return this.stepPerMinBase;
    }

    public double getStepPerMinFromHeartRate(double heartRate) {
        double c = getHeartRateFromStepPerMin(100) * (100 - stepPerMinMax); // the value 100 is arbitrary
        return stepPerMinMax - c / heartRate;
    }

    public double getHeartRateFromStepPerMin(double stepPerMin) {
        // aim for the sweetspot heartRate activity.getStepPerMinBase() at 180
        double c =  heartRateBase* (stepPerMinMax - stepPerMinBase);
        return c / (stepPerMinMax-stepPerMin);
    }
}

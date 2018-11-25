package com.example.wiwochama.myousicrun;

import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.Queue;
import java.lang.Math;

public class MainActivity extends AppCompatActivity {

    MediaPlayer bass_player;
    MediaPlayer high_player;
    MediaPlayer mid_player;

    //VARIABLES
    private static double step_objective;

    {
        step_objective = 160;
    }

    private static double HR_objective;

    {
        HR_objective = 160;
    }

    private static double integration_time;

    {
        integration_time = 5;
    }

    private double heartRate = 60;
    private double stepPerMin = 100;
    private double speed = 10;
    private int pace = 0;
    private boolean streaming = true;

    private HeartRateModel heartRateModel;

    //private final int stepPerMinBase = 130; // initial base stepPerMin for the session (if pace==0);
    //private final int stepPerMinMax = 280; // maximum stepPerMin defined for the session
    private static float bass_volume = (float) (1 + Math.log(HR_objective / 220));
    private static float high_volume = (float) (Math.log(220 / step_objective));


    private double HR_mean = HR_objective;
    private double step_mean = step_objective;

    Queue<Double> HRs = new LinkedList<>();
    Queue<Double> steps = new LinkedList<>();

    private void initialize_queues() {
        for (int i = 0; i < (int) integration_time; i++) {
            steps.add(step_objective);
            HRs.add(HR_objective);
        }
    }

    // Handler for Music routine
    Handler handler = new Handler();
    private Runnable PeriodicUpdate = new Runnable() {
        @Override
        public void run() {
            // scheduled another events to be in 10 seconds later
            handler.postDelayed(PeriodicUpdate, 1000);
            // below is whatever you want to do

            double HR_now = getHeartRate();
            HRs.add(HR_now);

            double step_now = getStepPerMin();
            steps.add(step_now);
            double HR_old = HRs.remove();
            double step_old = steps.remove();

            //Volume Computation
            HR_mean += (HR_now - HR_old) / integration_time;
            bass_volume = (float) (1 + Math.log(HR_mean));

            step_mean += (step_now - step_old) / integration_time;
            high_volume = (float) (Math.log(220 / step_mean));


            //Music Pace
            PlaybackParams plbParam = new PlaybackParams();
            plbParam.setSpeed((float) step_objective / 160);

            //Music Transformation :D
            if (bass_player != null) {
                bass_player.setPlaybackParams(plbParam);
                bass_player.setVolume(bass_volume, bass_volume);

            }
            if (high_player != null) {
                high_player.setPlaybackParams(plbParam);
                high_player.setVolume(high_volume, high_volume);
            }
            if (mid_player != null) {
                mid_player.setPlaybackParams(plbParam);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize_queues();
    }

    public void play1(View v) {
        //play the first track after having checked if it needed initialization
        if (bass_player == null) {
            bass_player = MediaPlayer.create(this, R.raw.bass_freak);
            bass_player.setLooping(true);
            bass_player.setVolume(bass_volume, bass_volume);

        }
    }

    public void play2(View v) {
        if (high_player == null) {
            high_player = MediaPlayer.create(this, R.raw.high_freak);
            high_player.setLooping(true);
            high_player.setVolume(high_volume, high_volume);

        }
    }

    public void play3(View v) {
        if (mid_player == null) {
            mid_player = MediaPlayer.create(this, R.raw.mid_freak);
            mid_player.setLooping(true);
            float mid_volume = (high_volume + bass_volume) / 2;
            mid_player.setVolume(mid_volume, mid_volume);

        }
    }

    public void play_all(View v) {
        play1(v);
        play2(v);
        play3(v);

        bass_player.start();
        high_player.start();
        mid_player.start();
    }

    public void pause(View v) {
        //pause the music
        if (bass_player != null) {
            bass_player.pause();
        }
        if (high_player != null) {
            high_player.pause();
        }
        if (mid_player != null) {
            mid_player.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        int stepPerMinBase = 130;
        int stepPerMinMax = 280;
        this.heartRateModel = new HeartRateModel(stepPerMinBase, stepPerMinMax, 160);

        FakeRun fakeRun = new FakeRun(MainActivity.this);
        fakeRun.startRun();
    }

    public void setHeartRate(double heartRate) {
        if (0 <= heartRate && heartRate <= 280) {
            this.heartRate = heartRate;
        } else {
            //you die !!!;
            throw new RuntimeException("You died: invalid heart rate");
        }
        final TextView textView = findViewById(R.id.valueBPM);
        textView.setText(String.valueOf(heartRate));
    }

    public void setStepPerMin(double stepPerMin) {
        this.stepPerMin = stepPerMin;
        final TextView textView = findViewById(R.id.textStepsPerMin);
        textView.setText(String.valueOf(stepPerMin));
    }

    public void setSpeed(double speed) {
        this.speed = speed;
        final TextView textView = findViewById(R.id.textSpeed);
        textView.setText(String.valueOf(speed));
    }

    public double getHeartRate() {
        return this.heartRate;
    }

    public double getStepPerMin() {
        return this.stepPerMin;
    }

    public double getStepObjective() {
        // linear interpolation
        return heartRateModel.getStepPerMinBase() + this.pace *
                (heartRateModel.getStepPerMinMax() - getHeartRateModel().getStepPerMinBase());
    }

    public double getSpeed() {
        return this.speed;
    }

    public HeartRateModel getHeartRateModel() {
        return heartRateModel;
    }

    public void increasePace(View view) {
        if (this.pace < 10) {
            this.pace++;
            final TextView textView = findViewById(R.id.textPace);
            textView.setText(String.valueOf(this.pace));
        }
    }

    public void decreasePace(View view) {
        if (this.pace > -10) {
            this.pace--;
            final TextView textView = findViewById(R.id.textPace);
            textView.setText(String.valueOf(this.pace));
        }
    }

    public void playPause(View view) {
        final ImageButton imageButton = findViewById(R.id.imageButtonPlayPause);
        if (streaming) {
            imageButton.setImageResource(R.drawable.ic_play_arrow_black_86dp);

            pause(view);
        } else {
            imageButton.setImageResource(R.drawable.ic_pause_black_86dp);

            play_all(view);

            PeriodicUpdate.run();
        }
        this.streaming = !this.streaming;
    }

    public void stop(View v) {
        stopPlayer();
    }

    private void stopPlayer() {
        if (bass_player != null) {
            bass_player.release();
            bass_player = null;
//            Toast.makeText(this, "MediaPlayer released", Toast.LENGTH_SHORT).show();
        }
        if (high_player != null) {
            high_player.release();
            high_player = null;
//            Toast.makeText(this, "MediaPlayer released", Toast.LENGTH_SHORT).show();
        }
        if (mid_player != null) {
            mid_player.release();
            mid_player = null;
//            Toast.makeText(this, "MediaPlayer released", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }
}
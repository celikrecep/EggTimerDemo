package com.example.loyer.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekbar;
    TextView timerTxt;
    Boolean counterisActive = false;
    Button btnController;
    CountDownTimer countDownTimer;

    public void resetTimer()
    {
        timerTxt.setText("0:00");
        timerSeekbar.setProgress(30);
        countDownTimer.cancel();
        btnController.setText("Go!");
        timerSeekbar.setEnabled(true);
        counterisActive = false;

    }
    public void updateTimer(int secondsLeft)
    {
        int minutes = (int) secondsLeft/60;
        int seconds =secondsLeft - minutes * 60;

        String secondString = Integer.toString(seconds);

        if(seconds <= 9)
        {
            secondString = "0" + secondString;
        }


        timerTxt.setText(Integer.toString(minutes) + ":" + secondString);
    }

    public void controlTimer(View view)
    {
        if(counterisActive == false) {
            btnController.setText("Stop");
            counterisActive =true;
            timerSeekbar.setEnabled(false);
            // timerı ayarlıyoruz
         countDownTimer =   new CountDownTimer(timerSeekbar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long l) {

                    updateTimer((int) l / 1000);

                }

                @Override
                public void onFinish() {
                    Log.i("Finished", "timer finihed");
                    resetTimer();
                        //sayaç bitince siren çalacak
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mplayer.start();

                }
            }.start();
        }else{
            resetTimer();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            btnController = (Button)findViewById(R.id.btnController);
         timerSeekbar = (SeekBar)findViewById(R.id.skbrTimer);
         timerTxt = (TextView)findViewById(R.id.txtTime);
         timerSeekbar.setMax(600);
         timerSeekbar.setProgress(30);

        //seekbar üzerinden süreyi ayarlıyoruz
        timerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                    updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}

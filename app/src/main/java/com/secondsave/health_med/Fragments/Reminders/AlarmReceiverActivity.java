package com.secondsave.health_med.Fragments.Reminders;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.secondsave.health_med.R;

import java.io.IOException;

public class AlarmReceiverActivity extends AppCompatActivity {
        private MediaPlayer mMediaPlayer;
    private int id;

    @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.alarm);
            Bundle bundle = getIntent().getExtras();
            id = bundle.getInt("id",-1);
            TextView texto = findViewById(R.id.alarm_text);
            texto.setText("EL ID DE LA ALARMA ES:"+id);
            Button stopAlarm = (Button) findViewById(R.id.stopAlarm);
            stopAlarm.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View arg0, MotionEvent arg1) {
                    mMediaPlayer.stop();
                    finish();
                    return false;
                }
            });

            playSound(this, getAlarmUri());
        }

        private void playSound(Context context, Uri alert) {
            mMediaPlayer = new MediaPlayer();
            try {
                mMediaPlayer.setDataSource(context, alert);
                final AudioManager audioManager = (AudioManager) context
                        .getSystemService(Context.AUDIO_SERVICE);
                if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                    mMediaPlayer.prepare();
                    mMediaPlayer.start();
                }
            } catch (IOException e) {
                System.out.println("OOPS");
            }
        }

        //Get an alarm sound. Try for an alarm. If none set, try notification,
        //Otherwise, ringtone.
        private Uri getAlarmUri() {
            Uri alert = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_ALARM);
            if (alert == null) {
                alert = RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                if (alert == null) {
                    alert = RingtoneManager
                            .getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                }
            }
            return alert;
        }
    }

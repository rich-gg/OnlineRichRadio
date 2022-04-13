package gg.rich.onlinerichradio;

import static android.util.Log.*;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.Console;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button b_play;

    //    String stream= "https://rfimonde64k.ice.infomaniak.ch/rfimonde-64.mp3";
//        String stream= "http://radio.net.pk/embed/fm-101-live/";
    //    String stream= "http://radio.net.pk/play/fm-101-live/";
//        String stream = "http://stream.radioreklama.bg/radio1rock128";

    String stream = "https://www.ssaurel.com/tmp/mymusic.mp3";

    MediaPlayer mediaPlayer;
    boolean prepared = false;
    boolean started = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b_play = (Button) findViewById(R.id.Radio);
        b_play.setEnabled(false);
        System.out.println("test LOADING");
        b_play.setText("LOADING");

        mediaPlayer = new MediaPlayer();

        //DEPRECATED
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        //NEW
//        mediaPlayer.setAudioAttributes(
//                new AudioAttributes
//                        .Builder()
//                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                        .build());


        new PlayerTask().execute(stream);

        b_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (started) {
                    started = false;
                    mediaPlayer.pause();
                    System.out.println("test READY");
                    b_play.setText("PLAY");

                } else {
                    started = true;
                    mediaPlayer.start();
                    b_play.setText("PAUSE");
                }
            }
        });
    }

    class PlayerTask extends AsyncTask<String, Void, Boolean> {


        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                mediaPlayer.setDataSource(strings[0]);
                mediaPlayer.prepare();
                prepared = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            b_play.setEnabled(true);
            b_play.setText("PLAY");
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (started) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (started) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (prepared) {
            mediaPlayer.release();
        }
    }
}



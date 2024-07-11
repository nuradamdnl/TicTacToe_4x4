package com.TicTacToeProject.tictactoe;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class BackgroundMusicService extends Service {

    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.background_music);
        mediaPlayer.setLooping(true);  // Looping the music
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();  // Start playing the music
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
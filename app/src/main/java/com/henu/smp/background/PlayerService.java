package com.henu.smp.background;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.henu.smp.Constants;

/**
 * Created by liyngu on 12/15/15.
 */
public class PlayerService extends Service {
    private int mPlayMode = Constants.MUSIC_MODE_FIRST;
    private MediaPlayer mMediaPlayer;
    private boolean isPlayed = false;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            int operation = bundle.getInt(Constants.MUSIC_OPERATION);
            if (operation == Constants.MUSIC_START) {
                if (isPlayed) {
                    bundle.putInt(Constants.ACTION_OPERATION, Constants.ACTION_PAUSED);
                } else {
                    bundle.putInt(Constants.ACTION_OPERATION, Constants.ACTION_PLAYED);
                }
                isPlayed = !isPlayed;

                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(Constants.ACTION_NAME);
                broadcastIntent.putExtras(bundle);
                sendBroadcast(broadcastIntent);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void switchPlayMode() {
        mPlayMode++;
        if (mPlayMode > Constants.MUSIC_MODE_END) {
            mPlayMode = Constants.MUSIC_MODE_FIRST;
        }
    }
}

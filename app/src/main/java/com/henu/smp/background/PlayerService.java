package com.henu.smp.background;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.henu.smp.Constants;
import com.henu.smp.entity.Song;
import com.henu.smp.util.IntentUtil;

import java.io.IOException;
import java.util.List;

/**
 * Created by liyngu on 12/15/15.
 */
public class PlayerService extends Service {
    private int mPlayMode = Constants.MUSIC_MODE_FIRST;
    private int mPlayingIndex;
    private boolean isPlayed;
    private PlayerBinder mPlayerBinder = new PlayerBinder();
    private MediaPlayer mMediaPlayer;
    private List<Song> mSongList;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        int operation = bundle.getInt(Constants.MUSIC_OPERATION);
        if (operation == Constants.MUSIC_START) {
            if (mMediaPlayer == null) {
                this.initMediaPlayer();
            }
        } else if (Constants.MUSIC_STOP == operation) {
            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                mMediaPlayer = null;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }


    private void initMediaPlayer() {
        mMediaPlayer = new MediaPlayer();

        final MediaPlayer mediaPlayer = mMediaPlayer;
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
                isPlayed = true;
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                isPlayed = false;
                if (mMediaPlayer == null) {
                    return;
                }
                if (Constants.MUSIC_MODE_SINGLE == mPlayMode) {
                    start();
                } else {
                    next();
                }
            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mPlayerBinder;
    }

    private void reStart() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        isPlayed = false;
        this.start();
    }

    private void start() {
        List<Song> songList = mSongList;
        if (songList == null) {
            return;
        }

        for (Song song : songList) {
            Log.i("SONG", song.getTitle());
        }

        Bundle bundle = new Bundle();
        MediaPlayer mediaPlayer = mMediaPlayer;
        boolean isPlaying = mediaPlayer.isPlaying();
        if (isPlaying) {
            mediaPlayer.pause();
            bundle.putInt(Constants.ACTION_OPERATION, Constants.ACTION_PAUSED);
        } else if (isPlayed) {
            mediaPlayer.start();
            bundle.putInt(Constants.ACTION_OPERATION, Constants.ACTION_PLAYED);
        } else {
            try {
                mediaPlayer.reset();
                String songPath = mSongList.get(mPlayingIndex).getPath();
                mediaPlayer.setDataSource(songPath);
                mediaPlayer.prepareAsync();
                bundle.putInt(Constants.ACTION_OPERATION, Constants.ACTION_PLAYED);
            } catch (IOException e) {
                Log.i("player service", e.getMessage());
            }
        }
        IntentUtil.sendBroadcast(PlayerService.this, bundle);
    }

    private void stop() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
    }

    private void next() {
        int songsCount = mSongList.size();
        if (Constants.MUSIC_MODE_ORDER == mPlayMode) {
            if (mPlayingIndex + 1 > songsCount - 1) {
                this.stop();
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.ACTION_OPERATION, Constants.ACTION_STOPPED);
                IntentUtil.sendBroadcast(this, bundle);
                return;
            }
            mPlayingIndex++;
        } else if (Constants.MUSIC_MODE_CIRCLE == mPlayMode
                || Constants.MUSIC_MODE_SINGLE == mPlayMode) {
            mPlayingIndex = (mPlayingIndex + 1) % songsCount;
        } else if (Constants.MUSIC_MODE_RANDOM == mPlayMode) {
            mPlayingIndex = (int) (Math.random() * songsCount);
        }
        this.reStart();
    }

    private void previous() {
        int songsCount = mSongList.size();
        if (Constants.MUSIC_MODE_ORDER == mPlayMode) {
            if (mPlayingIndex - 1 < Constants.MUSIC_MODE_FIRST) {
                this.stop();
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.ACTION_OPERATION, Constants.ACTION_STOPPED);
                IntentUtil.sendBroadcast(this, bundle);
                return;
            }
            mPlayingIndex--;
        } else if (Constants.MUSIC_MODE_CIRCLE == mPlayMode
                || Constants.MUSIC_MODE_SINGLE == mPlayMode) {
            mPlayingIndex--;
            mPlayingIndex = mPlayingIndex < 0 ? Constants.MUSIC_MODE_COUNT - 1 : mPlayingIndex;
        } else if (Constants.MUSIC_MODE_RANDOM == mPlayMode) {
            mPlayingIndex = (int) (Math.random() * songsCount);
        }
        this.reStart();
    }

    private void changePlayMode() {
        mPlayMode = (mPlayMode + 1) % Constants.MUSIC_MODE_COUNT;
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ACTION_OPERATION, Constants.ACTION_MODE_CHANGED);
        bundle.putInt(Constants.MUSIC_PLAY_MODE, mPlayMode);
        IntentUtil.sendBroadcast(this, bundle);
    }

    public class PlayerBinder extends Binder {
        public void start(int playingIndex, List<Song> songList) {
            mSongList = songList;
            mPlayingIndex = playingIndex;
            PlayerService.this.reStart();
        }

        public void start() {
            PlayerService.this.start();
        }

        public void stop() {
            PlayerService.this.stop();
        }

        public void next() {
            PlayerService.this.next();
        }

        public void previous() {
            PlayerService.this.previous();
        }

        public void changePlayMode() {
            PlayerService.this.changePlayMode();
        }

        public boolean isPlaying() {
            return mMediaPlayer.isPlaying();
        }

        public boolean isPlayed() {
            return PlayerService.this.isPlayed;
        }

        public int getPlayMode() {
            return mPlayMode;
        }
    }
}

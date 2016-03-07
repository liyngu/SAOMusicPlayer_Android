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
        Log.i("MainActivity", operation+"");
        if (Constants.MUSIC_START == operation) {
            if (mMediaPlayer == null) {
                this.initMediaPlayer();
            }
        }  else if (Constants.MUSIC_STOP == operation) {
            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                mMediaPlayer = null;
            }
        }
        if (isPlayed) {
            if (Constants.MUSIC_PAUSE == operation) {
                if (mMediaPlayer.isPlaying()) {
                    start();
                }
            } else if (Constants.MUSIC_CONTINUE == operation) {
                if (!mMediaPlayer.isPlaying()) {
                    start();
                }
            } else if (Constants.MUSIC_RESTART == operation) {
                reStart();
            } else if (Constants.MUSIC_PREVIOUS == operation) {
                previous();
            } else if (Constants.MUSIC_NEXT == operation) {
                next();
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
                startProgressUpdate();
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                isPlayed = false;
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.MUSIC_PROGRESS_PERCENT, 100);
                bundle.putInt(Constants.ACTION_OPERATION, Constants.ACTION_PROGRESS_CHANGED);
                IntentUtil.sendBroadcast(PlayerService.this, bundle);
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

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        if (isPlayed) {
            for (Song song : mSongList) {
                Log.i("SONG", song.getTitle());
            }
            Song song = mSongList.get(mPlayingIndex);
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.ACTION_OPERATION, Constants.ACTION_PLAYED);
            bundle.putString(Constants.PLAYED_MUSIC_NAME, song.getTitle());
            IntentUtil.sendBroadcast(PlayerService.this, bundle);
            sendProgressUpdateData(new Bundle(), mMediaPlayer);
        }
    }

    private void sendProgressUpdateData(Bundle bundle, MediaPlayer mediaPlayer) {
        int currentTime = mediaPlayer.getCurrentPosition();
        int maxTime = mediaPlayer.getDuration();
        int percent = (int) ((double) currentTime / maxTime * 100);
        bundle.putInt(Constants.ACTION_OPERATION, Constants.ACTION_PROGRESS_CHANGED);
        bundle.putInt(Constants.MUSIC_PROGRESS_PERCENT, percent);
        String displayTime = parseTimeToString(currentTime) + "/" + parseTimeToString(maxTime);
        bundle.putString(Constants.MUSIC_DISPLAY_TIME, displayTime);
        bundle.putInt(Constants.MUSIC_PLAY_MODE, mPlayMode);
        IntentUtil.sendBroadcast(PlayerService.this, bundle);
    }

    private void startProgressUpdate() {
        Thread thread  = new Thread() {
            @Override
            public synchronized void run() {
                MediaPlayer mediaPlayer = mMediaPlayer;
                Bundle bundle = new Bundle();
                while (mediaPlayer.isPlaying()) {
                    sendProgressUpdateData(bundle, mediaPlayer);
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        Log.i("playerservice", e.getMessage());
                    }
                }
            }
        };
        thread.start();
    }

    private String parseTimeToString(int time) {
        time /= 1000;
        return time / 60 + ":" + time % 60;
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
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ACTION_OPERATION, Constants.ACTION_MUSIC_CHANGED);
        IntentUtil.sendBroadcast(PlayerService.this, bundle);
    }

    private void start() {
        List<Song> songList = mSongList;
        if (songList == null) {
            return;
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
            this.startProgressUpdate();
        } else {
            try {
                mediaPlayer.reset();
                Song song = mSongList.get(mPlayingIndex);
                mediaPlayer.setDataSource(song.getPath());
                mediaPlayer.prepareAsync();
                bundle.putInt(Constants.ACTION_OPERATION, Constants.ACTION_PLAYED);
                bundle.putString(Constants.PLAYED_MUSIC_NAME, song.getTitle());
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
        if (Constants.MUSIC_MODE_CIRCLE == mPlayMode
                || Constants.MUSIC_MODE_SINGLE == mPlayMode
                || Constants.MUSIC_MODE_ORDER == mPlayMode) {
            mPlayingIndex = (mPlayingIndex + 1) % songsCount;
        } else if (Constants.MUSIC_MODE_RANDOM == mPlayMode) {
            mPlayingIndex = this.getRandomIndex(mPlayingIndex, songsCount);
        }
        this.reStart();
    }

    private int getRandomIndex(int before, int max) {
        int index = (int) (Math.random() * max);
        if (index == before) {
            index = (index + 1) % max;
        }
        return index;
    }

    private void previous() {
        int songsCount = mSongList.size();
        if (Constants.MUSIC_MODE_CIRCLE == mPlayMode
                || Constants.MUSIC_MODE_SINGLE == mPlayMode
                || Constants.MUSIC_MODE_ORDER == mPlayMode) {
            mPlayingIndex--;
            mPlayingIndex = mPlayingIndex < 0 ? songsCount - 1 : mPlayingIndex;
        } else if (Constants.MUSIC_MODE_RANDOM == mPlayMode) {
            mPlayingIndex = this.getRandomIndex(mPlayingIndex, songsCount);
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

        public int getPlayedSongId() {
            return mSongList.get(mPlayingIndex).getSongId();
        }

        public int getPlayMode() {
            return mPlayMode;
        }
    }
}

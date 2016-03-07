package com.henu.smp.activity;

import android.content.ServiceConnection;
import android.graphics.Point;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.FrameLayout;

import com.henu.smp.Constants;
import com.henu.smp.R;
import com.henu.smp.background.PlayerService;
import com.henu.smp.base.BaseActivity;
import com.henu.smp.base.BaseButton;
import com.henu.smp.listener.SimpleAnimationListener;
import com.henu.smp.util.IntentUtil;
import com.henu.smp.util.WidgetUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by liyngu on 12/24/15.
 */
@ContentView(R.layout.activity_music_control)
public class MusicControlActivity extends BaseActivity {
    private PlayerService.PlayerBinder mPlayerBinder;
    private ServiceConnection mServiceConnection;
    private Animation mEndAnimation;
    private int mPlayMode;

    @ViewInject(R.id.start_btn)
    private BaseButton mStartBtn;

    @ViewInject(R.id.mode_btn)
    private BaseButton  modeBtn;

    @ViewInject(R.id.collect_btn)
    private BaseButton mCollectBtn;

    @ViewInject(R.id.control_panel)
    private FrameLayout mControlPanel;

    @Event(R.id.background)
    private void finishActivityEvent(View v) {
        mControlPanel.startAnimation(mEndAnimation);
    }

    @Event(R.id.start_btn)
    private void startEvent(View v) {
        mPlayerBinder.start();
    }

    @Event(R.id.next_btn)
    private void nextEvent(View v) {
        mPlayerBinder.next();
    }

    @Event(R.id.previous_btn)
    private void previousEvent(View v) {
        mPlayerBinder.previous();
    }

    @Event(R.id.mode_btn)
    private void changeModeEvent(View v) {
        mPlayerBinder.changePlayMode();
    }

    @Event(R.id.collect_btn)
    private void collectMusicEvent(View v) {
        int songId = mPlayerBinder.getPlayedSongId();
        mMusicService.changeCollectedStatus(songId);
        boolean isSelected = mCollectBtn.isSelected();
        mCollectBtn.setSelected(!isSelected);
    }

    @Override
    public void onBindService(IBinder binder) {
        mPlayerBinder = (PlayerService.PlayerBinder) binder;
        if (mPlayerBinder.isPlaying()) {
            mStartBtn.setBackgroundResource(R.drawable.music_pause_btn);
        } else {
            mStartBtn.setBackgroundResource(R.drawable.music_start_btn);
        }
        modeBtn.setBackgroundResource(Constants.PLAY_MODE_MAPPING[mPlayerBinder.getPlayMode()]);
        int songId = mPlayerBinder.getPlayedSongId();
        boolean isCollected = mMusicService.isCollectedSong(songId);
        if (isCollected) {
            mCollectBtn.setSelected(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getBundle();
        int startX = bundle.getInt(Constants.CLICKED_POINT_X);
        int startY = bundle.getInt(Constants.CLICKED_POINT_Y);
        mControlPanel.setX(startX);
        mControlPanel.setY(startY);

        int animOffset = WidgetUtil.getResourceDimen(R.dimen.music_control_anim_offset);
        int pointX = startX + animOffset;
        int pointY = startY + animOffset;
        Animation startAnimation = new ScaleAnimation(0, 1, 0, 1, pointX, pointY);
        startAnimation.setDuration(100);
        mControlPanel.startAnimation(startAnimation);

        mEndAnimation = new ScaleAnimation(1, 0, 1, 0, pointX, pointY);
        mEndAnimation.setDuration(100);
        mEndAnimation.setAnimationListener(new SimpleAnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
            }
        });

        mServiceConnection = IntentUtil.bindService(this, PlayerService.class);
    }

    @Override
    public void onReceivedData(Bundle bundle, int operation) {
        if (operation == Constants.ACTION_PLAYED) {
            mStartBtn.setBackgroundResource(R.drawable.music_pause_btn);
        } else if (operation == Constants.ACTION_PAUSED) {
            mStartBtn.setBackgroundResource(R.drawable.music_start_btn);
        } else if (operation == Constants.ACTION_MUSIC_CHANGED) {
            int songId = mPlayerBinder.getPlayedSongId();
            boolean isCollected = mMusicService.isCollectedSong(songId);
            mCollectBtn.setSelected(isCollected);
        }  else if (Constants.ACTION_MODE_CHANGED == operation) {
            mPlayMode = bundle.getInt(Constants.MUSIC_PLAY_MODE, mPlayMode);
            modeBtn.setBackgroundResource(Constants.PLAY_MODE_MAPPING[mPlayMode]);
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(mServiceConnection);
        super.onDestroy();
    }
}

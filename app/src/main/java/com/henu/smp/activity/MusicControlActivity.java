package com.henu.smp.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.henu.smp.Constants;
import com.henu.smp.R;
import com.henu.smp.background.PlayerService;
import com.henu.smp.base.BaseActivity;
import com.henu.smp.service.MusicService;
import com.henu.smp.util.IntentUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.Map;

/**
 * Created by liyngu on 12/24/15.
 */
public class MusicControlActivity extends BaseActivity {
    private int mPlayMode;
    private boolean isPlayed;

    @ViewInject(R.id.start_btn)
    private Button startBtn;

    @ViewInject(R.id.mode_btn)
    private Button modeBtn;

    @ViewInject(R.id.control_panel)
    private FrameLayout controlPanel;

    @ViewInject(R.id.background)
    private FrameLayout background;

    private PlayerService.PlayerBinder mPlayerBinder;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mPlayerBinder = (PlayerService.PlayerBinder) service;
            if (mPlayerBinder.isPlaying()) {
                startBtn.setText("暂停");
            } else {
                startBtn.setText("开始");
            }
            modeBtn.setText(Constants.PLAY_MODE_MAPPING[mPlayerBinder.getPlayMode()]);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @OnClick(R.id.start_btn)
    public void start(View v) {
        mPlayerBinder.start();
    }

    @OnClick(R.id.next_btn)
    public void next(View v) {
        mPlayerBinder.next();
    }

    @OnClick(R.id.previous_btn)
    public void previous(View v) {
        mPlayerBinder.previous();
    }

    @OnClick(R.id.mode_btn)
    public void changeMode(View v) {
        mPlayerBinder.changePlayMode();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_control);
        ViewUtils.inject(this);

        Bundle bundle = getBundle();
        int startX = bundle.getInt(Constants.CLICKED_POINT_X);
        int startY = bundle.getInt(Constants.CLICKED_POINT_Y);
        controlPanel.setX(startX);
        controlPanel.setY(startY);

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = new Intent(this, PlayerService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    public void onReceivedData(Bundle bundle, int operation) {
        if (operation == Constants.ACTION_PLAYED) {
            startBtn.setText("暂停");
            isPlayed = true;
        } else if (operation == Constants.ACTION_PAUSED) {
            startBtn.setText("开始");
            isPlayed = false;
        } else  if (Constants.ACTION_MODE_CHANGED == operation) {
            mPlayMode = bundle.getInt(Constants.MUSIC_PLAY_MODE, mPlayMode);
            modeBtn.setText(Constants.PLAY_MODE_MAPPING[mPlayMode]);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}

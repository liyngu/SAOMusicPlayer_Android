package com.henu.smp.activity;

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
import com.henu.smp.util.IntentUtil;

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
    private int mPlayMode;

    @ViewInject(R.id.start_btn)
    private Button startBtn;

    @ViewInject(R.id.mode_btn)
    private Button modeBtn;

    @ViewInject(R.id.control_panel)
    private FrameLayout controlPanel;

    @Event(R.id.background)
    private void finishActivityEvent(View v) {
        finish();
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

    @Override
    public void onBindService(IBinder binder) {
        mPlayerBinder = (PlayerService.PlayerBinder) binder;
        if (mPlayerBinder.isPlaying()) {
            startBtn.setText("暂停");
        } else {
            startBtn.setText("开始");
        }
        modeBtn.setText(Constants.PLAY_MODE_MAPPING[mPlayerBinder.getPlayMode()]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getBundle();
        int startX = bundle.getInt(Constants.CLICKED_POINT_X);
        int startY = bundle.getInt(Constants.CLICKED_POINT_Y);
        controlPanel.setX(startX);
        controlPanel.setY(startY);

        mServiceConnection = IntentUtil.bindService(this, PlayerService.class);
    }

    @Override
    public void onReceivedData(Bundle bundle, int operation) {
        if (operation == Constants.ACTION_PLAYED) {
            startBtn.setText("暂停");
        } else if (operation == Constants.ACTION_PAUSED) {
            startBtn.setText("开始");
        } else  if (Constants.ACTION_MODE_CHANGED == operation) {
            mPlayMode = bundle.getInt(Constants.MUSIC_PLAY_MODE, mPlayMode);
            modeBtn.setText(Constants.PLAY_MODE_MAPPING[mPlayMode]);
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(mServiceConnection);
        super.onDestroy();
    }
}

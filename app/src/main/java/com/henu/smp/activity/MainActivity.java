package com.henu.smp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.henu.smp.Constants;
import com.henu.smp.R;
import com.henu.smp.background.PlayerService;
import com.henu.smp.base.BaseActivity;
import com.henu.smp.entity.User;
import com.henu.smp.listener.SimpleScreenListener;
import com.henu.smp.util.IntentUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @ViewInject(R.id.background)
    private FrameLayout background;

    private SimpleScreenListener screenListener = new SimpleScreenListener() {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (velocityY < -3000 && Math.abs(velocityX) < Math.abs(velocityY)) {
                // TODO 初始界面向上滑动的操作
            } else if (velocityY > 3000 && Math.abs(velocityX) < Math.abs(velocityY)) {
                MainActivity.this.startMenu((int) e1.getX(), (int) e1.getY());
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        screenListener.setContext(this);
        background.setOnTouchListener(screenListener);
        background.setLongClickable(true);

        User user = getMyApplication().getUser();
        if (user == null) {
            user = new User();
            mUserService.save(user);
            getMyApplication().setUser(user);
        }

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.MUSIC_OPERATION, Constants.MUSIC_START);
        IntentUtil.startService(this, PlayerService.class, bundle);
    }

    public void startMenu(int x, int y) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.CLICKED_POINT_X, x);
        bundle.putInt(Constants.CLICKED_POINT_Y, y);
        IntentUtil.startActivity(this, MenuTreeActivity.class, bundle);
    }


    @Override
    public void onReceivedData(Bundle bundle, int operation) {
        if (Constants.ACTION_EXIT == operation) {
            Bundle param = new Bundle();
            param.putInt(Constants.MUSIC_OPERATION, Constants.MUSIC_STOP);
            IntentUtil.startService(this, PlayerService.class, param);
            Intent intent = new Intent();
            intent.setClass(this, PlayerService.class);
            stopService(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

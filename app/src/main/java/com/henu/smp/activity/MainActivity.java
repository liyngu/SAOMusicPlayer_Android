package com.henu.smp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.henu.smp.Constants;
import com.henu.smp.R;
import com.henu.smp.base.BaseActivity;
import com.henu.smp.base.BaseMenu;
import com.henu.smp.listener.SimpleScreenListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

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
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        screenListener.setContext(this);
        background.setOnTouchListener(screenListener);
        background.setLongClickable(true);
    }

    public void startMenu(int x, int y) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, MenuTreeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.MENU_START_POINT_X, x);
        bundle.putInt(Constants.MENU_START_POINT_Y, y);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    public void onReceivedData(Bundle bundle) {
        int operation = bundle.getInt(Constants.ACTION_OPERATION);
        if (operation == Constants.ACTION_PLAYED) {
            //this.messagePanel.setTitle("暂停");
            //startBtn.setText("暂停");
        } else if (operation == Constants.ACTION_PAUSED) {
            //this.messagePanel.setTitle("开始");
            //startBtn.setText("开始");
        }
    }
}

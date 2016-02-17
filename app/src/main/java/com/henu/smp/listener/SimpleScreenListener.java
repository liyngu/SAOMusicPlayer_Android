package com.henu.smp.listener;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by liyngu on 1/2/16.
 */
public class SimpleScreenListener implements View.OnTouchListener,
        GestureDetector.OnGestureListener {
    private GestureDetector mGestureDetector;
    private int mPressPointX = 0;
    private int mPressPointY = 0;

    public void setContext(Context context) {
        mGestureDetector = new GestureDetector(context, this);
        mGestureDetector.setIsLongpressEnabled(true);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        mPressPointX = (int) e.getX();
        mPressPointY = (int) e.getY();
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        int distanceX = (int) e.getX() - mPressPointX;
        int distanceY = (int) e.getY() - mPressPointY;
        // 如果点击后移动的距离小于50, 触发onClick事件
        if (distanceX * distanceX + distanceY * distanceY < 50) {
            return this.onClick();
        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    public boolean onClick() {
        return false;
    }
}

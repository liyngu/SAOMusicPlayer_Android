package com.henu.smp.listener;

import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;

import com.henu.smp.activity.MainActivity;

/**
 * Created by liyngu on 12/7/15.
 */
public class ScreenListener implements OnTouchListener, OnGestureListener {
    private GestureDetector detector;
    private Point startPoint;
    private Point movePoint;
    private MainActivity activity;

    public ScreenListener(MainActivity activity) {
        this.activity = activity;
        this.detector = new GestureDetector(activity, this);
        detector.setIsLongpressEnabled(true);
        startPoint = new Point();
        movePoint = new Point();
    }

    @Override
    public boolean onDown(MotionEvent e) {
        startPoint.x = (int) e.getX();
        startPoint.y = (int) e.getY();
        movePoint.x = (int) e.getX();
        movePoint.y = (int) e.getY();
     //   Log.i("", "press");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if ((e.getX() - startPoint.x) * (e.getX() - startPoint.x)
                + (e.getY() - startPoint.y) * (e.getY() - startPoint.y) < 100) {
            activity.undoOperation();
        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        activity.moveMenu((int)distanceX, (int)distanceY);
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
      //  Log.i("fling", velocityY+"");
        if (velocityY < - 6000// ScreenUtil.DpToPx(context, -150)
                && Math.abs(velocityX) < Math.abs(velocityY)) {
           // service.clossAllMenu();
        } else if (velocityY > 6000// ScreenUtil.DpToPx(context, 100)
                && Math.abs(velocityX) < Math.abs(velocityY)) {
            activity.startMenu((int) e1.getX(), (int) e1.getY());
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // 获取触控的点数
        int pointCount = event.getPointerCount();
        if (event.getAction() == MotionEvent.ACTION_MOVE ) {
            int moveX = (int) (movePoint.x - event.getX(0));
            int moveY = (int) (movePoint.y - event.getY(0));
            if (pointCount>1) {
               // service.moveMenu(moveX, moveY);
            }
            movePoint.x = (int) event.getX(0);
            movePoint.y = (int) event.getY(0);
        }
        detector.onTouchEvent(event);
        return true;
    }
}

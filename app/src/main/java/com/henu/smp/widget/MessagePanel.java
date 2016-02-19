package com.henu.smp.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.henu.smp.Constants;
import com.henu.smp.R;
import com.henu.smp.activity.MenuTreeActivity;
import com.henu.smp.activity.MusicControlActivity;
import com.henu.smp.background.PlayerService;
import com.henu.smp.util.WidgetUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by liyngu on 12/12/15.
 */
public class MessagePanel extends RelativeLayout implements SmpWidget {
    private MenuTreeActivity mActivity;
    private AnimatorSet mStartAnimation = new AnimatorSet();
    private View mLastClickedView;
    private PlayerService.PlayerBinder mPlayerBinder;
    private boolean isShowDetail = false;

    public void setActivity(MenuTreeActivity Activity) {
        mActivity = Activity;
    }

    public void setPlayerBinder(PlayerService.PlayerBinder playerBinder) {
        mPlayerBinder = playerBinder;
    }

    private TextView titleTxt;

    @ViewInject(R.id.info_image)
    private ImageView infoImage;

    @ViewInject(R.id.time_txt)
    private TextView timeTxt;

    @ViewInject(R.id.music_progress_bar)
    private ProgressBar musicProgressBar;

    @Event(R.id.info_image)
    private void showMusicControllerEvent(View v) {
        if (mPlayerBinder != null) {
            isShowDetail = mPlayerBinder.isPlayed();
        }
        if (!isShowDetail) {
            return;
        }
        Point vp = WidgetUtil.getViewPoint(v);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.CLICKED_POINT_X, vp.x);
        bundle.putInt(Constants.CLICKED_POINT_Y, vp.y);
        mActivity.showDialog(MusicControlActivity.class, bundle);
    }

    public void setDisplayTime(String time) {
        this.timeTxt.setText(time);
    }

    public MessagePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获得inflater 对象
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //通过resource在container中填充组件
        inflater.inflate(R.layout.message_panel, this);
        x.view().inject(this);

        titleTxt = (TextView) findViewById(R.id.title_txt);
        setVisibility(View.INVISIBLE);
    }

    public void setProgressPercent(int percent) {
        musicProgressBar.setProgress(percent);
    }

    public void setTitle(String title) {
        this.titleTxt.setText(title);
    }

    public void setLocationByView(View v) {
        // 获得v的起始坐标点
        Point vp = WidgetUtil.getViewPoint(v);
        // 设置x坐标
        int x = vp.x;
        // 设置y坐标
        int y = vp.y + v.getHeight() / 2;
        // 设置菜单坐标
        this.setLocationByIndicator(x, y);
        if (this.isShowed()) {
            //mStartAnimation.play(ObjectAnimator.ofFloat(this, "translationY", this.getOffsetY(vp), getTranslationY()));
            //mStartAnimation = new TranslateAnimation(0, 0, this.getOffsetY(vp), 0);
        } else {
            Animator scaleXAnimator = ObjectAnimator.ofFloat(this, "scaleX", 0f, 1f);
            Animator scaleYAnimator = ObjectAnimator.ofFloat(this, "scaleY", 0f, 1f);
            mStartAnimation.play(scaleXAnimator).with(scaleYAnimator);// = ObjectAnimator.ofFloat(this, "scaleX", 0f, 1f); //new ScaleAnimation(0, 1, 0, 1, x, y);
//            if (isShowDetail) {
//                final Animation showDetailAnimation = new ScaleAnimation(1, 1, 1, 1.3f, x, y);
//                showDetailAnimation.setDuration(1000);
//                showDetailAnimation.setFillAfter(true);
//                mStartAnimation.setAnimationListener(new SimpleAnimationListener() {
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//                        startAnimation(showDetailAnimation);
//                    }
//                });
//            }
        }
        mStartAnimation.setDuration(100);
        mLastClickedView = v;
    }
    private int getOffsetY(Point nowPoint) {
        Point oldPoint = WidgetUtil.getViewPoint(mLastClickedView);
        return oldPoint.y - nowPoint.y;
    }

    public boolean isShowed() {
        return View.VISIBLE == getVisibility();
    }

    public void setLocationByIndicator(int x, int y) {
        int width = getWidth();
        if (width == 0) {
            measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            // 重新获得高度
            width = getMeasuredWidth();
        }

        setLocation(x - width, y - WidgetUtil.getResourceDimen(R.dimen.message_panel_position_offset));
    }

    public void setLocation(float x, float y) {
        setX(x);
        setY(y);
    }

    public void setLocation(int x, int y) {
        this.setLocation((float) x, (float) y);
    }

    public void show() {
        if (mStartAnimation == null) {
            return;
        }
        //startAnimation(mStartAnimation);
        mStartAnimation.start();
        setVisibility(View.VISIBLE);
        mStartAnimation = new AnimatorSet();
    }

    public void hidden() {
        setVisibility(View.GONE);
    }
}

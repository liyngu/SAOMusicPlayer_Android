package com.henu.smp.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import com.henu.smp.Constants;
import com.henu.smp.MyApplication;
import com.henu.smp.R;
import com.henu.smp.activity.AlertActivity;
import com.henu.smp.activity.CreateListActivity;
import com.henu.smp.activity.MusicControlActivity;
import com.henu.smp.activity.ShowSongsActivity;
import com.henu.smp.listener.SimpleAnimationListener;
import com.henu.smp.service.MusicService;
import com.henu.smp.service.UserService;
import com.henu.smp.util.IntentUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by liyngu on 10/31/15.
 */
public abstract class BaseActivity extends Activity {
    protected final String LOG_TAG = this.getClass().getSimpleName();
    protected Animation mActivityExitAnimation;
    protected UserService mUserService;
    protected MusicService mMusicService;
    private MyApplication myApplication;

    @ViewInject(R.id.background)
    private FrameLayout mBackground;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constants.ACTION_NAME)) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    int operation = bundle.getInt(Constants.ACTION_OPERATION);
                    onReceivedData(bundle, operation);
                }
            }
        }
    };

    protected FrameLayout getBackground() {
        return mBackground;
    }

    protected void finishActivity() {
        mBackground.startAnimation(mActivityExitAnimation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ACTION_NAME);
        registerReceiver(mReceiver, filter);
        // 注册控件
        x.view().inject(this);

        mUserService = getMyApplication().getUserService();
        mMusicService = getMyApplication().getMusicService();

        mActivityExitAnimation = AnimationUtils.loadAnimation(this, R.anim.activity_exit);
        mActivityExitAnimation.setFillAfter(true);
        mActivityExitAnimation.setAnimationListener(new SimpleAnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
            }
        });
    }

    protected MyApplication getMyApplication() {
        if (myApplication == null) {
            myApplication = (MyApplication) getApplication();
        }
        return myApplication;
    }

    protected void onReceivedData(Bundle bundle, int operation) {

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    public void showDialog(Class<?> cls, Bundle bundle) {
//        Bundle bundle = new Bundle();
//        if (cls.isAssignableFrom(AlertActivity.class)) {
//            String[] paramsStr = params.split(Constants.CONNECTOR);
//            bundle.putString(Constants.ALERT_DIALOG_PARAMS, paramsStr[0]);
//            bundle.putInt(Constants.ALERT_DIALOG_TYPE, Integer.parseInt(paramsStr[1]));
//        } else if (cls.isAssignableFrom(MusicControlActivity.class)) {
//            String[] point = params.split(Constants.CONNECTOR);
//            bundle.putInt(Constants.CLICKED_POINT_X, Integer.parseInt(point[0]));
//            bundle.putInt(Constants.CLICKED_POINT_Y, Integer.parseInt(point[1]));
//        } else if (cls.isAssignableFrom(ShowSongsActivity.class)) {
//            bundle.putInt(Constants.SHOW_SONGS_MENU_ID, Integer.parseInt(params));
//        }
        IntentUtil.startActivityForResult(this, cls, bundle);
    }

    public void showDialog(String dialogClassName, Bundle bundle) {
        if (!dialogClassName.contains("Activity")) {
            Log.i(LOG_TAG, "class error: incorrect class name, it isn't an activity");
            return;
        }
        try {
            Class<?> cls = Class.forName(Constants.ACTIVITY_PACKAGE + "." + dialogClassName);
            this.showDialog(cls, bundle);
        } catch (ClassNotFoundException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
    }

    protected Bundle getBundle() {
        return getIntent().getExtras();
    }

    public void onBindService(IBinder binder) {

    }
}

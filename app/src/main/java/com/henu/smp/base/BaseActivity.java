package com.henu.smp.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.henu.smp.Constants;
import com.henu.smp.activity.AlertActivity;
import com.henu.smp.activity.MainActivity;
import com.henu.smp.activity.MusicControlActivity;
import com.henu.smp.activity.ShowSongsActivity;
import com.henu.smp.service.UserService;
import com.henu.smp.service.impl.MusicServiceImpl;
import com.henu.smp.service.impl.UserServiceImpl;
import com.henu.smp.util.IntentUtil;

import org.xutils.x;

/**
 * Created by liyngu on 10/31/15.
 */
public abstract class BaseActivity extends Activity {
    protected final String LOG_TAG = this.getClass().getSimpleName();
    protected static UserService mUserService = new UserServiceImpl();
    protected static MusicServiceImpl mMusicService = new MusicServiceImpl();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ACTION_NAME);
        registerReceiver(mReceiver, filter);
        // 注册控件
        x.view().inject(this);
    }

    protected void onReceivedData(Bundle bundle, int operation) {

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    public void showDialog(Class<?> cls, String params) {
        Bundle bundle = new Bundle();
        if (cls.isAssignableFrom(AlertActivity.class)) {
            String[] paramsStr = params.split(Constants.CONNECTOR);
            bundle.putString(Constants.ALERT_DIALOG_PARAMS, paramsStr[0]);
            bundle.putInt(Constants.ALERT_DIALOG_TYPE, Integer.parseInt(paramsStr[1]));
        } else if (cls.isAssignableFrom(MusicControlActivity.class)) {
            String[] point = params.split(Constants.CONNECTOR);
            bundle.putInt(Constants.CLICKED_POINT_X, Integer.parseInt(point[0]));
            bundle.putInt(Constants.CLICKED_POINT_Y, Integer.parseInt(point[1]));
        } else if (cls.isAssignableFrom(ShowSongsActivity.class)) {
            bundle.putInt(Constants.SHOW_SONGS_MENU_ID, Integer.parseInt(params));
        }
        IntentUtil.startActivity(this, cls, bundle);
    }

    public void showDialog(String dialogClassName, String params) {
        if (!dialogClassName.contains("Activity")) {
            Log.i(LOG_TAG, "class error: incorrect class name, it isn't an activity");
            return;
        }
        try {
            Class<?> cls = Class.forName(Constants.ACTIVITY_PACKAGE + "." + dialogClassName);
            this.showDialog(cls, params);
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

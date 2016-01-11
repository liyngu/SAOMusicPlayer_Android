package com.henu.smp.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.henu.smp.Constants;
import com.henu.smp.background.PlayerService;
import com.henu.smp.base.BaseActivity;

/**
 * Created by liyngu on 1/3/16.
 */
public class IntentUtil {

    public static void startActivity(BaseActivity activity, Class<?> cls, Bundle bundle) {
        Intent intent = IntentUtil.initIntent(bundle);
        intent.setClass(activity, cls);
        activity.startActivity(intent);
    }

    public static void startActivityForResult(BaseActivity activity, Class<?> cls, Bundle bundle) {
        Intent intent = IntentUtil.initIntent(bundle);
        intent.setClass(activity, cls);
        activity.startActivityForResult(intent, 0);
    }

    public static ServiceConnection bindService(final BaseActivity activity, Class<?> cls) {
        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                activity.onBindService(service);
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        Intent intent = new Intent(activity, cls);
        activity.bindService(intent, connection, Activity.BIND_AUTO_CREATE);
        return connection;
    }

    public static void startActivity(BaseActivity activity, Class<?> cls) {
        IntentUtil.startActivity(activity, cls, null);
    }

    public static void startService(BaseActivity activity, Class<?> cls, Bundle bundle) {
        Intent intent = IntentUtil.initIntent(bundle);
        intent.setClass(activity, cls);
        activity.startService(intent);
    }

//    public static IBinder bindService(Activity activity, Class<?> cls) {
//        intent.setClass(activity,  cls);
//        activity.startService(intent);
//    }

    public static void sendBroadcast(Context context, Bundle bundle) {
        Intent intent = IntentUtil.initIntent(bundle);
        intent.setAction(Constants.ACTION_NAME);
        context.sendBroadcast(intent);
    }

    private static Intent initIntent(Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        return intent;
    }
}

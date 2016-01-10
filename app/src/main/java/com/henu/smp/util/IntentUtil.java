package com.henu.smp.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.henu.smp.Constants;

/**
 * Created by liyngu on 1/3/16.
 */
public class IntentUtil {

    public static void startActivity(Activity activity, Class<?> cls, Bundle bundle) {
        Intent intent = IntentUtil.initIntent(bundle);
        intent.setClass(activity, cls);
        activity.startActivity(intent);
    }

    public static void startActivity(Activity activity, Class<?> cls) {
        IntentUtil.startActivity(activity, cls, null);
    }

    public static void startService(Activity activity, Class<?> cls, Bundle bundle) {
        Intent intent = IntentUtil.initIntent(bundle);
        intent.setClass(activity,  cls);
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

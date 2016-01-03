package com.henu.smp.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.henu.smp.Constants;

/**
 * Created by liyngu on 1/3/16.
 */
public class IntentUtil {

    public static void startActivity(Activity activity, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public static void startActivity(Activity activity, Class<?> cls) {
        IntentUtil.startActivity(activity, cls, null);
    }

    public static void sendBroadcast(Context context, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(Constants.ACTION_NAME);
        intent.putExtras(bundle);
        context.sendBroadcast(intent);
    }
}

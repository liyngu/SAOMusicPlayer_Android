package com.henu.smp.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.henu.smp.Constants;
import com.henu.smp.activity.MainActivity;
import com.henu.smp.activity.MusicControlActivity;
import com.henu.smp.service.UserService;
import com.henu.smp.model.User;
import com.henu.smp.dto.MenuTree;

import java.util.List;

/**
 * Created by liyngu on 10/31/15.
 */
public abstract class BaseActivity extends Activity {
    protected final String LOG_TAG = this.getClass().getSimpleName();
    protected static UserService userService;
    protected static User user;
    static {
        userService = new UserService();
        //user = userService.getLocal();
    }
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(Constants.ACTION_NAME)){
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    onReceivedData(bundle);
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
    }

    protected void onReceivedData(Bundle bundle) {

    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    public void showDialog(View v) {
        Intent intent = new Intent();
        //intent.setClass(MainActivity.this, MusicControlActivity.class);
        startActivity(intent);
    }
}

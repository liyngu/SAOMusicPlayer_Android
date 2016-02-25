package com.henu.smp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.coderqi.publicutil.voice.VoiceToWord;
import com.henu.smp.Constants;
import com.henu.smp.R;
import com.henu.smp.background.PlayerService;
import com.henu.smp.base.BaseActivity;
import com.henu.smp.entity.User;
import com.henu.smp.listener.SimpleHttpCallBack;
import com.henu.smp.listener.SimpleScreenListener;
import com.henu.smp.util.HttpUtil;
import com.henu.smp.util.IntentUtil;
import com.henu.smp.util.JSONUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @ViewInject(R.id.background)
    private FrameLayout background;
    @ViewInject(R.id.speechRecognition)
    private Button speechRecognition;

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

        screenListener.setContext(this);
        background.setOnTouchListener(screenListener);
        background.setLongClickable(true);

        User user = getMyApplication().getUser();
        if (user == null) {
            user = new User();
            mUserService.save(user);
            getMyApplication().setUser(user);
        }
//        user.setId(133);
//        user.setPassword("1121");
//        user.setUsername("hhhhh");
//        String jsonStr = JSONUtil.parseToString(user);
//        Log.i("user", jsonStr);
//        HttpUtil.doGet("user/1", new SimpleHttpCallBack<String>(){
//            @Override
//            public void onSuccess(String s) {
//                Log.i("eeee", s);
//            }
//        });
        //HttpUtil.doPost("user", jsonStr);

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.MUSIC_OPERATION, Constants.MUSIC_START);
        IntentUtil.startService(this, PlayerService.class, bundle);

        speechRecognition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VoiceToWord voice = new VoiceToWord(MainActivity.this, "534e3fe2");
                voice.GetWordFromVoice();
            }
        });
    }

    public void startMenu(int x, int y) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.CLICKED_POINT_X, x);
        bundle.putInt(Constants.CLICKED_POINT_Y, y);
        IntentUtil.startActivityForResult(this, MenuTreeActivity.class, bundle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (RESULT_OK == resultCode) {
            Bundle param = new Bundle();
            param.putInt(Constants.MUSIC_OPERATION, Constants.MUSIC_STOP);
            IntentUtil.startService(this, PlayerService.class, param);
            Intent intent = new Intent();
            intent.setClass(this, PlayerService.class);
            stopService(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}

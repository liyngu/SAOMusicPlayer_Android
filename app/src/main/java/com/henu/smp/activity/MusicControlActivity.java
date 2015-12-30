package com.henu.smp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.henu.smp.Constants;
import com.henu.smp.R;
import com.henu.smp.background.PlayerService;
import com.henu.smp.base.BaseActivity;
import com.henu.smp.service.MusicService;

/**
 * Created by liyngu on 12/24/15.
 */
public class MusicControlActivity extends BaseActivity {
    private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_control);

        startBtn = (Button) findViewById(R.id.start_btn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.MUSIC_OPERATION, Constants.MUSIC_START);
                Intent intent = new Intent(MusicControlActivity.this, PlayerService.class);
                intent.putExtras(bundle);
                startService(intent);
            }
        });
    }

    @Override
    public void onReceivedData(Bundle bundle) {
        int operation = bundle.getInt(Constants.ACTION_OPERATION);
        if (operation == Constants.ACTION_PLAYED) {
            startBtn.setText("暂停");
        } else if (operation == Constants.ACTION_PAUSED) {
            startBtn.setText("开始");
        }
    }
}

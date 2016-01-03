package com.henu.smp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.henu.smp.Constants;
import com.henu.smp.R;
import com.henu.smp.background.PlayerService;
import com.henu.smp.base.BaseActivity;
import com.henu.smp.service.MusicService;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by liyngu on 12/24/15.
 */
public class MusicControlActivity extends BaseActivity {
    private Button startBtn;

    @ViewInject(R.id.control_panel)
    private FrameLayout controlPanel;

    @ViewInject(R.id.background)
    private FrameLayout background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_control);
        ViewUtils.inject(this);

        Bundle bundle = getIntent().getExtras();
        int startX = bundle.getInt(Constants.CLICKED_POINT_X);
        int startY = bundle.getInt(Constants.CLICKED_POINT_Y);
        controlPanel.setX(startX);
        controlPanel.setY(startY);

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
        background.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onReceivedData(Bundle bundle, int operation) {
        if (operation == Constants.ACTION_PLAYED) {
            startBtn.setText("暂停");
        } else if (operation == Constants.ACTION_PAUSED) {
            startBtn.setText("开始");
        }
    }
}

package com.henu.smp.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.henu.smp.R;
import com.henu.smp.base.BaseActivity;
import com.henu.smp.base.BaseButton;

/**
 * Created by leen on 10/31/15.
 */
public class AlertActivity extends BaseActivity implements View.OnClickListener {
    private BaseButton okBtn;
    private BaseButton cancelBtn;
    private TextView textView;
    private FrameLayout background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_alert_dialog);
        okBtn = (BaseButton) findViewById(R.id.ok_btn);
        cancelBtn = (BaseButton) findViewById(R.id.cancel_btn);
        background = (FrameLayout) findViewById(R.id.background);
        okBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        background.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}

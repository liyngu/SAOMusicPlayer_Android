package com.henu.smp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.henu.smp.Constants;
import com.henu.smp.R;
import com.henu.smp.base.BaseActivity;
import com.henu.smp.base.BaseButton;
import com.henu.smp.util.IntentUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by leen on 10/31/15.
 */
public class AlertActivity extends BaseActivity implements View.OnClickListener {
    private int mDialogConfirmType;

    @ViewInject(R.id.ok_btn)
    private BaseButton okBtn;

    @ViewInject(R.id.cancel_btn)
    private BaseButton cancelBtn;

    @ViewInject(R.id.content_txt)
    private TextView contentTxt;

    @ViewInject(R.id.background)
    private FrameLayout background;

    View.OnClickListener confirmListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            if (Constants.ALERT_DIALOG_TYPE_EXIT == mDialogConfirmType) {
                bundle.putInt(Constants.ACTION_OPERATION, Constants.ACTION_EXIT);
                IntentUtil.sendBroadcast(AlertActivity.this, bundle);
            }
            AlertActivity.this.finish();
        }
    };

    View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertActivity.this.finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_alert_dialog);
        ViewUtils.inject(this);

        Bundle bundle = getIntent().getExtras();
        mDialogConfirmType = bundle.getInt(Constants.ALERT_DIALOG_TYPE, Constants.EMPTY_INTEGER);
        String text = bundle.getString(Constants.ALERT_DIALOG_PARAMS, Constants.EMPTY_STRING);
        contentTxt.setText(text);

        okBtn.setOnClickListener(confirmListener);
        cancelBtn.setOnClickListener(cancelListener);
        //background.setOnClickListener(cancelListener);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}

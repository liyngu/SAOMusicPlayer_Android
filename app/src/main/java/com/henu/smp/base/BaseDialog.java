package com.henu.smp.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.henu.smp.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by liyngu on 12/12/15.
 */
public abstract class BaseDialog extends BaseActivity {
    private FrameLayout background;

    private BaseButton okBtn;

    private TextView titleTxt;

    View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        BaseButton cancelBtn = (BaseButton) findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(cancelListener);
        okBtn = (BaseButton) findViewById(R.id.ok_btn);
    }

    protected void setOnButtonOnclickListener(View.OnClickListener listener) {
        okBtn.setOnClickListener(listener);
    }
}

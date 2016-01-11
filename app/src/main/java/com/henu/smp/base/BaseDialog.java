package com.henu.smp.base;

import android.view.View;
import android.widget.TextView;

import com.henu.smp.R;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


/**
 * Created by liyngu on 12/12/15.
 */
public abstract class BaseDialog extends BaseActivity {
    @ViewInject(R.id.ok_btn)
    private BaseButton mOkBtn;

    @ViewInject(R.id.cancel_btn)
    private BaseButton mCancelBtn;

    @ViewInject(R.id.title_txt)
    private TextView mTitleTxt;

    @Event({R.id.background, R.id.cancel_btn})
    private void finishActivityEvent(View v) {
        finish();
    }

    @Event(R.id.ok_btn)
    private void confirmEvent(View v) {
        this.okBtnOnclickListener(v);
    }

    protected void okBtnOnclickListener(View v) {
    }

    protected void disabledButtons() {
        mOkBtn.setEnabled(false);
        mCancelBtn.setEnabled(false);
    }
}

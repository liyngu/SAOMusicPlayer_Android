package com.henu.smp.base;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.henu.smp.Constants;
import com.henu.smp.R;
import com.henu.smp.listener.SimpleAnimationListener;
import com.henu.smp.util.StringUtil;
import com.henu.smp.widget.EmptyDialog;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


/**
 * Created by liyngu on 12/12/15.
 */
public abstract class BaseDialog extends BaseActivity {
    private Animation mEndAnimation;
    @ViewInject(R.id.ok_btn)
    private BaseButton mOkBtn;

    @ViewInject(R.id.cancel_btn)
    private BaseButton mCancelBtn;

    @ViewInject(R.id.title_txt)
    private TextView mTitleTxt;

    @ViewInject(R.id.dialog)
    private EmptyDialog mDialog;

    @Event({R.id.background, R.id.cancel_btn})
    private void finishActivityEvent(View v) {
        finishActivity();
    }

    @Event(R.id.ok_btn)
    private void confirmEvent(View v) {
        this.okBtnOnclickListener(v);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getBundle();
        String name = bundle.getString(Constants.MENU_NAME);
        mTitleTxt.setText(StringUtil.formatToTitle(name));
        Animation startAnimation = AnimationUtils.loadAnimation(this, R.anim.dialog_start);
        mDialog.startAnimation(startAnimation);
    }

    protected EmptyDialog getDialog () {
        return mDialog;
    }

    protected void okBtnOnclickListener(View v) {
    }

    protected void disabledButtons() {
        mOkBtn.setEnabled(false);
        mCancelBtn.setEnabled(false);
    }
}

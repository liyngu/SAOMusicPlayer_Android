package com.henu.smp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.henu.smp.Constants;
import com.henu.smp.R;
import com.henu.smp.base.BaseDialog;
import com.henu.smp.util.IntentUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
/**
 * Created by leen on 10/31/15.
 */
@ContentView(R.layout.activity_alert_dialog)
public class AlertActivity extends BaseDialog {
    private int mDialogConfirmType;

    @ViewInject(R.id.content_txt)
    private TextView mContentTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getBundle();
        mDialogConfirmType = bundle.getInt(Constants.ALERT_DIALOG_TYPE, Constants.EMPTY_INTEGER);
        String text = bundle.getString(Constants.ALERT_DIALOG_PARAMS, Constants.EMPTY_STRING);
        mContentTxt.setText(text);
    }

    @Override
    protected void okBtnOnclickListener(View v) {
        Bundle bundle = new Bundle();
        if (Constants.ALERT_DIALOG_TYPE_EXIT == mDialogConfirmType) {
            bundle.putInt(Constants.ACTION_OPERATION, Constants.ACTION_EXIT);
            IntentUtil.sendBroadcast(AlertActivity.this, bundle);
        } else if (Constants.ALERT_DIALOG_TYPE_DELETE_ALL == mDialogConfirmType) {
            mUserService.deleteAll();
            mMusicService.deleteAll();
            bundle.putInt(Constants.ACTION_OPERATION, Constants.ACTION_DELETE_ALL);
            IntentUtil.sendBroadcast(AlertActivity.this, bundle);
            setResult(RESULT_OK);
        }
        finishActivity();
    }
}

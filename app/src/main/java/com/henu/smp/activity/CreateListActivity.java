package com.henu.smp.activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.henu.smp.Constants;
import com.henu.smp.R;
import com.henu.smp.base.BaseButton;
import com.henu.smp.base.BaseDialog;
import com.henu.smp.base.BaseMenu;
import com.henu.smp.listener.SimpleAnimationListener;
import com.henu.smp.util.IntentUtil;
import com.henu.smp.util.StringUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by liyngu on 12/23/15.
 */
@ContentView(R.layout.activity_create_list)
public class CreateListActivity extends BaseDialog {

    @ViewInject(R.id.content_input)
    private EditText mContentInput;

    @ViewInject(R.id.create_operation_menu)
    private BaseMenu mCreateOperationMenu;

    @ViewInject(R.id.create_menu_list_btn)
    private BaseButton mCreateMenuListBtn;

    @ViewInject(R.id.create_music_list_btn)
    private BaseButton mCreateMusicListBtn;

    @Override
    protected void okBtnOnclickListener(View v) {
        String text = mContentInput.getText().toString();
        if (StringUtil.isEmpty(text)) {
            return;
        }
        final int offsetX = mCreateOperationMenu.getWidth();
        Animation animation = new TranslateAnimation(0, -offsetX, 0, 0);
        animation.setDuration(100);
        animation.setFillAfter(true);
        animation.setAnimationListener(new SimpleAnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                mCreateOperationMenu.setLocationByView(getDialog());
                mCreateOperationMenu.setX(mCreateOperationMenu.getX() - offsetX);
                mCreateOperationMenu.show();
            }
        });
        getDialog().startAnimation(animation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCreateMenuListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createList(Constants.CREATE_TYPE_MENU_LIST);
            }
        });
        mCreateMusicListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createList(Constants.CREATE_TYPE_MUSIC_LIST);
            }
        });

    }

    private void createList(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ACTION_OPERATION, Constants.ACTION_CREATE_LIST);
        bundle.putInt(Constants.CREATE_LIST_TYPE, type);
        bundle.putString(Constants.CREATE_LIST_NAME, mContentInput.getText().toString());
        IntentUtil.sendBroadcast(CreateListActivity.this, bundle);
        finishActivity();
    }
}

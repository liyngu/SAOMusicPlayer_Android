package com.henu.smp.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;

import com.henu.smp.R;
import com.henu.smp.base.BaseMenu;
import com.henu.smp.widget.CircleButton;

/**
 * Created by liyngu on 10/31/15.
 */
public class MainMenuLayout extends BaseMenu implements OnClickListener, View.OnLongClickListener {
    private CircleButton userBtn;
    private CircleButton listBtn;
    private CircleButton searchBtn;
    private CircleButton settingBtn;
    private CircleButton exitBtn;

    public MainMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs, R.layout.menu_main);
        // 初始化按钮
        userBtn = (CircleButton) findViewById(R.id.user_btn);
        listBtn = (CircleButton) findViewById(R.id.list_btn);
        searchBtn = (CircleButton) findViewById(R.id.search_btn);
        settingBtn = (CircleButton) findViewById(R.id.setting_btn);
        exitBtn = (CircleButton) findViewById(R.id.exit_btn);
        // 设置按钮的事件监听
        userBtn.setOnClickListener(this);
        listBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        settingBtn.setOnClickListener(this);
        exitBtn.setOnClickListener(this);

        settingBtn.setOnLongClickListener(this);
        userBtn.setOnLongClickListener(this);
        listBtn.setOnLongClickListener(this);
        searchBtn.setOnLongClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (R.id.exit_btn == v.getId()) {
            getActivity().showDialog(v);
        } else {
            getActivity().showLayoutByView(v);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        getActivity().showOperationMenu(v);
        return true;
    }
}
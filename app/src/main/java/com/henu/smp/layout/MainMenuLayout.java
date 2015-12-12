package com.henu.smp.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;

import com.henu.smp.MainActivity;
import com.henu.smp.R;
import com.henu.smp.base.BaseContainer;
import com.henu.smp.widget.MenuButton;

/**
 * Created by liyngu on 10/31/15.
 */
public class MainMenuLayout extends BaseContainer implements OnClickListener, View.OnLongClickListener {
    private MenuButton userBtn;
    private MenuButton listBtn;
    private MenuButton searchBtn;
    private MenuButton settingBtn;
    private MenuButton exitBtn;

    public MainMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs, R.layout.menu_main);
        // 初始化按钮
        userBtn = (MenuButton) findViewById(R.id.user_btn);
        listBtn = (MenuButton) findViewById(R.id.list_btn);
        searchBtn = (MenuButton) findViewById(R.id.search_btn);
        settingBtn = (MenuButton) findViewById(R.id.setting_btn);
        exitBtn = (MenuButton) findViewById(R.id.exit_btn);
        // 设置按钮的事件监听
        userBtn.setOnClickListener(this);
        listBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        settingBtn.setOnClickListener(this);
        exitBtn.setOnClickListener(this);

        exitBtn.setOnLongClickListener(this);
        userBtn.setOnLongClickListener(this);
        listBtn.setOnLongClickListener(this);
        searchBtn.setOnLongClickListener(this);

        //setId(R.id.menu_main);

    }
    @Override
    public void onClick(View v) {
        if (R.id.exit_btn == v.getId()) {
            getActivity().showDialog(v);
        } else {
            getActivity().showLayoutByView(v);
        }
        //((BaseView) v).setAsClickedStyle();
    }

    @Override
    public boolean onLongClick(View v) {
        getActivity().showOperationMenu(v);
        return true;
    }
}
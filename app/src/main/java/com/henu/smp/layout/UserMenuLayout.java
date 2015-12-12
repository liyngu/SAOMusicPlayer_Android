package com.henu.smp.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.henu.smp.R;
import com.henu.smp.base.BaseContainer;
import com.henu.smp.widget.base.BaseButton;

/**
 * Created by liyngu on 10/31/15.
 */
public class UserMenuLayout extends BaseContainer implements View.OnClickListener {

    public UserMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs, R.layout.menu_user);
        BaseButton userInfoBtn = (BaseButton) findViewById(R.id.user_info_btn);
        userInfoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        getActivity().showLayoutByView(v);
    }
}

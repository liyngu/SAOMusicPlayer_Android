package com.henu.smp.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.henu.smp.R;
import com.henu.smp.base.BaseMenu;

/**
 * Created by leen on 10/31/15.
 */
public class SearchMenuLayout extends BaseMenu implements View.OnClickListener {

    public SearchMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs, R.layout.menu_search);
    }

    @Override
    public void onClick(View v) {

    }
}

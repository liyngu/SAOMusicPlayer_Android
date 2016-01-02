package com.henu.smp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.henu.smp.R;
import com.henu.smp.base.BaseMenu;

/**
 * Created by liyngu on 12/21/15.
 */
public class EmptyMenu extends BaseMenu {

    public EmptyMenu(Context context, AttributeSet attrs) {
        super(context, attrs, R.layout.menu_empty);
    }
    public EmptyMenu(Context context) {
        super(context, R.layout.menu_empty);
    }

}

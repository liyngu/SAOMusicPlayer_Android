package com.henu.smp.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.henu.smp.R;
import com.henu.smp.base.BaseMenu;

/**
 * Created by liyngu on 12/12/15.
 */
public class OperationMenuLayout extends BaseMenu {
    private View clickedView;
    private ViewGroup parentPanel;

    public OperationMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs, R.layout.menu_operation);
    }

    @Override
    public void show() {
        parentPanel.setVisibility(View.VISIBLE);
        super.show();
    }

    @Override
    public void hidden() {
        parentPanel.setVisibility(View.GONE);
        super.hidden();
    }

    public void resetLocation() {
        if (clickedView != null) {
            setLocationByView(clickedView);
        }
    }

    public void setParentPanel(ViewGroup parentPanel) {
        this.parentPanel = parentPanel;
    }

    public void setClickedView(View clickedView) {
        this.clickedView = clickedView;
    }

}

package com.henu.smp.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.henu.smp.R;
import com.henu.smp.base.BaseContainer;
import com.henu.smp.widget.base.BaseButton;

/**
 * Created by liyngu on 12/12/15.
 */
public class OperationMenuLayout extends BaseContainer {
    private View parentView;

    public OperationMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs, R.layout.menu_operation);
    }

    public void setParentView(View parentView) {
        this.parentView = parentView;
    }

    public View getParentView() {
        return parentView;
    }
}

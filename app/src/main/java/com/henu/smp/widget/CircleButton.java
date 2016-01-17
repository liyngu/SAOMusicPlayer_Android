package com.henu.smp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.henu.smp.base.BaseButton;

/**
 * Created by leen on 10/14/15.
 */
public class CircleButton extends BaseButton {
    private LinearLayout.LayoutParams params = null;
    public CircleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        params = new LinearLayout.LayoutParams(context, attrs);
    }

    public LinearLayout.LayoutParams getParams() {
        return params;
    }
}

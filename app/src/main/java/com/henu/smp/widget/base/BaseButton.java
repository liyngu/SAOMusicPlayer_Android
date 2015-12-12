package com.henu.smp.widget.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.Toast;

import com.henu.smp.R;
import com.henu.smp.base.SmpWidget;

/**
 * Created by leen on 10/14/15.
 */
public abstract class BaseButton extends ImageButton implements SmpWidget {
    protected final String LOG_TAG = this.getClass().getSimpleName();
    private int childLayoutId;

    public BaseButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.smp);
        childLayoutId = typedArray.getResourceId(R.styleable.smp_child_layout, -1);
        if(childLayoutId == -1) {
            return;
        }
    }

    public int getChildLayoutId() {
        return childLayoutId;
    }
}

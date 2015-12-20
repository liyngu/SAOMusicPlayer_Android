package com.henu.smp.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageButton;

import com.henu.smp.R;
import com.henu.smp.model.Menu;
import com.henu.smp.model.SmpMenuWidget;
import com.henu.smp.model.SmpWidget;

/**
 * Created by leen on 10/14/15.
 */
public abstract class BaseButton extends ImageButton implements SmpMenuWidget {
    protected final String LOG_TAG = this.getClass().getSimpleName();
    private Menu data = new Menu();
    private int childLayoutId;
    private String text;
    private String name;
    private int index;

    public BaseButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.smp);
        childLayoutId = typedArray.getResourceId(R.styleable.smp_child_layout, -1);
        if(childLayoutId == -1) {
            return;
        }
        this.text = "列表";
        this.name = "list";
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public Menu getData() {
        return data;
    }
    public int getChildLayoutId() {
        return childLayoutId;
    }
}

package com.henu.smp.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import com.henu.smp.Constants;
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
    private int childMenuId;

    public BaseButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.smp);
        this.childMenuId = typedArray.getResourceId(R.styleable.smp_child_menu_id, Constants.EMPTY_MENU_ID);
        String text = typedArray.getString(R.styleable.smp_text);
        typedArray.recycle();

        this.data.setText(text);
        if (this.getText() == null || this.getText().equals("")) {
            this.setText("列表");
        }
        this.setName("list");
    }

    public void setName(String name) {
        this.data.setName(name);
    }

    public void setIndex(int index) {
        this.data.setIndex(index);
    }

    public void setText(String text) {
        this.data.setText(text);
        this.invalidate();
    }

    public int getIndex() {
        return data.getIndex();
    }

    public String getName() {
        return data.getName();
    }

    public String getText() {
        return data.getText();
    }

    public Menu getData() {
        return data;
    }

    public int getChildMenuId() {
        return childMenuId;
    }
}

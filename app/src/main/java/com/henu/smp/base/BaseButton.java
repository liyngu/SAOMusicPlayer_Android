package com.henu.smp.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageButton;

import com.henu.smp.Constants;
import com.henu.smp.R;
import com.henu.smp.entity.Menu;
import com.henu.smp.widget.SmpWidget;
import com.henu.smp.util.StringUtil;

/**
 * Created by leen on 10/14/15.
 */
public abstract class BaseButton extends ImageButton implements SmpWidget {
    protected final String LOG_TAG = this.getClass().getSimpleName();
    private int index;
    private int type;
    private int id;
    private String name;
    private String text;
    private int childMenuId;
    private String dialogClassName;
    private String dialogParams;

    public BaseButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.smp);
        this.childMenuId = typedArray.getResourceId(R.styleable.smp_child_menu_id, Constants.EMPTY_MENU_ID);
        String text = typedArray.getString(R.styleable.smp_text);
        this.text = StringUtil.format(text);
        String name = typedArray.getString(R.styleable.smp_name);
        this.name = StringUtil.format(name);
        String dialogClassName = typedArray.getString(R.styleable.smp_dialog_activity);
        this.dialogClassName = StringUtil.format(dialogClassName);
        String dialogParams = typedArray.getString(R.styleable.smp_dialog_params);
        this.dialogParams = StringUtil.format(dialogParams);
        typedArray.recycle();

        if (StringUtil.isEmpty(text)) {
            this.text = "列表";
        }
    }

    public void setDialogClassName(String dialogClassName) {
        this.dialogClassName = dialogClassName;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setDialogParams(String dialogParams) {
        this.dialogParams = dialogParams;
    }

    public String getDialogParams() {
        return dialogParams;
    }

    public String getDialogClassName() {
        return dialogClassName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setText(String text) {
        this.text = text;
        this.invalidate();
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

    public int getMenuId() {
        return id;
    }

    public Menu getData() {
        Menu menu = new Menu();
        menu.setName(name);
        menu.setText(text);
        menu.setIndex(index);
        menu.setType(type);
        menu.setId(id);
        return menu;
    }

    public void setData(Menu menu) {
        this.name = menu.getName();
        this.text = menu.getText();
        this.index = menu.getIndex();
        this.type = menu.getType();
        this.id = menu.getId();
    }

    public int getChildMenuId() {
        return childMenuId;
    }
}

package com.henu.smp.widget;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.henu.smp.R;
import com.henu.smp.model.SmpWidget;
import com.henu.smp.util.WidgetUtil;

/**
 * Created by liyngu on 12/12/15.
 */
public class MessagePanel extends RelativeLayout implements SmpWidget {

    public MessagePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获得inflater 对象
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //通过resource在container中填充组件
        inflater.inflate(R.layout.message_panel, this);
        setVisibility(View.INVISIBLE);
    }

    public void setLocationByView(View v) {
        // 获得v的起始坐标点
        Point vp = WidgetUtil.getViewPoint(v);
        // 设置x坐标
        int x = vp.x;
        // 设置y坐标
        int y = vp.y + v.getHeight() / 2;
        // 设置菜单坐标
        this.setLocationByIndicator(x, y);

    }
    public void setLocationByIndicator(int x, int y) {
        int width = getWidth();
        if (width == 0) {
            measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            // 重新获得高度
            width = getMeasuredWidth();
        }
        setLocation(x - width, y - 300);
    }

    public void setLocation(int x, int y) {
        setX(x);
        setY(y);
    }

    public void show() {
        setVisibility(View.VISIBLE);
        invalidate();
    }

    public void hidden() {
        setVisibility(View.GONE);
    }
}

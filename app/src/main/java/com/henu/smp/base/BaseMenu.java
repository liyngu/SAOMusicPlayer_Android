package com.henu.smp.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.NinePatch;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.henu.smp.activity.MainActivity;
import com.henu.smp.R;
import com.henu.smp.layout.MainMenuLayout;
import com.henu.smp.model.SmpMenuWidget;
import com.henu.smp.util.WidgetUtil;

/**
 * Created by liyngu on 10/14/15.
 */
public abstract class BaseMenu extends ScrollView implements SmpMenuWidget,
        View.OnClickListener, View.OnLongClickListener {
    protected final String LOG_TAG = this.getClass().getSimpleName();
    private static final int MAX_NUM = 6;
    private MainActivity activity;
    private LinearLayout layout;
    /**
     * 指针的图片
     */
    private NinePatch indicator;


    public BaseMenu(Context context, int resource) {
        this(context, null, resource);
    }

    public BaseMenu(Context context, AttributeSet attrs, int resource) {
        super(context, attrs);
        //获得inflater 对象
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //通过resource在container中填充组件
        inflater.inflate(resource, this);
        //隐藏滚动条
        setVerticalScrollBarEnabled(false);
        this.layout = (LinearLayout) findViewById(R.id.content_layout);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_indicator);
        indicator = new NinePatch(bmp, bmp.getNinePatchChunk(), null);
        //设置为默认不显示
        setVisibility(View.INVISIBLE);
        //setBackgroundColor(Color.YELLOW);
    }

    public void setLocationByView(View v) {
        // 获得v的起始坐标点
        Point vp = WidgetUtil.getViewPoint(v);
        int childCount = this.getLayoutChildCount();
        int itemHeight = 0;
        if (childCount > 0) {
            itemHeight = this.getLayoutChildAt(0).getHeight();
        }

        int height = getHeight();
        if (height < itemHeight * childCount) {
            measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            // 重新获得高度
            height = getMeasuredHeight();
        }

        // 设置x坐标
        int x = (int) (vp.x + v.getWidth());// + dimens.CHILD_BUTTON_MARGIN_LEFT);
        // 设置y坐标
        int y = vp.y + v.getHeight() / 2 - height / 2;
        // 设置菜单坐标
        this.setLocation(x, y);
    }

    public void resetStyle() {
        for (int i = 0; i < ((ViewGroup) getChildAt(0)).getChildCount(); i++) {
            View item = ((ViewGroup) getChildAt(0)).getChildAt(i);
            item.setEnabled(true);
            item.setSelected(false);
        }
    }

    public boolean isShowed() {
        return getVisibility() == View.VISIBLE;
    }

    public void setActivity(MainActivity activity) {
        this.activity = activity;
    }

    public View getLayoutChildAt(int index) {
        return layout.getChildAt(index);
    }

    public int getLayoutChildCount() {
        return layout.getChildCount();
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

    protected MainActivity getActivity() {
        return activity;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = this.getLayoutChildCount();
        if (childCount == 0) {
            return;
        }
        View childItem = getLayoutChildAt(0);
        int count = MAX_NUM < childCount ? MAX_NUM : childCount;
        // 设置此子菜单大小
        int width = childItem.getWidth();
        int height = childItem.getHeight();
        if (width == 0 || height == 0) {
            childItem.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        }
        setMeasuredDimension(childItem.getWidth(), childItem.getHeight() * count);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!(this instanceof MainMenuLayout)) {
            RectF location = new RectF(0, getScrollY(), indicator.getWidth(), getHeight() + getScrollY());
            indicator.draw(canvas, location);
        }
        invalidate();
    }

    @Override
    public void addView(View child) {
        if (getChildCount() > 0) {
            this.layout.addView(child);
            if (child instanceof BaseButton) {
                BaseButton btn = (BaseButton) child;
                btn.setOnClickListener(this);
                btn.setOnLongClickListener(this);
            }
        } else {
            super.addView(child);
        }
    }

    @Override
    public void onClick(View v) {
        this.activity.showLayoutByView(v);
    }

    @Override
    public boolean onLongClick(View v) {
        this.activity.showOperationMenu(v);
        return true;
    }
}

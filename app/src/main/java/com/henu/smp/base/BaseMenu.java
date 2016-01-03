package com.henu.smp.base;

import android.content.Context;
import android.content.res.TypedArray;
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

import com.henu.smp.R;
import com.henu.smp.activity.MenuTreeActivity;
import com.henu.smp.entity.SmpMenuWidget;
import com.henu.smp.util.WidgetUtil;

/**
 * Created by liyngu on 10/14/15.
 */
public abstract class BaseMenu extends ScrollView implements SmpMenuWidget, View.OnClickListener,
        View.OnLongClickListener {
    protected final String LOG_TAG = this.getClass().getSimpleName();
    private static final int MAX_NUM = 6;
    private MenuTreeActivity mActivity;
    private LinearLayout mLayout;
    /**
     * 指针的图片
     */
    private NinePatch mIndicatorImg;
    private boolean isShowIndicator;

    public void setIndicatorVisibility(boolean isShowIndicator) {
        this.isShowIndicator = isShowIndicator;
    }

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
        mLayout = (LinearLayout) findViewById(R.id.content_layout);

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_indicator);
        mIndicatorImg = new NinePatch(bmp, bmp.getNinePatchChunk(), null);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.smp);
        isShowIndicator = typedArray.getBoolean(R.styleable.smp_show_indicator, true);
        typedArray.recycle();
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

    public void setActivity(MenuTreeActivity activity) {
        mActivity = activity;
    }

    public View getLayoutChildAt(int index) {
        return mLayout.getChildAt(index);
    }

    public int getLayoutChildCount() {
        return mLayout.getChildCount();
    }

    public void setLocation(float x, float y) {
        setX(x);
        setY(y);
    }

    public void setLocation(int x, int y) {
        this.setLocation((float) x, (float) y);
    }

    public void show() {
        setVisibility(View.VISIBLE);
        invalidate();
    }

    public void hidden() {
        setVisibility(View.GONE);
    }

    protected MenuTreeActivity getActivity() {
        return mActivity;
    }

    private void addViewToLayout(View view) {
        this.mLayout.addView(view);
        if (view instanceof BaseButton) {
            BaseButton btn = (BaseButton) view;
            btn.setOnClickListener(this);
            btn.setOnLongClickListener(this);
        }
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
        if (isShowIndicator) {
            RectF location = new RectF(0, getScrollY(), mIndicatorImg.getWidth(), getHeight() + getScrollY());
            mIndicatorImg.draw(canvas, location);
        }
        invalidate();
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (getChildCount() > 0) {
            this.addViewToLayout(child);
        } else {
            super.addView(child, params);
        }
    }

    @Override
    public void addView(View child) {
        if (getChildCount() > 0) {
            this.addViewToLayout(child);
        } else {
            super.addView(child);
        }
    }

    /**
     * 每一个菜单按钮都会调用此方法
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        mActivity.showMenuByView(v);
    }

    /**
     * 每一个菜单按钮都会调用此方法
     *
     * @param v
     */
    @Override
    public boolean onLongClick(View v) {
        mActivity.showOperationMenu(v);
        return true;
    }

}

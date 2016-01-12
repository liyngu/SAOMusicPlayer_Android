package com.henu.smp.widget;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.henu.smp.Constants;
import com.henu.smp.R;
import com.henu.smp.activity.MenuTreeActivity;
import com.henu.smp.activity.MusicControlActivity;
import com.henu.smp.util.WidgetUtil;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by liyngu on 12/12/15.
 */
public class MessagePanel extends RelativeLayout implements SmpWidget {
    private MenuTreeActivity mActivity;

    public void setActivity(MenuTreeActivity Activity) {
        mActivity = Activity;
    }

    private TextView titleTxt;

    @ViewInject(R.id.info_image)
    private ImageView infoImage;

    @Event(R.id.info_image)
    private void showMusicControllerEvent(View v) {
        int x = (int) v.getX();
        int y = (int) v.getY();
        String params = String.valueOf(x) + Constants.CONNECTOR + String.valueOf(y);
        mActivity.showDialog(MusicControlActivity.class, params);
    }

    public MessagePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获得inflater 对象
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //通过resource在container中填充组件
        inflater.inflate(R.layout.message_panel, this);
        x.view().inject(this);

        titleTxt = (TextView) findViewById(R.id.title_txt);
        setVisibility(View.INVISIBLE);
    }

    public void setTitle(String title) {
        this.titleTxt.setText(title);
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

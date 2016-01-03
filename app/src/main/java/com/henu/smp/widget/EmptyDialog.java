package com.henu.smp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.henu.smp.R;
import com.henu.smp.entity.SmpWidget;

/**
 * Created by liyngu on 12/23/15.
 */
public class EmptyDialog extends FrameLayout implements SmpWidget {

    public EmptyDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获得inflater 对象
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //通过resource在container中填充组件
        inflater.inflate(R.layout.dialog_empty, this);
    }
}

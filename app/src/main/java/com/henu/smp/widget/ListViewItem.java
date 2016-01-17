package com.henu.smp.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.henu.smp.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by liyngu on 1/4/16.
 */
public class ListViewItem extends LinearLayout implements SmpWidget {
//    @ViewInject(R.id.item_title)
//    private TextView mItemTitle;
//
//    @ViewInject(R.id.item_layout)
//    private LinearLayout mItemLayout;

    public ListViewItem(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //通过resource在container中填充组件
        inflater.inflate(R.layout.auto_search_list_item, this);
        x.view().inject(this);
    }
}

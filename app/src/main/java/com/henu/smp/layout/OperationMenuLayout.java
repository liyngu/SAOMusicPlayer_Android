package com.henu.smp.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.henu.smp.Constants;
import com.henu.smp.R;
import com.henu.smp.base.BaseButton;
import com.henu.smp.base.BaseMenu;
import com.henu.smp.widget.RectButton;

/**
 * Created by liyngu on 12/12/15.
 */
public class OperationMenuLayout extends BaseMenu {
    private View clickedView;
    private ViewGroup parentPanel;
    private Button addBtn;
    private Button delBtn;

    public OperationMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs, R.layout.menu_operation);
        addBtn = (Button) findViewById(R.id.add_btn);
        delBtn = (Button) findViewById(R.id.del_btn);
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getActivity().menuOperation(clickedView, Constants.MENU_OPERATION_ADD);
            }
        });
        delBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ViewGroup viewGroup = (ViewGroup) clickedView.getParent();
                viewGroup.removeView(clickedView);
                hidden();
            }
        });
    }

    @Override
    public void show() {
        parentPanel.setVisibility(View.VISIBLE);
        super.show();
    }

    @Override
    public void hidden() {
        parentPanel.setVisibility(View.GONE);
        super.hidden();
    }

    public void resetLocation() {
        if (clickedView != null) {
            setLocationByView(clickedView);
        }
    }

    public void setParentPanel(ViewGroup parentPanel) {
        this.parentPanel = parentPanel;
    }

    public void setClickedView(View clickedView) {
        this.clickedView = clickedView;
    }

}

package com.henu.smp.widget;

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
public class OperationMenu extends BaseMenu {
    private BaseButton mClickedBtn;
    private ViewGroup mParentPanel;
    private Button addBtn;
    private Button delBtn;

    public OperationMenu(Context context, AttributeSet attrs) {
        super(context, attrs, R.layout.menu_operation);
        addBtn = (Button) findViewById(R.id.add_btn);
        delBtn = (Button) findViewById(R.id.del_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().menuOperation(mClickedBtn, Constants.MENU_OPERATION_ADD);
            }
        });
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup viewGroup = (ViewGroup) mClickedBtn.getParent();
                viewGroup.removeView(mClickedBtn);
                hidden();
            }
        });
    }

    @Override
    public void show() {
        mParentPanel.setVisibility(View.VISIBLE);
        super.show();
    }

    @Override
    public void hidden() {
        mParentPanel.setVisibility(View.GONE);
        super.hidden();
    }

    public void resetLocation() {
        if (mClickedBtn != null) {
            setLocationByView(mClickedBtn);
        }
    }

    public void setClickedView(BaseButton clickedBtn) {
        this.mClickedBtn = clickedBtn;
    }

    public void setParentPanel(ViewGroup ParentPanel) {
        mParentPanel = ParentPanel;
    }
}

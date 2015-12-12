package com.henu.smp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.henu.smp.activity.AlertActivity;
import com.henu.smp.base.BaseActivity;
import com.henu.smp.base.BaseContainer;
import com.henu.smp.base.SmpWidget;
import com.henu.smp.layout.MainMenuLayout;
import com.henu.smp.listener.ScreenListener;
import com.henu.smp.model.SmpForest;
import com.henu.smp.widget.base.BaseButton;

import java.util.List;

public class MainActivity extends BaseActivity {
    private RelativeLayout mainPage;
    private LinearLayout operationMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mainPage = (RelativeLayout) findViewById(R.id.main_page);
        // 设置屏幕事件监听
        ScreenListener listener = new ScreenListener(this);
        mainPage.setOnTouchListener(listener);
        mainPage.setLongClickable(true);
        operationMenu = (LinearLayout) findViewById(R.id.operation_menu);
        operationMenu.setOnTouchListener(listener);
        operationMenu.setLongClickable(true);
        initWidgetForest();
    }

    public void showDialog(View v) {
        Intent intent = new Intent();
        intent.setClass(this, AlertActivity.class);
        startActivity(intent);
    }

    private void initWidgetForest() {
        SmpForest<SmpWidget> widgetForest = new SmpForest<>();
        BaseContainer container = (BaseContainer) findViewById(R.id.menu_main);
        widgetForest.setRoot(container);
        setWidgetForest(widgetForest);
        this.findChildContainer(container);
    }

    private void findChildContainer(BaseContainer container) {
        int containerChildCount = container.getLayoutChildCount();
        for (int i = 0; i < containerChildCount; i++) {
            BaseButton childBtn = (BaseButton) container.getLayoutChildAt(i);
            getWidgetForest().addChild(container, childBtn);
            View childView = findViewById(childBtn.getChildLayoutId());
            if (childView != null && childView instanceof BaseContainer) {
                BaseContainer childLayout = (BaseContainer) childView;
                getWidgetForest().addChild(childBtn, childLayout);
                findChildContainer(childLayout);
            }
        }
        container.setActivity(this);
    }

    public void startMenu(int x, int y) {
        MainMenuLayout mainMenu = (MainMenuLayout) getWidgetForest().getRoot();
        if (mainMenu.getVisibility() == View.VISIBLE) {
            closeMenu(mainMenu);
            mainMenu.hidden();
        }
        getWidgetForest().setFocus(mainMenu);
        mainMenu.setLocation(x, y);
        mainMenu.show();
    }

    public void moveMenu(int x, int y) {
        MainMenuLayout mainMenu = (MainMenuLayout) getWidgetForest().getRoot();
        mainMenu.setX(mainMenu.getX() - x);
        mainMenu.setY(mainMenu.getY());
        List<BaseContainer> containers = getWidgetForest().getChildsByClass(mainMenu, BaseContainer.class);
        for (BaseContainer container: containers) {
            if (container.getVisibility() == View.VISIBLE) {
                container.setLocationByView((View) getWidgetForest().getParent(container));
            }
        }
    }



    public void showOperationMenu(View v) {
        operationMenu.setVisibility(View.VISIBLE);
        BaseContainer view = (BaseContainer) operationMenu.getChildAt(0);
        view.setLocationByView(v);
    }

//    public void closeByContainer(BaseContainer root) {
//
//    }

    public void undoOperation() {
        if (operationMenu.getVisibility() == View.VISIBLE) {
            operationMenu.setVisibility(View.GONE);
            return;
        }
        BaseContainer container = (BaseContainer) getWidgetForest().getFocus();
        if (container != null) {
            container.hidden();
            getWidgetForest().rollbackFocus();
            container = (BaseContainer) getWidgetForest().getFocus();
            if (container != null) {
                container.resetStyle();
            }
        }
    }

    /**
     * 按钮被点击后所进行的操作，打开子菜单以及关闭原来的菜单
     * @param v
     */
    public void showLayoutByView(View v) {
        if (v instanceof BaseButton) {
            BaseButton btn = (BaseButton) v;
            closeMenu(btn);
            SmpWidget child = menuForest.getChild(btn);
            if (child != null) {
                openChildMenu(btn);
            }
        }
    }

    /**
     * 当按钮被点击后触发的菜单打开以及按钮样式设定的操作
     * 此方法直接适用于按钮点击后的操作
     * @param btn
     */
    public void openChildMenu(BaseButton btn) {
        //设置所有按钮的样式
        List<BaseButton> buttons = menuForest.getSiblingsByClass(btn, BaseButton.class);
        for (BaseButton sibling : buttons) {
            sibling.setSelected(true);
            sibling.setEnabled(true);
        }
        btn.setEnabled(false);
        //打开子菜单并设置位置
        BaseContainer childMenu = (BaseContainer) menuForest.getChild(btn);
        childMenu.setLocationByView(btn);
        openChildMenu(childMenu);
    }

    /**
     * 当按钮被点击后所进行的菜单打开操作
     * 包括disabled的样式设定与selected的样式设定，以及被打开的菜单样式的初始化
     * 并且设定菜单打开后为获得焦点状态，但不包括其余多余菜单的关闭工作
     * @param menu
     */
    public void openChildMenu(BaseContainer menu) {
        menuForest.setFocus(menu);
        menu.resetStyle();
        menu.show();
    }

    /**
     * 通过一个点击的按钮来关闭其下所有打开的菜单，并重置菜单的style
     * 此方法直接适用于按钮点击后触发的事件
     * @param btn
     */
    public void closeMenu(BaseButton btn) {
        BaseContainer parentMenu = (BaseContainer) menuForest.getParent(btn);
        closeMenu(parentMenu);
    }
}

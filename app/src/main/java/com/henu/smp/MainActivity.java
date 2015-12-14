package com.henu.smp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.henu.smp.activity.AlertActivity;
import com.henu.smp.base.BaseActivity;
import com.henu.smp.base.BaseContainer;
import com.henu.smp.model.SmpMenuWidget;
import com.henu.smp.model.SmpWidget;
import com.henu.smp.layout.MainMenuLayout;
import com.henu.smp.layout.OperationMenuLayout;
import com.henu.smp.listener.ScreenListener;
import com.henu.smp.model.SmpForest;
import com.henu.smp.widget.base.BaseButton;

import java.util.List;

public class MainActivity extends BaseActivity {
    private RelativeLayout mainPage;
    private OperationMenuLayout operationMenu;

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

        operationMenu = (OperationMenuLayout) findViewById(R.id.operation_menu);
        LinearLayout operationPanel = (LinearLayout) findViewById(R.id.operation_panel);
        operationPanel.setOnTouchListener(listener);
        operationPanel.setLongClickable(true);
        operationMenu.setParentPanel(operationPanel);
        initWidgetForest();
    }

    public void showDialog(View v) {
        Intent intent = new Intent();
        intent.setClass(this, AlertActivity.class);
        startActivity(intent);
    }

    private void initWidgetForest() {
        SmpForest<SmpMenuWidget> widgetForest = new SmpForest<>();
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
        //如果并不是操作状态，则只能横向移动
        if (!operationMenu.isShowed()) {
            y = 0;
        }
        //获得主菜单位置并设置其移动后的坐标
        MainMenuLayout mainMenu = (MainMenuLayout) getWidgetForest().getRoot();
        mainMenu.setX(mainMenu.getX() - x);
        mainMenu.setY(mainMenu.getY() - y);
        //移动所有显示的子菜单的位置
        List<BaseContainer> containers = getWidgetForest().getChildsByClass(mainMenu, BaseContainer.class);
        for (BaseContainer container: containers) {
            if (container.isShowed()) {
                container.setLocationByView((View) menuForest.getParent(container));
            }
        }
        if (operationMenu.isShowed()) {
            //移动操作菜单
            operationMenu.resetLocation();
        }
    }


    /**
     * 当菜单启动后调用的取消操作
     * 即如果有菜单处于启动状态，那么点击出来有按钮的位置，都将调用此方法
     * 判断的内容包括：操作菜单，主菜单，音乐菜单（暂无）
     */
    public void undoOperation() {
        if (operationMenu.isShowed()) {
            operationMenu.hidden();
        } else {
            BaseContainer focusMenu = menuForest.getFocus();
            if (focusMenu != null) {
                focusMenu.hidden();
                menuForest.rollbackFocus();
            } else {
                return;
            }
        }
        BaseContainer container = menuForest.getFocus();
        if (container != null) {
            container.resetStyle();
        }
    }

    /**
     * 按钮长按后直接调用的方法
     * 具有显示操作菜单及关闭其余菜单的功能
     * @param v
     */
    public void showOperationMenu(View v) {
        //设置操作菜单的显示位置以及点击的按钮
        operationMenu.show();
        operationMenu.setClickedView(v);
        operationMenu.setLocationByView(v);
        //关闭多余的子菜单
        if (v instanceof BaseButton) {
            BaseButton btn = (BaseButton) v;
            closeMenu(btn);
            //设置所有按钮的样式与焦点
            setClickedButtonsStyle(btn);
            menuForest.setFocus(menuForest.getParent(btn));
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
        setClickedButtonsStyle(btn);
        //打开子菜单并设置位置
        BaseContainer childMenu = (BaseContainer) menuForest.getChild(btn);
        childMenu.setLocationByView(btn);
        openChildMenu(childMenu);
    }

    /**
     * 设置按钮被点击过后父容器及其自身的按钮样式
     * @param btn
     */
    public void setClickedButtonsStyle(BaseButton btn) {
        List<BaseButton> buttons = menuForest.getSiblingsByClass(btn, BaseButton.class);
        for (BaseButton sibling : buttons) {
            sibling.setSelected(true);
            sibling.setEnabled(true);
        }
        btn.setEnabled(false);
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

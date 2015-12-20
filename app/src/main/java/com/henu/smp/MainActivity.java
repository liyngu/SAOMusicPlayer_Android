package com.henu.smp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.henu.smp.activity.AlertActivity;
import com.henu.smp.base.BaseActivity;
import com.henu.smp.base.BaseButton;
import com.henu.smp.base.BaseMenu;
import com.henu.smp.layout.OperationMenuLayout;
import com.henu.smp.listener.ScreenListener;
import com.henu.smp.model.SmpWidget;
import com.henu.smp.model.User;
import com.henu.smp.widget.MenuTree;

import org.json.JSONArray;

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

        if(user == null) {
            user = new User();
            user.setId(10001);
            user.setUsername("test");
            user.setPassword("123");
            MenuTree menuTree = this.menuTree;
            user.setMenus(menuTree.parseData());
            userService.create(user);
        }
    }

    public void showDialog(View v) {
        Intent intent = new Intent();
        intent.setClass(this, AlertActivity.class);
        startActivity(intent);
//        BaseMenu mainMenu = menuTree.getRoot();
//        List<BaseButton> btns = menuTree.getChilds(mainMenu);
//        for(BaseButton btn : btns) {
//            btn.setIndex(10);
//            btn.setText("hhhhhhhh");
//        }
//        userService.create(user);
//        MenuTree menuTree = this.menuTree;
//        user.setMenus(menuTree.parseData());
//        userService.create(user);
    }

    private void initWidgetForest() {
        BaseMenu mainMenu = (BaseMenu) findViewById(R.id.menu_main);
        menuTree.setRoot(mainMenu);
        this.findChildMenu(mainMenu);
    }

    private void findChildMenu(BaseMenu menu) {
        int childCount = menu.getLayoutChildCount();
        for (int i = 0; i < childCount; i++) {
            BaseButton btn = (BaseButton) menu.getLayoutChildAt(i);
            btn.setIndex(i);

            menuTree.addChild(menu, btn);
            View childView = findViewById(btn.getChildLayoutId());
            if (childView != null && childView instanceof BaseMenu) {
                BaseMenu childMenu = (BaseMenu) childView;
                menuTree.addChild(btn, childMenu);
                findChildMenu(childMenu);
            }
        }
        menu.setActivity(this);
    }

    public void startMenu(int x, int y) {
        BaseMenu mainMenu = menuTree.getRoot();
        if (mainMenu.isShowed()) {
            closeMenu(mainMenu);
            mainMenu.hidden();
        }
        menuTree.setFocus(mainMenu);
        mainMenu.setLocation(x, y);
        mainMenu.show();
    }

    public void moveMenu(int x, int y) {
        //如果并不是操作状态，则只能横向移动
        if (!operationMenu.isShowed()) {
            y = 0;
        }
        //获得主菜单位置并设置其移动后的坐标
        BaseMenu mainMenu = menuTree.getRoot();
        mainMenu.setX(mainMenu.getX() - x);
        mainMenu.setY(mainMenu.getY() - y);
        //移动所有显示的子菜单的位置
        List<BaseMenu> containers = menuTree.getChildsByClass(mainMenu, BaseMenu.class);
        for (BaseMenu container: containers) {
            if (container.isShowed()) {
                container.setLocationByView((View) menuTree.getParent(container));
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
            BaseMenu focusMenu = menuTree.getFocus();
            if (focusMenu != null) {
                focusMenu.hidden();
                menuTree.rollbackFocus();
            } else {
                return;
            }
        }
        BaseMenu container = menuTree.getFocus();
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
            menuTree.setFocus(menuTree.getParent(btn));
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
            SmpWidget child = menuTree.getChild(btn);
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
        BaseMenu childMenu = (BaseMenu) menuTree.getChild(btn);
        childMenu.setLocationByView(btn);
        openChildMenu(childMenu);
    }

    /**
     * 设置按钮被点击过后父容器及其自身的按钮样式
     * @param btn
     */
    public void setClickedButtonsStyle(BaseButton btn) {
        List<BaseButton> buttons = menuTree.getSiblingsByClass(btn, BaseButton.class);
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
    public void openChildMenu(BaseMenu menu) {
        menuTree.setFocus(menu);
        menu.resetStyle();
        menu.show();
    }

    /**
     * 通过一个点击的按钮来关闭其下所有打开的菜单，并重置菜单的style
     * 此方法直接适用于按钮点击后触发的事件
     * @param btn
     */
    public void closeMenu(BaseButton btn) {
        BaseMenu parentMenu = (BaseMenu) menuTree.getParent(btn);
        closeMenu(parentMenu);
    }
}

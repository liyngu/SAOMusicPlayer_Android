package com.henu.smp.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.henu.smp.Constants;
import com.henu.smp.R;
import com.henu.smp.base.BaseActivity;
import com.henu.smp.base.BaseButton;
import com.henu.smp.base.BaseMenu;
import com.henu.smp.dto.MenuTree;
import com.henu.smp.listener.SimpleScreenListener;
import com.henu.smp.util.StringUtil;
import com.henu.smp.widget.EmptyMenu;
import com.henu.smp.widget.MessagePanel;
import com.henu.smp.widget.OperationMenu;
import com.henu.smp.widget.RectButton;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by liyngu on 12/30/15.
 */
public class MenuTreeActivity extends BaseActivity {
    private MenuTree mMenuTree;

    @ViewInject(R.id.background)
    private FrameLayout background;

    @ViewInject(R.id.operation_menu)
    private OperationMenu operationMenu;

    @ViewInject(R.id.operation_panel)
    private ViewGroup operationPanel;

    @ViewInject(R.id.message_panel)
    private MessagePanel messagePanel;

    @ViewInject(R.id.main_menu)
    private BaseMenu mainMenu;

    private SimpleScreenListener screenListener = new SimpleScreenListener() {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            MenuTreeActivity.this.moveMenu((int) distanceX, (int) distanceY);
            return true;
        }

        @Override
        public boolean onClick() {
            MenuTreeActivity.this.undoOperation();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_tree);
        ViewUtils.inject(this);

        this.initMenuTree();
        operationMenu.setParentPanel(operationPanel);
        screenListener.setContext(this);
        background.setOnTouchListener(screenListener);
        background.setLongClickable(true);
        operationPanel.setOnTouchListener(screenListener);
        operationPanel.setLongClickable(true);
        messagePanel.setActivity(this);

        Bundle bundle = getIntent().getExtras();
        int startPointX = bundle.getInt(Constants.CLICKED_POINT_X);
        int startPointY = bundle.getInt(Constants.CLICKED_POINT_Y);
        startMenu(startPointX, startPointY);

    }

    public void moveMenu(int x, int y) {
        MenuTree menuTree = mMenuTree;
        //获得主菜单位置并设置其移动后的坐标
        BaseMenu mainMenu = this.mainMenu;
        List<BaseMenu> menuList = menuTree.getAllChildren(mainMenu);
        menuList.add(mainMenu);
        //如果并不是操作状态，则只能横向移动
        if (!operationMenu.isShowed()) {
            y = 0;
        } else {
            menuList.add(operationMenu);
        }

        int menuCount = menuList.size();
        for (int i = 0; i < menuCount; i++) {
            BaseMenu menu = menuList.get(i);
            //移动所有显示的子菜单的位置
            if (menu.isShowed()) {
                float pointX = menu.getX() - x;
                float pointY = menu.getY() - y;
                menu.setLocation(pointX, pointY);
            }
        }
        if (operationMenu.isShowed()) {
            //移动操作菜单
            operationMenu.resetLocation();
        }
    }

    public void showMessagePanel(View v) {
        this.moveMessagePanelToView(v);
        messagePanel.show();
    }

    public void startMenu(int x, int y) {
        MenuTree menuTree = mMenuTree;
        BaseMenu mainMenu = menuTree.getRoot();
        if (mainMenu.isShowed()) {
            //closeMenu(mainMenu);
            mainMenu.hidden();
        }
        menuTree.setFocus(mainMenu);
        mainMenu.setLocation(x, y);
        mainMenu.show();
    }

    public void moveMessagePanelToView(View v) {
        messagePanel.setLocationByView(v);
    }

    public void menuOperation(View v, int operateState) {
        if (operateState == Constants.MENU_OPERATION_ADD) {
            MenuTree menuTree = mMenuTree;
            BaseButton btn = (BaseButton) v;
            BaseMenu baseMenu = menuTree.getChild(btn);
            if (baseMenu == null) {
                BaseMenu emptyMenu = new EmptyMenu(this);
                menuTree.addChild(btn, emptyMenu);
                background.addView(emptyMenu);
                // emptyMenu.setActivity(this);
                emptyMenu.show();
            } else {
                RectButton rb = new RectButton(this);
                baseMenu.addView(rb);
                menuTree.addChild(baseMenu, rb);
            }
        }
    }

    /**
     * 初始化菜单树，包括新建和设置菜单关系
     */
    private void initMenuTree() {
        BaseMenu mainMenu = this.mainMenu;
        mMenuTree = new MenuTree();
        mMenuTree.setRoot(mainMenu);
        this.findChildMenu(mainMenu);
    }

    /**
     * 通过一个菜单去递归添加所有的菜单关系
     *
     * @param menu
     */
    private void findChildMenu(BaseMenu menu) {
        MenuTree menuTree = mMenuTree;
        int childCount = menu.getLayoutChildCount();
        for (int i = 0; i < childCount; i++) {
            BaseButton btn = (BaseButton) menu.getLayoutChildAt(i);
            btn.setIndex(i);
            // 为这个菜单添加按钮孩子
            menuTree.addChild(menu, btn);
            // 获得每一个按钮的孩子
            int childMenuId = btn.getChildMenuId();
            if (childMenuId != Constants.EMPTY_MENU_ID) {
                BaseMenu childMenu = (BaseMenu) findViewById(childMenuId);
                // 为这个按钮添加菜单孩子
                menuTree.addChild(btn, childMenu);
                // 以孩子菜单为基础继续添加菜单关系
                this.findChildMenu(childMenu);
            }
        }
        // 为每一个获得到的菜单添加activity, 目的在于提供事件调用的activity
        menu.setActivity(this);
    }

    /**
     * 当菜单启动后调用的取消操作
     * 即如果有菜单处于启动状态，那么点击出来有按钮的位置，都将调用此方法
     * 判断的内容包括：操作菜单，主菜单
     */
    public void undoOperation() {
        MenuTree menuTree = mMenuTree;
        OperationMenu operationMenu = this.operationMenu;
        // 如果没有显示操作菜单，则向上回滚焦点
        if (!operationMenu.isShowed()) {
            BaseMenu focusMenu = menuTree.getFocus();
            if (focusMenu != null) {
                focusMenu.hidden();
                menuTree.rollbackFocus();
            }
        } else {
            // 否则取消操作菜单，因为操作菜单不存在焦点之中
            operationMenu.hidden();
        }
        BaseMenu currentFocusMenu = menuTree.getFocus();
        // 判断焦点是否为空，如果为空则证明没有菜单被打开，即结束activity
        if (currentFocusMenu != null) {
            currentFocusMenu.resetStyle();
        } else {
            MenuTreeActivity.this.finish();
        }

    }

    /**
     * 按钮长按后直接调用的方法
     * 具有显示操作菜单及关闭其余菜单的功能
     *
     * @param v
     */
    public void showOperationMenu(View v) {
        OperationMenu operationMenu = this.operationMenu;
        //设置操作菜单的显示位置以及点击的按钮
        operationMenu.show();
        //关闭多余的子菜单
        if (v instanceof BaseButton) {
            BaseButton btn = (BaseButton) v;
            operationMenu.setClickedView(btn);
            operationMenu.setLocationByView(btn);
            closeMenu(btn);
            //设置所有按钮的样式与焦点
            setClickedButtonsStyle(btn);
            MenuTree menuTree = mMenuTree;
            menuTree.setFocus(menuTree.getParent(btn));
        }
    }

    /**
     * 按钮被点击后所进行的操作，打开子菜单以及关闭原来的菜单
     *
     * @param v
     */
    public void showMenuByView(View v) {
        if (v instanceof BaseButton) {
            MenuTree menuTree = mMenuTree;
            BaseButton btn = (BaseButton) v;
            String dialogClassName = btn.getDialogClassName();
            // 如果这个按钮和一个菜单关联的话，则不进行显示菜单的操作
            if (!StringUtil.isEmpty(dialogClassName)) {
                showDialog(dialogClassName, btn.getDialogParams());
                return;
            }
            BaseMenu childMenu = menuTree.getChild(btn);
            if (childMenu == null) {
                this.showOperationMenu(btn);
            } else {
                closeMenu(btn);
                this.openChildMenu(btn);
                this.showMessagePanel(btn);
            }
        }
    }

    /**
     * 当按钮被点击后触发的菜单打开以及按钮样式设定的操作
     * 此方法直接适用于按钮点击后的操作
     *
     * @param btn
     */
    public void openChildMenu(BaseButton btn) {
        //设置所有按钮的样式
        setClickedButtonsStyle(btn);
        //打开子菜单并设置位置
        BaseMenu childMenu = mMenuTree.getChild(btn);
        childMenu.setLocationByView(btn);
        this.openChildMenu(childMenu);
    }

    /**
     * 设置按钮被点击过后父容器及其自身的按钮样式
     *
     * @param btn
     */
    public void setClickedButtonsStyle(BaseButton btn) {
        List<BaseButton> buttons = mMenuTree.getSiblings(btn);
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
     *
     * @param menu
     */
    public void openChildMenu(BaseMenu menu) {
        mMenuTree.setFocus(menu);
        menu.resetStyle();
        menu.show();
    }

    /**
     * 通过一个点击的按钮来关闭其下所有打开的菜单，并重置菜单的style
     * 此方法直接适用于按钮点击后触发的事件
     *
     * @param btn
     */
    public void closeMenu(BaseButton btn) {
        BaseMenu parentMenu = mMenuTree.getParent(btn);
        closeMenu(parentMenu);
    }

    /**
     * 关闭所有已经打开的菜单
     */
    public void closeAllMenu() {
        BaseMenu rootMenu = mMenuTree.getRoot();
        closeMenu(rootMenu);
        rootMenu.hidden();
        rootMenu.resetStyle();
    }

    /**
     * 通过一个Container关闭已经打开的菜单，并重置菜单的style
     * 关闭位于这个Container级别下的所有的菜单，不包括这个Container
     *
     * @param root 为 Container
     */
    public void closeMenu(BaseMenu root) {
        List<BaseMenu> menus = mMenuTree.getAllChildren(root);
        for (BaseMenu menu : menus) {
            if (isOpenedMenu(menu)) {
                menu.hidden();
            }
        }
    }

    /**
     * 判断这个菜单是否已经被打开
     *
     * @param menu
     * @return 如果被打开，返回true
     */
    public boolean isOpenedMenu(BaseMenu menu) {
        return menu.getVisibility() == View.VISIBLE;
    }

    @Override
    protected void onReceivedData(Bundle bundle, int operation) {
        if (operation == Constants.ACTION_PLAYED) {
            this.messagePanel.setTitle("暂停");
        } else if (operation == Constants.ACTION_PAUSED) {
            this.messagePanel.setTitle("开始");
        }
    }
}

package com.henu.smp.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.henu.smp.business.UserService;
import com.henu.smp.model.SmpForest;
import com.henu.smp.model.SmpMenuWidget;
import com.henu.smp.model.User;
import com.henu.smp.widget.MenuTree;

import java.util.List;

/**
 * Created by liyngu on 10/31/15.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected final String LOG_TAG = this.getClass().getSimpleName();
    protected MenuTree menuTree = MenuTree.getIntance();
    protected static UserService userService;
    protected static User user;
    static {
        userService = new UserService();
        //user = userService.getLocal();
    }

    /**
     * 关闭所有已经打开的菜单
     */
    public void closeAllMenu() {
        BaseMenu rootMenu = menuTree.getRoot();
        closeMenu(rootMenu);
        rootMenu.hidden();
        rootMenu.resetStyle();
    }

    /**
     * 通过一个Container关闭已经打开的菜单，并重置菜单的style
     * 关闭位于这个Container级别下的所有的菜单，不包括这个Container
     * @param root 为 Container
     */
    public void closeMenu(BaseMenu root) {
        List<BaseMenu> menus = menuTree.getChildsByClass(root, BaseMenu.class);
        for (BaseMenu menu : menus) {
            if (isOpenedMenu(menu)) {
                menu.hidden();
            }
        }
    }
    /**
     * 判断这个菜单是否已经被打开
     * @param menu
     * @return 如果被打开，返回true
     */
    public boolean isOpenedMenu(BaseMenu menu) {
        return menu.getVisibility() == View.VISIBLE;
    }
}

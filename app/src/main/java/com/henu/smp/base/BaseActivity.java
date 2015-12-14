package com.henu.smp.base;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.henu.smp.model.SmpForest;
import com.henu.smp.model.SmpMenu;
import com.henu.smp.model.SmpMenuWidget;
import com.henu.smp.model.SmpWidget;

import java.util.List;

/**
 * Created by liyngu on 10/31/15.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected final String LOG_TAG = this.getClass().getSimpleName();
    protected SmpForest<SmpMenuWidget> menuForest;

    protected void setWidgetForest(SmpForest<SmpMenuWidget> widgetForest) {
        this.menuForest = widgetForest;
    }

    public SmpForest<SmpMenuWidget> getWidgetForest() {
        return menuForest;
    }

    /**
     * 关闭所有已经打开的菜单
     */
    public void closeAllMenu() {
        BaseContainer rootContainer = (BaseContainer) menuForest.getRoot();
        closeMenu(rootContainer);
        rootContainer.hidden();
        rootContainer.resetStyle();
    }

    /**
     * 通过一个Container关闭已经打开的菜单，并重置菜单的style
     * 关闭位于这个Container级别下的所有的菜单，不包括这个Container
     * @param root 为 Container
     */
    public void closeMenu(BaseContainer root) {
        List<BaseContainer> containers = menuForest.getChildsByClass(root, BaseContainer.class);
        for (BaseContainer container : containers) {
            if (isOpenedMenu(container)) {
                container.hidden();
            }
        }
    }
    /**
     * 判断这个菜单是否已经被打开
     * @param menu
     * @return 如果被打开，返回true
     */
    public boolean isOpenedMenu(BaseContainer menu) {
        return menu.getVisibility() == View.VISIBLE;
    }
}

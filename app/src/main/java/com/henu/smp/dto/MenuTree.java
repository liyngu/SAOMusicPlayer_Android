package com.henu.smp.dto;

import android.util.Log;

import com.henu.smp.base.BaseButton;
import com.henu.smp.base.BaseMenu;
import com.henu.smp.entity.Menu;
import com.henu.smp.widget.SmpWidget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liyngu on 12/15/15.
 */
public class MenuTree {
    private static final String LOG_TAG = MenuTree.class.toString();
    private HashMap<SmpWidget, TreeNode> mDataMap = new HashMap<>();
    private BaseMenu mRootMenu;
    private BaseMenu focusMenu;

    public List<Menu> parseData() {
        TreeNode rootNode = mDataMap.get(mRootMenu);
        return this.parseData(rootNode);
    }

    public List<Menu> parseData(TreeNode node) {
        if (node == null) {
            return new ArrayList<>();
        }

        List<TreeNode> btnNodes = node.getChildren();
        int btnNodesCount = btnNodes.size();
        List<Menu> menuList = new ArrayList<>();
        for (int i = 0; i < btnNodesCount; i++) {
            TreeNode btnNode = btnNodes.get(i);
            BaseButton btn = (BaseButton) btnNode.getElem();
            Menu menuData = btn.getData();

            TreeNode childMenuNode = btnNode.getFirstChild();
            List<Menu> childMenuList = this.parseData(childMenuNode);
            menuData.setMenus(childMenuList);

            menuList.add(menuData);
        }
        return menuList;
    }

    /**
     * 如果参数是一个按钮，则返回的结果必定是此按钮对应的菜单
     *
     * @param btn
     * @return
     */
    public BaseMenu getChild(BaseButton btn) {
        TreeNode node = mDataMap.get(btn);
        if (btn == null || node == null) {
            return null;
        }
        TreeNode childNode = node.getFirstChild();
        if (childNode == null) {
            Log.i(LOG_TAG, "get error: not exist the child node");
            return null;
        }
        return (BaseMenu) childNode.getElem();
    }

    /**
     * 如果参数是一个菜单，则获得的必定是这个菜单中的所有按钮
     *
     * @param menu
     * @return
     */
    public List<BaseButton> getChildren(BaseMenu menu) {
        TreeNode node = mDataMap.get(menu);
        if (menu == null || node == null) {
            Log.i(LOG_TAG, "get error: not exist the tree node");
            return new ArrayList<>();
        }
        List<TreeNode> childrenNodes = node.getChildren();
        return this.parseToList(childrenNodes, BaseButton.class);
    }
    /**
     * 获得不包括这个菜单在内的位于这个菜单级别下的所有菜单
     *
     * @param menu
     * @return
     */
    public List<BaseMenu> getAllChildren(BaseMenu menu) {
        TreeNode node = mDataMap.get(menu);
        if (menu == null || node == null) {
            Log.i(LOG_TAG, "get error: not exist the tree node");
            return new ArrayList<>();
        }
        List<TreeNode> childrenNodes = node.getAllChildren();
        return this.parseToList(childrenNodes, BaseMenu.class);
    }

    /**
     * 建立父节点与字节点之间的关系
     * @param parent
     * @param elem
     */
    public void addChild(SmpWidget parent, SmpWidget elem) {
        TreeNode parentNode = mDataMap.get(parent);
        if (parentNode == null) {
            Log.i(LOG_TAG, "add error: not exist the tree node");
            return;
        }
        if (elem == null) {
            Log.i(LOG_TAG, "add error: you want to add an empty elem");
            return;
        }
        TreeNode childNode = new TreeNode(elem);
        mDataMap.put(elem, childNode);
        parentNode.addChild(childNode);
    }

    public void remove(SmpWidget elem) {
        TreeNode treeNode = mDataMap.get(elem);
        treeNode.getParent().remove(treeNode);
        mDataMap.remove(elem);
    }

    /**
     * 将一个树节点的list转换成所需要形式的list
     *
     * @param nodeList
     * @param cls
     * @param <T>
     * @return
     */
    private <T extends SmpWidget> List<T> parseToList(List<TreeNode> nodeList, Class<T> cls) {
        List<T> items = new ArrayList<>();
        int nodeCount = nodeList.size();
        for (int i = 0; i < nodeCount; i++) {
            SmpWidget widget = nodeList.get(i).getElem();
            if (cls.isInstance(widget)) {
                items.add(cls.cast(widget));
            }
        }
        return items;
    }

    /**
     * 获得一个组件的父元素，不会被直接调用
     * @param widget
     * @return
     */
    private SmpWidget getWidgetParent(SmpWidget widget) {
        TreeNode node = mDataMap.get(widget);
        if (widget == null || node == null) {
            Log.i(LOG_TAG, "get error: not exist the tree node");
            return null;
        }
        TreeNode parent = node.getParent();
        if (parent == null) {
            Log.i(LOG_TAG, "get error: not exist the parent node");
            Log.i(LOG_TAG, "cause of: the node maybe is a root node");
            return null;
        }
        return parent.getElem();
    }

    /**
     * 如果元素是一个菜单，则父元素必定是一个按钮
     * @param menu
     * @return
     */
    public BaseButton getParent(BaseMenu menu) {
        SmpWidget widget = this.getWidgetParent(menu);
        if (widget == null) {
            return null;
        }
        return (BaseButton) widget;
    }

    /**
     * 如果元素是一个按钮，则父元素必定是一个菜单
     * @param btn
     * @return
     */
    public BaseMenu getParent(BaseButton btn) {
        SmpWidget widget = this.getWidgetParent(btn);
        if (widget == null) {
            return null;
        }
        return (BaseMenu) widget;
    }

    /**
     * 获得包括此节点在内的所有兄弟节点
     * 只有按钮才会有兄弟节点
     * @param btn
     * @return
     */
    public List<BaseButton> getSiblings(BaseButton btn) {
        TreeNode node = mDataMap.get(btn);
        if (btn == null || node == null) {
            Log.i(LOG_TAG, "get error: not exist the tree node");
            return null;
        }
        List<TreeNode> nodeList = node.getSiblings();
        List<BaseButton> btnList = this.parseToList(nodeList, BaseButton.class);
        btnList.add(btn);
        return btnList;
    }

    public void rollbackFocus() {
        TreeNode node = mDataMap.get(focusMenu);
        focusMenu = (BaseMenu) getWidgetParent(getWidgetParent(node.getElem()));
    }

    public void setRoot(SmpWidget root) {
        mRootMenu = (BaseMenu) root;
        TreeNode node = new TreeNode(root);
        mDataMap.put(root, node);
    }

    public BaseMenu getRoot() {
        return mRootMenu;
    }

    public void setFocus(SmpWidget focus) {
        this.focusMenu = (BaseMenu) focus;
    }

    public BaseMenu getFocus() {
        return focusMenu;
    }
}

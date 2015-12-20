package com.henu.smp.widget;

import android.util.Log;

import com.henu.smp.Constants;
import com.henu.smp.base.BaseButton;
import com.henu.smp.base.BaseMenu;
import com.henu.smp.model.Menu;
import com.henu.smp.model.SmpWidget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liyngu on 12/15/15.
 */
public class MenuTree {
    private static final String LOG_TAG = MenuTree.class.toString();
    private static MenuTree menuTree;
    private HashMap<SmpWidget, TreeNode> dataMap = new HashMap<>();
    private BaseMenu rootMenu;
    private BaseMenu focusMenu;


    public List<Menu> parseData() {
        TreeNode rootNode = this.dataMap.get(this.rootMenu);
        return this.parseData(rootNode);
    }

    public List<Menu> parseData(TreeNode node){
        if (node == null) {
            return new ArrayList<>();
        }

        List<TreeNode> btnNodes = this.getChilds(node);
        int btnNodesCount = btnNodes.size();
        List<Menu> menuList = new ArrayList<>();
        for (int i = 0; i < btnNodesCount; i++) {

            TreeNode btnNode = btnNodes.get(i);
            BaseButton btn = (BaseButton) btnNode.getElem();
            Menu menuObj = new Menu();
            menuObj.setIndex(btn.getIndex());
            menuObj.setName(btn.getName());
            menuObj.setText(btn.getText());

            TreeNode childMenuNode = this.getChild(btnNode);
            List<Menu> childMenuList = this.parseData(childMenuNode);
            menuObj.setMenus(childMenuList);

            menuList.add(menuObj);
        }
        return menuList;
    }
//    public JSONArray parseData() {
//        TreeNode rootNode = this.dataMap.get(this.rootMenu);
//        JSONArray menuArray = new JSONArray();
//        try {
//            menuArray = this.parseData(rootNode);
//        } catch (JSONException e) {
//            Log.i(LOG_TAG, e.getMessage());
//        }
//        return menuArray;
//    }
//
//    public JSONArray parseData(TreeNode node) throws JSONException {
//        if (node == null) {
//            return new JSONArray();
//        }
//
//        List<TreeNode> btnNodes = this.getChilds(node);
//        int btnNodesCount = btnNodes.size();
//        JSONArray menuArray = new JSONArray();
//        for (int i = 0; i < btnNodesCount; i++) {
//
//            TreeNode btnNode = btnNodes.get(i);
//            BaseButton btn = (BaseButton) btnNode.getElem();
//            // JSON菜单数据的获取
//            JSONObject menuObj = new JSONObject();
//            menuObj.put("index", btn.getIndex());
//            menuObj.put("name", btn.getName());
//            menuObj.put("displayname", btn.getText());
//
//            //JSON子菜单数据大获取
//            TreeNode childMenuNode = this.getChild(btnNode);
//            JSONArray childMenuArray = this.parseData(childMenuNode);
//            menuObj.put(Constants.MENU, childMenuArray);
//
//            menuArray.put(menuObj);
//        }
//        return menuArray;
//    }

    public BaseMenu getChild(BaseButton button) {
        TreeNode treeNode = this.dataMap.get(button);
        TreeNode childNode = treeNode.getLeftChild();
        return (BaseMenu) childNode.getElem();
    }

    public TreeNode getChild(TreeNode node) {
        return node.getLeftChild();
    }

    /**
     * 获得一个菜单中的所有按钮
     * @param menu
     * @return
     */
    public List<BaseButton> getChilds(BaseMenu menu) {
        TreeNode node = this.dataMap.get(menu);
        List<TreeNode> childNodes = this.getChilds(node);
        return this.parseToList(childNodes, BaseButton.class);
    }

    public List<TreeNode> getChilds(TreeNode node) {
        TreeNode chlidNode = node.getLeftChild();
        return this.getSiblings(chlidNode);
    }

    /**
     * 将一个树节点的list转换成所需要形式的list
     * @param nodes
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends SmpWidget> List<T> parseToList(List<TreeNode> nodes, Class<T> cls) {
        List<T> items = new ArrayList<>();
        int nodesCount = nodes.size();
        for (int i = 0; i < nodesCount; i++) {
            SmpWidget widget = nodes.get(i).getElem();
            if (cls.isInstance(widget)) {
                items.add((T) widget);
            }
        }
        return items;
    }

    /**
     * 获得包括此节点在内的所有兄弟节点
     * @param node
     * @return
     */
    public List<TreeNode> getSiblings(TreeNode node) {
        List<TreeNode> nodes = new ArrayList<>();
        TreeNode preNode = node.getParent();
        while (preNode != null && preNode.getRightChild() == node) {
            node = preNode;
            preNode = preNode.getParent();
        }
        while (node != null) {
            nodes.add(node);
            node = node.getRightChild();
        }
        return nodes;
    }


    public <T extends SmpWidget> List<T> getChildsByClass(SmpWidget elem, Class<T> cls) {
        List<T> items = new ArrayList<>();
        TreeNode root = dataMap.get(elem);
        List<TreeNode> nodes = getAllChilds(root);
        nodes.remove(root);
        for (TreeNode node : nodes) {
            SmpWidget e = node.getElem();
            if (cls.isInstance(e)) {
                items.add((T) e);
            }
        }
        return items;
    }

    public static MenuTree getIntance() {
        if (menuTree == null) {
            menuTree = new MenuTree();
        }
        return menuTree;
    }

    public static void destoryInstance() {
        menuTree = null;
    }

    public void addChild(SmpWidget parent, SmpWidget elem) {
        TreeNode parentNode = dataMap.get(parent);
        if (parentNode == null || parent == null) {
            return;
        }
        TreeNode childNode = parentNode.getLeftChild();
        if (childNode == null) {
            TreeNode node = new TreeNode(elem);
            parentNode.setLeftChild(node);
            node.setParent(parentNode);
            dataMap.put(elem, node);
        } else {
            addSibling(childNode.getElem(), elem);
        }
    }

    public void addSibling(SmpWidget sibling, SmpWidget elem) {
        TreeNode siblingNode = dataMap.get(sibling);
        if (siblingNode == null || sibling == null) {
            return;
        }
        TreeNode nextSiblingNode = siblingNode.getRightChild();
        while (nextSiblingNode != null) {
            siblingNode = nextSiblingNode;
            nextSiblingNode = siblingNode.getRightChild();
        }
        TreeNode node = new TreeNode(elem);
        siblingNode.setRightChild(node);
        node.setParent(siblingNode);
        dataMap.put(elem, node);
    }

    public <T extends SmpWidget> List<T> getSiblingsByClass(SmpWidget elem, Class<T> cls) {
        List<T> items = new ArrayList<>();
        TreeNode root = dataMap.get(elem);
        List<TreeNode> nodes = getSiblings(root);
        nodes.remove(root);
        for (TreeNode node : nodes) {
            SmpWidget e = node.getElem();
            if (cls.isInstance(e)) {
                items.add((T) e);
            }
        }
        return items;
    }


    public void rollbackFocus() {
        TreeNode node = dataMap.get(focusMenu);
        focusMenu = (BaseMenu) getParent(getParent(node.getElem()));
    }

    public SmpWidget getParent(SmpWidget elem) {
        if (elem == null) {
            return null;
        }
        TreeNode node = dataMap.get(elem);
        TreeNode parent = node.getParent();
        if (parent == null) {
            return null;
        }
        while (parent.getRightChild() == node) {
            node = parent;
            parent = parent.parent;
        }
        return parent.getElem();
    }

    public int getChildCount(SmpWidget elem) {
        TreeNode node = dataMap.get(elem);
        node = node.getLeftChild();
        int count = 1;
        while (node.getRightChild() != null) {
            node = node.getRightChild();
            count++;
        }
        return count;
    }
    public SmpWidget getChildAt(SmpWidget elem, int index) {
        TreeNode node = dataMap.get(elem);
        node = node.getLeftChild();
        for (int i = 0; i < index - 1; i++) {
            node = node.getRightChild();
        }
        return node.getElem();
    }

    public SmpWidget getChild(SmpWidget elem) {
        TreeNode node = dataMap.get(elem);
        return node.getLeftChild().getElem();
    }

    public void setRoot(SmpWidget root) {
        this.rootMenu = (BaseMenu) root;
        TreeNode node = new TreeNode(root);
        dataMap.put(root, node);
    }

    public BaseMenu getRoot() {
        return rootMenu;
    }

    public void setFocus(SmpWidget focus) {
        this.focusMenu = (BaseMenu) focus;
    }

    public BaseMenu getFocus() {
        return focusMenu;
    }

    /*之后的方法为对树节点操作的方法*/
    public List<TreeNode> getAllChilds(TreeNode node) {
        if (node == null) {
            return new ArrayList<>();
        }
        List<TreeNode> nodes = new ArrayList<>();
        while (node != null) {
            nodes.addAll(getAllChilds(node.getRightChild()));
            nodes.add(node);
            node = node.getLeftChild();
        }
        return nodes;
    }


    /**
     * 二叉树树的数据结构，提供左右孩子和父节点的操作
     * 使用左孩子又兄弟的存储法存储需要存储的菜单森林
     */
    private class TreeNode {
        private SmpWidget elem;
        private TreeNode leftChild;
        private TreeNode rightChild;
        private TreeNode parent;

        public TreeNode(SmpWidget elem) {
            this.elem = elem;
        }

        public SmpWidget getElem() {
            return elem;
        }

        public TreeNode getParent() {
            return parent;
        }

        public void setParent(TreeNode parent) {
            this.parent = parent;
        }

        public TreeNode getLeftChild() {
            return leftChild;
        }

        public void setLeftChild(TreeNode leftChild) {
            this.leftChild = leftChild;
        }

        public TreeNode getRightChild() {
            return rightChild;
        }

        public void setRightChild(TreeNode rightChild) {
            this.rightChild = rightChild;
        }
    }
}

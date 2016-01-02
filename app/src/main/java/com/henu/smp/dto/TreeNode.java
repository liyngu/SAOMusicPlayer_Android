package com.henu.smp.dto;

/**
 * Created by liyngu on 1/2/16.
 */

import com.henu.smp.model.SmpWidget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyngu on 12/15/15.
 * 用二叉树的数据结构存储一个树，对外提供逻辑上的树操作
 * 这个类的操作全为不安全的操作，即没有提供对空操作的判断
 */
public class TreeNode {
    private SmpWidget mElem;
    private TreeNode mLeftChild;
    private TreeNode mRightChild;
    private TreeNode mParent;

    public TreeNode(SmpWidget elem) {
        mElem = elem;
    }

    public SmpWidget getElem() {
        return mElem;
    }

    /**
     * 为这个节点添加一个兄弟节点
     * @param sibling
     */
    public void addSibling(TreeNode sibling) {
        // 获得右孩子判断此节点是否已经有兄弟
        TreeNode node = this;
        TreeNode rightChild = node.getRightChild();
        // 同时迭代右孩子节点和右孩子的父亲节点，可以获得最后一个兄弟节点
        while (rightChild != null) {
            node = rightChild;
            rightChild = rightChild.getRightChild();
        }
        node.setRightChild(sibling);
    }

    /**
     * 获得这个节点的父亲节点
     * @return
     */
    public TreeNode getParent() {
        // 获得此节点的物理上的父亲节点，只有左孩子的父节点才是真正的父节点
        TreeNode parentNode = this.getParentNode();
        // 当且仅当此节点为根节点时，父节点为空，所以不在迭代中进行空判断
        if (parentNode == null) {
            return null;
        }
        // 如果此节点的右孩子是这个节点时继续迭代
        TreeNode node = this;
        while (parentNode.getRightChild() == node) {
            node = parentNode;
            parentNode = parentNode.getParentNode();
        }
        return parentNode;
    }

    /**
     * 为一个节点添加一个孩子
     *
     * @param child
     */
    public void addChild(TreeNode child) {
        // 获得父节点的左孩子判断此节点现在是否已经有孩子
        TreeNode leftChild = this.getLeftChild();
        // 如果没有左孩子，则添加左孩子，如果有左孩子，则为左孩子节点添加兄弟
        if (leftChild == null) {
            this.setLeftChild(child);
        } else {
            leftChild.addSibling(child);
        }
    }

    /**
     * 获得不包括此节点在内的所有兄弟节点
     * @return
     */
    public List<TreeNode> getSiblings() {
        List<TreeNode> nodeList = new ArrayList<>();
        // 首先遍历位于这个节点之后的兄弟节点
        TreeNode sibling = this.getRightChild();
        while (sibling != null) {
            nodeList.add(sibling);
            sibling = sibling.getRightChild();
        }
        // 然后遍历位于这个节点之前的兄弟节点
        TreeNode node = this;
        TreeNode parent = node.getParentNode();
        while (parent.getRightChild() == node) {
            nodeList.add(parent);
            node = parent;
            parent = node.getParentNode();
        }
        return nodeList;
    }

    /**
     * 获得不包括此节点在内的所有孩子节点
     * @return
     */
    public List<TreeNode> getChildren() {
        List<TreeNode> nodeList = new ArrayList<>();
        TreeNode firstChild = this.getLeftChild();
        if (firstChild != null) {
            nodeList.add(firstChild);
            List<TreeNode> siblings = firstChild.getSiblings();
            nodeList.addAll(siblings);
        }
        return nodeList;
    }

    /**
     * 获得不包括此节点在内的所有孩子节点以及此节点之后的孩子的所有孩子节点
     * @return
     */
    public List<TreeNode> getAllChildren() {
        List<TreeNode> nodeList = new ArrayList<>();
        List<TreeNode> children = this.getChildren();
        // 获得所有的孩子并继续遍历，并继续递归调用此方法
        int childCount = children.size();
        for (int i = 0; i < childCount; i++) {
            TreeNode child = children.get(i);
            List<TreeNode> childrenNodes = child.getAllChildren();
            nodeList.add(child);
            nodeList.addAll(childrenNodes);
        }
        return nodeList;
    }

    /**
     * 获得这个节点的第一个孩子
     * @return
     */
    public TreeNode getFirstChild() {
        return this.getLeftChild();
    }

        /*以下几个私有方法为二叉树的基本操作*/

    /**
     * 对父节点的操作并不是真正的父亲，只是二叉树中的父亲
     */
    private void setParentNode(TreeNode parent) {
        mParent = parent;
    }

    /**
     * 每次添加孩子节点时，都会为此孩子节点设置父节点
     *
     * @param leftChild
     */
    private void setLeftChild(TreeNode leftChild) {
        mLeftChild = leftChild;
        leftChild.setParentNode(this);
    }

    /**
     * 每次添加孩子节点时，都会为此孩子节点设置父节点
     *
     * @param rightChild
     */
    private void setRightChild(TreeNode rightChild) {
        mRightChild = rightChild;
        rightChild.setParentNode(this);
    }

    private TreeNode getParentNode() {
        return mParent;
    }

    private TreeNode getLeftChild() {
        return mLeftChild;
    }

    private TreeNode getRightChild() {
        return mRightChild;
    }
}
package com.ztsdk.lib.two;


//滑动窗口 双指针技巧
public class Day12 {

    public static void main(String[] args) {


    }

    //700. 二叉搜索树中的搜索
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null) return null;
        if (root.val == val) {
            return root;
        } else if (root.val < val) {
            return searchBST(root.right, val);

        } else if (root.val > val) {
            return searchBST(root.left, val);
        }
        return root;
    }

    //701. 二叉搜索树中的插入操作
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);
        if (root.val == val) {

        } else if (root.val < val) {
            root.right = insertIntoBST(root.right, val);

        } else if (root.val > val) {
            root.left = insertIntoBST(root.left, val);
        }
        return root;

    }

    //98. 验证二叉搜索树
    public boolean isValidBST(TreeNode root) {

        return isValidBST(root, null, null);


    }

    private boolean isValidBST(TreeNode root, TreeNode min, TreeNode max) {
        if (root == null) return true;
        if (min != null && min.val >= root.val) return false;
        if (max != null && max.val <= root.val) return false;
        return isValidBST(root.left, min, root) && isValidBST(root.right, root, max);
    }

    //450. 删除二叉搜索树中的节点
    public TreeNode deleteNode(TreeNode root, int key) {

        if (root == null) return null;
        if (root.val == key) {
            //对节点进行操作，有一个节点的情况
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;

            //有两个节点情况：
            TreeNode min = getMin(root.right);
            root.val = min.val;
            //注意，这里是root.right
            root.right = deleteNode(root.right, min.val);

        } else if (root.val < key) {
            root.right = deleteNode(root.right, key);

        } else if (root.val > key) {
            root.left = deleteNode(root.left, key);
        }
        return root;


    }

    private TreeNode getMin(TreeNode right) {
        while (right.left != null) right = right.left;
        return right;
    }

}




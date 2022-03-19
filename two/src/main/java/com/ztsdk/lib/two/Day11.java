package com.ztsdk.lib.two;


//滑动窗口 双指针技巧
public class Day11 {

    public static void main(String[] args) {


    }

    // 226. 翻转二叉树
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        TreeNode left = root.left;
        TreeNode right = root.right;
        invertTree(left);
        invertTree(right);

        root.left = right;
        root.right = left;

        return root;
    }

    //114. 二叉树展开为链表
    public void flatten(TreeNode root) {

        if (root == null) return;
        TreeNode left = root.left;
        TreeNode right = root.right;
        flatten(left);
        flatten(right);

        root.left = null;
        root.right = left;
        TreeNode p = root;
        while (p.right != null) {
            p = p.right;
        }

        p.right = right;

    }

    //116. 填充每个节点的下一个右侧节点指针
    public Node connect(com.ztsdk.lib.two.Node root) {
        if (root == null) return null;
        connectTwo(root.left, root.right);
        return root;
    }

    private void connectTwo(com.ztsdk.lib.two.Node left, com.ztsdk.lib.two.Node right) {
        if (left == null || right == null) return;
        left.next = right;
        connectTwo(left.left, left.right);
        connectTwo(right.left, right.right);
        connectTwo(left.right, right.left);
    }


}




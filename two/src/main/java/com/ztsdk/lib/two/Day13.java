package com.ztsdk.lib.two;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//滑动窗口 双指针技巧
public class Day13 {

    public static void main(String[] args) {


    }


    //102. 二叉树的层序遍历
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;
        Queue<TreeNode> q = new LinkedList<>();
        //二叉树不需要visited
        q.offer(root);

        while (!q.isEmpty()) {
            int size = q.size();
            List<Integer> inner = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode cur = q.poll();
                inner.add(cur.val);
                if (cur.left != null) q.offer(cur.left);
                if (cur.right != null) q.offer(cur.right);
            }
            res.add(inner);
        }
        return res;
    }

    //    111. 二叉树的最小深度
    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        Queue<TreeNode> q = new LinkedList<>();
        //二叉树不需要visited
        q.offer(root);
        int depth = 1;
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode cur = q.poll();
                if (cur.left == null && cur.right == null) {
                    return depth;
                }
                if (cur.left != null) q.offer(cur.left);
                if (cur.right != null) q.offer(cur.right);
            }
            depth++;
        }
        return depth;

    }

    //752. 打开转盘锁 todo
    public int openLock(String[] deadends, String target) {


        return 0;
    }


}




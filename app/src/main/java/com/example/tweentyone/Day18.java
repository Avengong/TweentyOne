package com.example.tweentyone;

import com.example.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Day18 {

    public static void main(String[] asrgs) {
        int[] test = new int[]{4, 3, 2, 3, 5, 2, 1};
        Day18 day18 = new Day18();
        boolean b = day18.canPartitionKSubsets(test, 4);
        System.out.println("day18, res: " + b);

    }

    public boolean canPartitionKSubsets(int[] nums, int k) {

        if (k > nums.length) return false;
        int sum = 0;
        for (int n : nums) {
            sum += n;
        }
        if (sum % k != 0) return false;
        int target = sum / k;


        //先遍历桶，在遍历数组
        boolean[] used = new boolean[nums.length];

        return backTrack(k, nums, 0, used, target, 0);
    }

    private boolean backTrack(int k, int[] nums, int start, boolean[] used, int target,
                              int bucket) {
        System.out.println("k= " + k);
        if (k == 0) {
            return true;
        }
        if (target == bucket) {
            return backTrack(k - 1, nums, 0, used, target, 0);
        }

        for (int i = start; i < nums.length; i++) {
            int num = nums[i];
            if (used[i]) {
                continue;
            }
            if (bucket + num > target) {
                continue;
            }
            used[i] = true;
            bucket = bucket + num;
            if (backTrack(k, nums, i + 1, used, target, bucket)) {
                return true;
            }
            bucket = bucket - num;
            used[i] = false;

        }
        return false;
    }


    public void flatten(TreeNode root) {
        //一个节点能不能干事？
        if (root == null) {
            return;
        }

        flatten(root.left);
        flatten(root.right);
        TreeNode tempRight = root.right;
        root.right = root.left;
        root.left = null;
        //都是拿header来处理的
        TreeNode rigth = root;
        while (rigth.right != null) {
            rigth = rigth.right;
        }
        rigth.right = tempRight;

    }

    //验证bst二叉树合法
    public boolean isValidBST(TreeNode root) {
        //左小右大。 对于任意节点，注意是左子树的最大值小于val，而右子树的最小节点要大于val。
        // 所以是对整个树有约束的。而不仅仅是当前节点

        //通过增加参数的方式来传递约束条件。
        return isValidBST(root, null, null);
    }

    //假定 max>root>min
    private boolean isValidBST(TreeNode root, TreeNode min, TreeNode max) {
        // base case
        if (root == null) {
            return true;
        }

        //明确这个方法可以已经证明了左右子树合法性。也就是min和max都有值了。

        //当前节点怎么做？ min代表左子树中的最大值？是的。左小右大规则。
//        if (min != null && min.val >= root.val) {
//            return false;
//        }
//        if (max != null && max.val <=root.val){
//            return false;
//        }

        if ((min == null || min.val < root.val) && (max == null || max.val > root.val)) {
            return true;
        }

        return isValidBST(root.left, min, root) && isValidBST(root.right, root, max);
    }

    public TreeNode deleteNode(TreeNode root, int key) {
        //base case
        if (root == null) {
            return null;
        }
        // 框架遍历
        if (root.val == key) {
            //找到节点：
            // 1,叶子节点
            if (root.left == null && root.right == null) {
                // root=null; //有必要吗？
                return null;
            }
            //2，只有左右一个节点
            if (root.left == null && root.right != null) {
                return root.right;
            }
            if (root.left != null && root.right == null) {
                return root.left;
            }
            //3， 有左右两个节点
            TreeNode rightMax = getMin(root.right);
            //先简单的处理一下：
            root.val = rightMax.val;

            // swap(root,rightMax);
            //删除交换后的节点
            root.right = deleteNode(root.right, root.val);

        } else if (key > root.val) {
            root.right = deleteNode(root.right, key);
        } else if (key < root.val) {
            //重新赋值要接收最新的左子树
            root.left = deleteNode(root.left, key);
        }
        return root;
    }

    private TreeNode getMin(TreeNode node) {
        if (node == null) {
            return null;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    //BFS 广度优先搜索 二叉树的最小高度
    public int minDepth(TreeNode root) {

        //base case
        if (root == null) {
            return -1;
        }
        //队列用来存储连接的顶点，根节点就是第一个
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        // 深度depth=1，
        int depth = 1;
        //可能会有visited数组

        // 明确框架： start ？ target？
        //遍历队列：从根节点开始。 如果找到叶子节点，那么它肯定是第一个。就是最短的
        while (q.size() > 0) {
            int size = q.size();
            //遍历一层
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                if (node.left == null && node.right == null) {
                    //叶子节点
                    return depth;
                } else {
                    if (node.left != null)
                        q.offer(node.left);
                    if (node.right != null)
                        q.offer(node.right);

                }
            }
            depth++;
        }
        return -1;
    }

    //BFS 打开密码锁
    public int openLock(String[] deadends, String target) {

        //可以认为四位密码是一个节点，每次波动一位的数字就换成了另外节点。相当于一个节点连接了8个节点
        // 可以当做是图，，//开始套用框架
        //base case
        //队列存储周围的节点
        Queue<String> q = new LinkedList<>();
        HashSet<String> visited = new HashSet<>();
        String start = "0000";
        List<String> strings = Arrays.asList(deadends);

        q.offer(start);
        visited.add(start);
        //depth=1， 次数。
        int depth = 0;
        //开始遍历 start target？
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                String pswd = q.poll();

                if (strings.contains(pswd)) {
                    continue;
                }

                if (target.equals(pswd)) {
                    return depth;
                }

                //生成周围节点
                for (int j = 0; j < 4; j++) {
                    String plus = plusOne(pswd, j);
                    String minus = minusOne(pswd, j);
                    //加上周围节点
                    if (!visited.contains(plus)) {
                        q.offer(plus);
                        visited.add(plus);
                    }
                    if (!visited.contains(minus)) {
                        q.offer(minus);
                        visited.add(minus);
                    }

                }
            }
            depth++;
        }
        return -1;
    }

    public int openLock3(String[] deadends, String target) {

        //可以认为四位密码是一个节点，每次波动一位的数字就换成了另外节点。相当于一个节点连接了8个节点
        // 可以当做是图，，//开始套用框架
        //base case
        //队列存储周围的节点
        Queue<String> q = new LinkedList<>();
        HashSet<String> visited = new HashSet<>();
        String start = "0000";
        Collections.addAll(visited, deadends);
        q.offer(start);
        visited.add(start);
        //depth=0， 次数。
        int depth = 0;
        //开始遍历 start target？
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                String pswd = q.poll();

//                if (!visited.contains(pswd)) {
//                    visited.add(pswd);
//                }

                if (target.equals(pswd)) {
                    return depth;
                }

                //生成周围节点
                for (int j = 0; j < 4; j++) {
                    String plus = plusOne(pswd, j);
                    String minus = minusOne(pswd, j);
                    //加上周围节点
                    if (!visited.contains(plus)) {
                        q.offer(plus);
                        visited.add(plus);
                    }
                    if (!visited.contains(minus)) {
                        q.offer(minus);
                        visited.add(minus);
                    }

                }
            }
            depth++;
        }
        return -1;
    }

    private String minusOne(String pswd, int j) {
        char[] ch = pswd.toCharArray();
        if (ch[j] == '0') {
            ch[j] = '9';
        } else {
//            ch[j]+=1;

            ch[j] = (char) (ch[j] - 1);
        }
        return new String(ch);
    }

    private String plusOne(String pswd, int j) {
        char[] ch = pswd.toCharArray();
        if (ch[j] == '9') {
            ch[j] = '0';
        } else {
//            ch[j]+=1;

            ch[j] = (char) (ch[j] + 1);
        }
        return new String(ch);
    }

    //双向bfs，搜索，优化
    public int openLock2(String[] deadends, String target) {
        HashSet<String> q1 = new HashSet();
        HashSet<String> q2 = new HashSet();
        HashSet<String> visited = new HashSet();
        q1.add("0000");
        q2.add(target);
        for (String dead : deadends) {
            visited.add(dead);
        }
        int depth = 0;
        while (!q1.isEmpty() && !q2.isEmpty()) {

            int size = q1.size();
            HashSet<String> others = new HashSet<>();
            for (String pswd : q1) {
                if (visited.contains(pswd)) {
                    continue;
                }
                if (q2.contains(pswd)) {
                    return depth;
                }
                visited.add(pswd);
                //构造周围节点，进行扩散
                for (int j = 0; j < 4; j++) {
                    String plus = plusOne(pswd, j);
                    String minus = minusOne(pswd, j);
                    //加上周围节点
                    if (!visited.contains(plus)) {
                        others.add(plus);

                    }
                    if (!visited.contains(minus)) {
                        others.add(minus);
                    }


                }
            }
            depth++;
            q1 = q2;
            q2 = others;
        }
        return -1;

    }

    public List<Integer> inorderTraversal(TreeNode root) {

        List<Integer> res = new ArrayList();
        return travers(root, res);
    }

    private List<Integer> travers(TreeNode root, List<Integer> res) {
        if (root == null) {
            return null;
        }
        travers(root.left, res);
        res.add(root.val);
        travers(root.right, res);
        return res;
    }




}

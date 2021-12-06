package com.example.binary_tree.simple;


import com.example.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class Day01 {
    List<TreeNode> res = new ArrayList<>();

    public static void main(String[] asrgs) {
        int[] test = new int[]{4, 3, 2, 3, 5, 2, 1};


//        List<Integer> anagrams = findAnagrams("cbaebabacd", "abc");
        List<Integer> anagrams = findAnagrams2("cbaebabacd", "abc");
        System.out.println("res: " + anagrams.toString());
    }

    //滑动窗口   todo
    public static List<Integer> findAnagrams2(String s, String p) {
        if (s.length() < p.length()) {
            return new ArrayList<Integer>();
        }
        int[] sCnt = new int[26];
        int[] pCnt = new int[26];

        List<Integer> res = new ArrayList<>();
        int pLen = p.length();
        for (int i = 0; i < pLen; i++) {
            pCnt[p.charAt(i) - 'a']++;
            sCnt[s.charAt(i) - 'a']++;
        }
        if (Arrays.equals(sCnt, pCnt)) {
            res.add(0);
        }
        for (int i = 0; i < (s.length() - pLen); i++) {
            sCnt[s.charAt(i) - 'a']--;
            sCnt[s.charAt(i + pLen) - 'a']++;
            if (Arrays.equals(sCnt, pCnt)) {
                res.add(i + 1);
            }
        }
        return res;
    }

    public static List<Integer> findAnagrams(String s, String p) {
        //怎么去分析呢？
        //既然是二叉树相关，那么就往二叉树方面想。
        //我能想到的二叉树相关的知识点：遍历、查询、插入、删除？
        //怎么联系题目呢？ 草，这tm是滑动窗口。。石乐志！

        HashMap<Character, Integer> need = new HashMap<Character, Integer>();
        HashMap<Character, Integer> window = new HashMap<Character, Integer>();
        //原理： need代表了需要满足的条件，即字符对应的个数。window往右开始滑动，每滑动
        //一个字符，则判断是否满足need的一个字符
        List<Integer> res = new ArrayList<>();
        //左右指针,定义左闭右开的窗口
        int left = 0, right = 0;
        char[] chars = p.toCharArray();
        for (char ch : chars) {
            Integer counter = need.get(ch);
            int count = 1;
            if (counter != null) {
                count = count + counter;
            }
            need.put(ch, count);
        }
        Iterator<Map.Entry<Character, Integer>> iterator = need.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Character, Integer> next = iterator.next();
            System.out.println("valid: " + next.toString());
        }

        int valid = 0;

        while (right < s.length()) {
            char c = s.charAt(right);
            // 右移窗口
            right++;

            Integer needChar = need.get(c);
            //只有满足need，才加入到window中去
            if (needChar != null) {
                int count = 1;
                Integer counter = window.get(c);
                if (counter != null) {
                    count = count + counter;
                }
                window.put(c, count);
                if (window.get(c) == needChar) {
                    valid++;
                }
            }
            System.out.println("valid: " + valid + ", left:" + left);
            while (valid == need.size()) {
                //满足条件了，开始收缩

                char leftChar = s.charAt(left);
                left++;
                Integer integer = need.get(leftChar);
                if (integer != null) {
                    window.put(leftChar, window.get(leftChar) - 1);
                    if (window.get(leftChar) == 0) {
                        res.add(left - 1);
                        valid--;
                    }
                }
            }
        }
        return res;
    }

    //构建一颗二叉搜索树 包含所有的BST,返回个数
    public int generateTreeCount(int n) {
        return buildTreeCount(1, n);


    }

    private int buildTreeCount(int start, int end) {
        //动态规划？？？
        //根据题目得到递推状态转移方程
        //在选择自底向上的dp数组来计算
        /*
        G(n)=f(1)+...f(n);  G(n)表示以1~n为根节点的所以二叉搜索树的总和。f（i）表示以i为root节点的所有二叉搜索树的总和。

        f(i)=G(i-1)*G(n-i); G(i-1)：表示左子树所有二叉搜索树的总和，G(n-i)表示右子树所有二叉搜索树的总和。
        结合两个方程：
        G(n)=G(0)*G(n-1)+..G(i-1)*G(n-i)...+G(n-1)*G(0); i的取值范围是 1~n
        设dp数组 为G（n）的值集合


         */
        if (start > end) {
            return 0;
        }

        int[] G = new int[end + 1]; //dp数组用来存放G(n)的值。
        G[0] = 1;
        G[1] = 1;
        for (int i = 2; i <= end; i++) {
            for (int j = 1; j <= i; j++) {
                G[i] += G[j - 1] * G[i - j];
            }
        }
        return G[end];
    }

    //构建一颗二叉搜索树 包含所有的BST
    public List<TreeNode> generateTrees(int n) {
        return buildTree2(1, n);


    }

    private List<TreeNode> buildTree2(int start, int end) {

        List<TreeNode> res = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            List<TreeNode> leftNodes = buildTree2(start, i - 1);
            List<TreeNode> rightNodes = buildTree2(i + 1, end);
            for (TreeNode left : leftNodes) {
                for (TreeNode rigth : rightNodes) {
                    TreeNode root = new TreeNode(i);
                    root.left = left;
                    root.right = rigth;
                    res.add(root);
                }
            }

        }
        return res;
    }

    private List<TreeNode> buildTree(int start, int end) {
        //因为要构建所有的二叉搜索树，所以要用集合存起来。
        List<TreeNode> res = new ArrayList<>();
        //1，base case 没有左右子树了
        if (start > end) {
            res.add(null);
            return res;
        }

        for (int i = start; i <= end; i++) {
            //2，开始穷举root节点！！
            //3, 我相信这个方法，因此相当于已经得到左子树所有的集合，也得到了右子树所有的集合
            List<TreeNode> lefts = buildTree(start, i - 1);
            List<TreeNode> rigths = buildTree(i + 1, end);
            //4，开始组合所有的节点。认为当前节点就是root。从左子树拿一颗，从右子树拿一颗，接到root节点即满足要求。
            for (TreeNode l : lefts) {
                for (TreeNode r : rigths) {
                    TreeNode treeNode = new TreeNode(i);
                    treeNode.left = l;
                    treeNode.right = r;
                    res.add(treeNode);
                }
            }

        }
        return res;
    }

    private void backtrace(HashSet<TreeNode> visited, TreeNode root, int n) {
        //满足条件，此次递归结束。
        if (visited.size() == n) {
            res.add(root);
            return;
        }

        for (int i = 1; i <= n; i++) {
            TreeNode newRoot = new TreeNode(i);
            if (visited.contains(newRoot)) {
                continue;
            }
            //这里怎么办？？

            if (root.val > i) {
                root.left = newRoot;
            } else if (root.val < i) {
                root.right = newRoot;
            }
            visited.add(newRoot);
            backtrace(visited, root, n);
            visited.remove(newRoot);

        }
    }


}

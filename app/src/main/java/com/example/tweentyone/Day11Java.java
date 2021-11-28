package com.example.tweentyone;

class Day11Java {


    public Node connect(Node root) {


        if (root == null) return null;
        //一个节点无法完成，那么久两个节点。
        realConnect(root.left, root.right);
        return root;


    }

    private void realConnect(Node left, Node right) {
        if (left == null || right == null) return;
        //我相信这个定义，所以我敢递归。
        realConnect(left.left, left.right);
        realConnect(right.left, right.right);
        realConnect(left.right, right.left);
        //那我用哪种递归顺序呢？ 前、后都可以。
        left.next = right;

    }


    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }
}

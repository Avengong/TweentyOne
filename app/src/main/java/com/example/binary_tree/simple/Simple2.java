package com.example.binary_tree.simple;

import android.opengl.Matrix;

import com.example.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

class Simple2 {

    public static void main(String[] asrgs) {
        //判断两颗树是否相同。
        //

    }

    /**
     * todo 路径和  回溯
     *
     * @param root
     * @param targetSum
     * @return
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {

        return getSum(root, targetSum, 0);

    }

    private boolean getSum(TreeNode root, int targetSum, int sum) {
        if (root == null) {
            return sum == targetSum;
        }
        sum += root.val;
        if (root.left == null && root.right == null) {
            if (sum == targetSum) {
                return true;
            }
        }
        return getSum(root.left, targetSum, sum) || getSum(root.right, targetSum, sum);
    }

    /**
     * todo 最短路径 ，广度优先啦
     */

    public int minDepth(TreeNode root) {

        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();

        queue.offer(root);
        int res = 0;
        while (!queue.isEmpty()) {
            res++;
            int size = queue.size();
            while (size > 0) {
                TreeNode poll = queue.poll();
                if (poll.left == null && poll.right == null) {
                    return res;
                }
                if (poll.left != null) {
                    queue.offer(poll.left);
                }
                if (poll.right != null) {
                    queue.offer(poll.right);
                }
                size--;
            }

        }

        return res;

    }


    /**
     * todo 平衡二叉树  一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1 。
     *
     * @param root
     * @return
     */
    public boolean isBalanced(TreeNode root) {
        //递归实现
        if (root == null) return true;//??? 为什么null还是true？？
//        int leftH = getHeight(root.left);
//        int rightH = getHeight(root.right);
//
//        return Math.abs(leftH - rightH) <= 1&&isBalanced(root.left)&&isBalanced(root.right);
        return getHeight(root) > 0;
    }

    //返回节点的高度
    private int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }
//        int leftH = getHeight(root.left);
//        int rightH = getHeight(root.right);
//        return Math.max(leftH, rightH) + 1;

        //优化，可以先判断左右子树的高度差是否符合要求
        int leftH = getHeight(root.left);
        int rightH = getHeight(root.right);

        if (Math.abs(leftH - rightH) > 1 || leftH == -1 || rightH == -1) {
            return -1;
        } else {
            return Math.max(leftH, rightH) + 1;
        }

    }


    /**
     * todo 3 数组转平衡的二叉树---构造一颗二叉搜索树？？
     *
     * @param nums
     * @return
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        /*
         *1,遍历数组，以i为根节点，去分段构建。
         */

        return buildBst(0, nums.length - 1, nums);
    }

    private TreeNode buildBst(int start, int end, int[] nums) {
        if (start > end) {
            return null;
        }
        int middle = (end + start) / 2;
        TreeNode root = new TreeNode(nums[middle]);
        TreeNode left = buildBst(start, middle - 1, nums);
        TreeNode rigth = buildBst(middle + 1, end, nums);
        root.left = left;
        root.right = rigth;
        return root;
    }


    /**
     * todo 2 二叉树的最大深度 广度优先？？
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

//        Queue<TreeNode> queue=new LinkedList<>();
//        queue.offer(root);
//        int res=0;
//        while (!queue.isEmpty()){
//            int size = queue.size();
//            //size表示这一层的节点个数
//            while (size>0){
//                TreeNode poll = queue.poll();
//                if (poll.left!=null){
//                    queue.offer(poll.left);
//                }
//                if (poll.right!=null){
//                    queue.offer(poll.right);
//                }
//                size--;
//            }
//            res++;
//        }
//
//        return res;

        /**
         * 深度优先 递归如何实现？
         *
         * 我相信我的字问题：最大的深度。已经解决了，如何求父问题。
         * 也就是说我的左右字节点已经得到了它的最大深度。
         */

        int leftHeight = maxDepth(root.left);
        int rightHeight = maxDepth(root.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }


    /**
     * todo 1 二 叉树对称。
     *
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        //对称，并不是仅仅左右子节点而已。
        //而是左右子树要求也要对称

        return checkSymmetric(root.left, root.right);

    }

    private boolean checkSymmetric(TreeNode left, TreeNode right) {
//        if (left==null&&right==null){
//            return true;
//        }
//
//        if(left==null||right==null){
//            return false;
//        }
//
//        if (left.val!= right.val){
//            return false;
//        }
//        //todo 这里需要注意镜像的判断条件
//        return  checkSymmetric(left.left,right.right)&&checkSymmetric(left.right,right.left);

        // 广度优先


        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(left);
        queue.offer(right);
        while (!queue.isEmpty()) {
            //每次弹出两个
            TreeNode p1 = queue.poll();
            TreeNode p2 = queue.poll();
            //什么条件下判空呢？
            if (p1 == null && p2 == null) {
                // 这里是继续比较，涉及到true时候要真正小心。
                continue;
            }
            if (p1 == null || p2 == null) {
                return false;
            }

            if (p1.val != p2.val) {
                return false;
            }
            queue.offer(p1.left);
            queue.offer(p2.right);
            queue.offer(p1.right);
            queue.offer(p2.left);

        }
        return queue.isEmpty();

    }


    public boolean isSameTree(TreeNode p, TreeNode q) {
        /*
           两棵树相同，肯定是所有节点都要相同。那么我们就需要去比较每个节点的值。
           只能通过遍历。 而遍历树有前中后序遍历？ ？？
           还有广度优先。
         */

        //base case
//        if (p==null&&q==null){
//            return true;
//        }
//        if (p==null||q==null){
//            return false;
//        }
//
//        //1，深度优先，前序遍历
//        if (p.val!=q.val){
//            return false;
//        }
//        return isSameTree(p.left,q.left)&&isSameTree(p.right,q.right);

        //3，广度优先，an层遍历

        /**
         *吧根节点加入队列，然后开始遍历队列。同时再加入左右子树节点
         */
        Queue<TreeNode> qP = new LinkedList<>();
        Queue<TreeNode> qQ = new LinkedList<>();


        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        qP.offer(p);
        qQ.offer(q);
        while (!qP.isEmpty() && !qQ.isEmpty()) {
            TreeNode pollP = qP.poll();
            TreeNode pollQ = qQ.poll();

            if (pollP.val != pollQ.val) {
                return false;
            }
            if (pollP.left == null ^ pollQ.left == null) {
                return false;
            }
            if (pollP.right == null ^ pollQ.right == null) {
                return false;
            }
            qP.offer(pollP.left);
            qP.offer(pollP.right);
            qQ.offer(pollQ.left);
            qQ.offer(pollQ.right);
        }
        return qP.isEmpty() && qQ.isEmpty();
    }

}

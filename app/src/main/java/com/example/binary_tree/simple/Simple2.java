package com.example.binary_tree.simple;

import android.opengl.Matrix;
import android.os.Build;

import com.example.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import androidx.annotation.RequiresApi;

class Simple2 {

    public static void main(String[] asrgs) {
        //判断两颗树是否相同。
        //

    }

    /**
     * todo 左叶子之和
     *
     * @param root
     * @return
     */
    int sum = 0;

    public int sumOfLeftLeaves(TreeNode root) {
        //如何判断左叶子？

        travel(root);
        return sum;
    }

    private void travel(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode left = root.left;
        if (left != null) {
            if (left.left == null && left.right == null) {
                //叶子节点
                sum += left.val;
            }
        }
        travel(root.left);
        travel(root.right);
    }

    /**
     * todo 二叉树的所有路径
     *
     * @param root
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public List<String> binaryTreePaths(TreeNode root) {
        /**
         * 递归实现
         */
//        List<String> res=new ArrayList<>();
//        String str="";
//        findPath(root,res,str);
//
//        return  res;

        /**
         * 广度优先
         */
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        List<String> res = new ArrayList<>();
        String str = "";
        while (!queue.isEmpty()) {
            TreeNode poll = queue.poll();
            str += (str.isEmpty() ? "" : "->") + poll.val;
            if (poll.left == null && poll.right == null) {
                res.add(str);
            }
            if (poll.left != null) {
                queue.offer(poll.left);
            }
            if (poll.right != null) {
                queue.offer(poll.right);
            }
        }

        return res;


    }

    private void findPath(TreeNode root, List<String> res, String str) {
        str += (str.isEmpty() ? "" : "->") + root.val;
        if (root.left == null && root.right == null) {
            res.add(str);
        }
        findPath(root.left, res, str);
        findPath(root.right, res, str);
    }


    /**
     * todo 二叉搜索树的最近祖先
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        /**
         * 1，两次遍历，分别记录达到p、q的路径。然后比较两个数组值大小相等的就是最近祖先。
         */

        /**
         * 2, 一次遍历，递归实现。因为是二叉搜索树，所以只要判断p、q的不在同一个分支上即得到祖先。
         */
//        if (root == null) {
//            return null;
//        }
//        if (p.val < root.val && q.val < root.val) {
//            return lowestCommonAncestor(root.left, p, q);
//        } else if (p.val > root.val && q.val > root.val) {
//            return lowestCommonAncestor(root.right, p, q);
//        } else {
//            return root;
//        }
        /**
         * 3, 一次遍历，非递归，迭代实现
         */
        while ((p.val - root.val) * (q.val - root.val) > 0) {
            root = p.val - root.val > 0 ? root.right : root.left;
        }
        return root;

    }


    /**
     * todo 路径和  遍历
     *
     * @param root
     * @param targetSum
     * @return
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        return getSum(root, targetSum);

    }

    private boolean getSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
//        targetSum -= root.val;
//        if (root.left == null && root.right == null) {
//            if (0 == targetSum) {
//                return true;
//            }
//        }
//        return getSum(root.left, targetSum) || getSum(root.right, targetSum);
        /**
         * 广度优先怎么实现
         */
        Queue<TreeNode> queue = new LinkedList<>();
        Queue<Integer> queueVal = new LinkedList<>();
        queue.offer(root);
        queueVal.offer(root.val);
        while (!queue.isEmpty()) {
            TreeNode poll = queue.poll();
            Integer sum = queueVal.poll();
            if (poll.left == null && poll.right == null) {
                if (targetSum == sum) {
                    return true;
                }
                //为什么要continue呢？因为到这里都不相等，后面肯定不会再相等了。
                continue;
            }

            if (poll.left != null) {
                queue.offer(poll.left);
                queueVal.offer(sum + poll.left.val);
            }
            if (poll.right != null) {
                queue.offer(poll.right);
                queueVal.offer(sum + poll.right.val);
            }

        }

        return false;


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

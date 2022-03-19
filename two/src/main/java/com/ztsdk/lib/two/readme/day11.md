# 二叉树训练(第一期)

很多经典算法都跟二叉树有关， 如： 二分搜索、快速排序就是二叉树的谦虚遍历。 归并排序就是二叉树的后序遍历。 后面要讲的动态规划、回溯算法都跟二叉树有关。

树的问题有规律，永远避不开树的遍历框架：

```
void travel(root){
    //这里是谦虚
    travel(root.left);
    //这里是中序
    travel(root.right);
    //这里是后序遍历
}

快速排序[0,n]，找到中间分界点p，通过交换让[0，p-1]的元素都小鱼nums[p]，让[p+1，n]的元素都大于nums[p]；
在分别对[0，p-1]、[p+1，n]两个区间再次排序。

归并排序[0,n]：找到一个分界点p，然后分别对[0，p-1]、[p，n]两个区间进行排序，拍完后在merge成一个新的数组。
这就是后续遍历。
```

# 写递归算法的秘诀

搞清楚当前节点要做什么(具体代码实现)？以及 什么时候做(也就是前序、中序、后序)？

# 算法实践

## 翻转二叉树

```
分析： 怎么做？ 什么时候做？ 
先对当前节点的左右子节点进行交换，然后分别对左右子节点再次递归。--ok

先对左右子节点递归，然后在对当前节点进行交换。--ok

先对左节点递归交换，然后对当前节点的左右子节点交换，再次对右节点进行交换。次方案不行，会对原来的左子节点交换了两次。
```

## 填充二叉树节点的右侧指针

```
分析： 
一个节点搞不定，那么就分成两个节点。left、right

root connect(root){

    if(root==null) return null;
    
    connect(root.left,root.right);
    
}

void connect(left,right){
    if(left==null||right==null){return;}
    
    left.next=right;
    //连接相同父节点的两个节点
    connect(left.left,left.right);
    connect(right.left,right.right);
    //连接跨越父节点的两个子节点
    connect(left.right,right.left);

}
```

## 将二叉树展开为链表

```
void flatten(TreeNode root){
    
    if(root==null){retrun;}
  
    flatten(left);
    flatten(right);
    
    
    TreeNode temp=root.right;
    root.right=left;
    root.left=null;
    
    //假设两个子树已经拉平，那么只要找到当前最右边的节点即可
    TreeNode p=root;
    while(p.right!=null){
        p=p.next;
    }
    p.right=right;
    

}


```





















































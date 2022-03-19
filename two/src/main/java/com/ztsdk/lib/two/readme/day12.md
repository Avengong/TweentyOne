# 二叉搜索树基础(第二期)

BST的特点： 左小右大 1，对于任意一个节点，它的左子树中的所以元素都比这个节点小。右子树中所以元素都比 这个节点大。 2，RST中任意一个节点的左右子树都是BST。

遍历框架如下：

```
void travel(root,target){
    if(root.val==target){
        //找到了，做些什么
    }else if(root.val>target){
        travel(root.left);
    }else if(root.val<target){
    travel(root.right);
    }
}
```

# 实战

## 判断 BST 的合法性

原理： 要判断任意节点的左子树中所以元素都比当前节点小，

```
boolean isValidBST(TreeNode root) {
    
   return isValidBST(root,min,max);
}
boolean isValidBST(root,min,max){
    if(root==null){ return true;}
    
    if(min!=null&&min.val>=root.val)return false;
    if(max!=null&&max.val<=root.val)return false;
    retrun isValidBST(root.left,min,root)&&isValidBST(root.right,root,max);

}
```

## 在 BST 中搜索一个数

这个简单啊，利用左小右大特性，遍历。比普通遍历要好很多。

```
普通：
boolean isInBST(TreeNode root, int target) {
    if (root == null) return false;
    if (root.val == target) return true;
    // 当前节点没找到就递归地去左右子树寻找
    return isInBST(root.left, target)
        || isInBST(root.right, target);
}

左小右大：
boolean isInBST(TreeNode root, int target) {
    if (root == null) return false;
    if (root.val == target)
        return true;
    if (root.val ‹ target) 
        return isInBST(root.right, target);
    if (root.val › target)
        return isInBST(root.left, target);
    // root 该做的事做完了，顺带把框架也完成了，妙
}

```

## 在 BST 中插入一个数

````
在遍历的框架上该动：

TreeNode insertInBST(TreeNode root, int target) {
    if (root == null) return new Node();
    if (root.val == target)
      
    if (root.val ‹ target) 
        root.right= insertInBST(root.right, target);
    if (root.val › target)
        root.left= insertInBST(root.left, target);
    return root;
}
````

## 在 BST 中删除一个数

原理： 1，如果左右子树都没有，那么直接删除 2，如果只有左右子节点的某一个，那么直接替换。
3，如果两个都有，找到某个值后，需要去右子树中找到最小的值，跟当前节点进行替换，然后删掉右子树中的那个节点。

```
TreeNode deleteNode(TreeNode root, int key) {
     if (root == null) return null;
    if (root.val == target)
        //if(root.left==null&&root.right==null) return null;
        //if(root.left==null&&root.right!=null) return root.right;
        //if(root.left!=null&&root.right==null) return root.left;
        //合并if
        if(root.left==null){return root.right}
        if(root.right==null){return root.left}
        
        TreeNode min=getMin(root.right);
        root.val=min.val;
        root.right=deleteNode(root.right,min.val);
        
    if (root.val ‹ target) 
        root.right= insertInBST(root.right, target);
    if (root.val › target)
        root.left= insertInBST(root.left, target);

    return root;
}

TreeNode getMin(root){
    while(root.left!=null){
        root=root.left;
    }
    return root;
}

```

























































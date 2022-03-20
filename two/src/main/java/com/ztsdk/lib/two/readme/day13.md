# BFS 算法框架套路详解

针对 图Graph 这种非线性的数据结构。 两种算法： 深度优先DFS、广度优先BFS： Breadth-First-Search

原理： 对任意节点，周围的节点进行层层地毯式搜索。

使用场景： 在一幅图中，找出一个起点start到结束点end的距离。

# 算法框架

```
int BFS(Node start, Node target) {
    Queue<Node> q;
    Set<Node> visited;
    int step=0;

    q.offer(start);
    visited.add(start);
    
    while(q.size>0){
       int size= q.size();
       //当前这一层
       for(int k=0;k<size;k++){
            Node cur= q.poll();
            if(cur==target){
           return step;
       }else{
            //获取下一层节点
            for(Node n: cur.adj()){
                if(!visited.contians(n)){
                    q.offer(n);
                    visited.add(n);
                }
            }
        
       }
     }
        step++;
    }
 
}
```

注意： 二叉树由于不会往回走，因此不需要visited数组来去重。

# 实践

## 二叉树的最小高度

## 解开密码锁的最少次数

# 双向 BFS 优化

起点和终点同时开始搜索。 







































# 并查集 (Union-Find) 算法

使用场景： 主要解决图论中的 动态连通性问题。

例子： 0~9 10个数字。我们要实现如下api：

```
class UF{
    
    //p q 相连
    void union(p,q);
    //p q是否相连
    boolean isConnected(p,q);
    //返回联通量
    int getCount();

}
```

# 基本思路

```
class UF{
    int count; //连通量
    int[] parent;
    int[] size;
    
    UF(n){
        count=n;
        //自己指向自己 
        for(int i=0;i<n;i++){
            parent[i]=i;
            size[]i=1;       
        }    
    }
    
    void union(p,q){
        int rootP=find(p);
        int rootQ=find(q);
        if(rootQ==rootP){return;}
        if(size[rootP]>size[rootQ]){
            parent[rootP]=rootQ;
            size[rootP]+=size[rootQ];
        }else{
            parent[rootQ]=rootP;
             size[rootQ]+=size[rootP];
        }
       
        count--;
    
    }
    
    boolean isConnected(p,q){
    
     int rootP=find(p);
        int rootQ=find(q);
        return rootP==rootQ;
    }
    
    int getCount(){
        return count;
    }
    
    int find(x){
        while(x!=parent[x]){
        // 路径压缩
        parent[x]=parent[parent[x]]
            //x=parent[x];
        
        }
    return x;
        
    }
    
}







```
















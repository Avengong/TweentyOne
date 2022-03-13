# 二分查找 应用场景识别

只要是单调递减 或者 单调递增 都可以尝试二分搜索。

抽象出 x 、f(x) 、target三者的关系

# 爱吃香蕉的咳咳

```
珂珂喜欢吃香蕉。这里有 N 堆香蕉，第 i 堆中有 piles[i] 根香蕉。警卫已经离开了，将在 H 小时后回来。

珂珂可以决定她吃香蕉的速度 K （单位：根/小时）。每个小时，她将会选择一堆香蕉，从中吃掉 K 根。如果这堆香蕉少于 K 根，她将吃掉这堆的所有香蕉，然后这一小时内不会再吃更多的香蕉。  

珂珂喜欢慢慢吃，但仍然想在警卫回来前吃掉所有的香蕉。

返回她可以在 H 小时内吃掉所有香蕉的最小速度 K（K 为整数）。

```

确定x： 速度k 跟/小时 确定f(x): 吃完所需的小时数 确定target： 规定的小时 函数原型： public int minEatingSpeed(int[] piles, int h) {

f（x）计算如下：

```
int h=0;
for(int num: piles){
    if(num>k){
    int h1=num/k;
    int h2=num%k;
    h+h1+h2;
    }else{
    h++;
   
    }
    

}

retrun h;
```

随着k的增加，所需时间肯定减少。因此是单调递减函数。求的是左边界。

left 为： 1 right 为： 10^4

# 最小运载力的计算

确定x： 最小的运载能力 确定f(x): 所耗时 确定target： 给定耗时D

总结： 一般采用right=xx.length; 也就是左闭右开区间来计算mid。 边界判定： 找左侧边界： 判断if(left>=xx.length|| f(x)!=target){return
-1} return left; 找右侧边界： 判断if(right<0|| f(x)!=target){return -1} return rigth-1;























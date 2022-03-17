# 单调栈与单调队列

## 单调栈模板

使用场景： 处理在数组中下一个最大值的问题。 原理： 倒着遍历数组，然后压入栈，相当于顺序遍历栈，剔除两个元素之间的中间值，因为没有存在的必要。最后找到比自己大的值。

```
例如：nums = [2,1,2,4,3]，返回数组 [4,2,4,-1,-1]
第1遍： num:3   res:-1  stack: 3
第2遍： num:4   res:-1,-1  stack: 4
第3遍： num:2   res:4,-1,-1  stack: 2,4
第4遍： num:1   res:2？,4,-1,-1  stack: 1,2,4
第5遍： num:2   res:4?,2,4,-1,-1  stack:因为小于等于当前值，所以 pop 1,pop 2,4，最终得到4.

Stack<int> s=new Stack();

for(int i=nums.length;i>=0;i--){
    int num=nums[i];
    while(s.isNotEmpty()&&s.peek()<=num){
        s.poll();
    }
    res[i]=s.isEmpty()?-1:s.peek();
    s.push(num);
    

}
```

## 单调队列技巧 





















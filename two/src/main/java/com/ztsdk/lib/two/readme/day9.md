# 队列实现栈|栈实现队列

底层都是数组或者链表。

## 队列的接口

```
class MyQueue{
    void push(int x);
   int pop();
    int peek();
    boolean isEmpty();
}
```

原理： 利用两个栈来回倒腾。

## 队列实现栈

栈的接口：

```
class MyStack{
    void push(int x);
     int pop();
     int top();
   boolean isEmpty();
}

```

原理： 利用一个队列。push的时候就直接加入队列，同时记录队尾元素作为栈顶元素。 pop的时候，就先把之前的弹出队列在重新加入队里，直到拿到队尾元素，作为栈顶弹出。















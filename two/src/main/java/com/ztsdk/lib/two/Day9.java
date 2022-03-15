package com.ztsdk.lib.two;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

//滑动窗口 双指针技巧
public class Day9 {

    public static void main(String[] args) {


    }

    // 用栈实现队列
    class MyQueue {
        Stack<Integer> s1 = new Stack<>();
        Stack<Integer> s2 = new Stack<>();

        public MyQueue() {

        }

        public void push(int x) {
            s1.push(x);
        }

        public int pop() {
            if (s2.isEmpty()) {
                while (!s1.isEmpty()) {
                    s2.push(s1.pop());
                }
            }
            return s2.pop();
        }

        public int peek() {
            if (s2.isEmpty()) {
                while (!s1.isEmpty()) {
                    s2.push(s1.pop());
                }
            }
            return s2.peek();
        }

        public boolean empty() {
            return s2.isEmpty() && s1.isEmpty();
        }
    }

    //队列实现栈
    class MyStack {

        Queue<Integer> mQueue = new LinkedList<>();
        Integer top = null;

        public MyStack() {

        }

        public void push(int x) {
            mQueue.add(x);
            top = x;
        }

        public int pop() {
            int size = mQueue.size();
            while (size > 1) {
                if (size == 2) {
                    top = mQueue.peek();
                }
                mQueue.offer(mQueue.poll());
                size--;
            }

            return mQueue.poll();
        }

        public int top() {
            return top;
        }

        public boolean empty() {
            return mQueue.isEmpty();
        }
    }

}


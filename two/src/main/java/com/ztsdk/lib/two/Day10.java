package com.ztsdk.lib.two;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

//滑动窗口 双指针技巧
public class Day10 {

    public static void main(String[] args) {


    }

    //    496. 下一个更大元素 I
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {

//        Stack<Integer> s = new Stack<>();
//        int[] res = new int[nums1.length];
//        for (int j = nums1.length-1; j >=0; j--) {
//            int num = nums1[j];
//            int resV = -1;
//            for (int i = nums2.length - 1; i >= 0; i--) {
//                int num2 = nums2[i];
//                if (num == num2) {
//                    while (!s.isEmpty() && s.peek() <= num) {
//                        s.pop();
//                    }
//                    resV = s.isEmpty() ? -1 : s.peek();
//                    break;
//                }
//                s.push(num2);
//            }
//            res[j] = resV;
//
//        }


        Stack<Integer> s = new Stack<>();
        int[] res = new int[nums1.length];
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = nums2.length - 1; i >= 0; i--) {
            int num2 = nums2[i];
            while (!s.isEmpty() && s.peek() <= num2) {
                s.pop();
            }
            map.put(num2, s.isEmpty() ? -1 : s.peek());
            s.push(num2);
        }
        for (int j = nums1.length - 1; j >= 0; j--) {
            int num = nums1[j];
            res[j] = map.get(num);
        }

        return res;

    }

}


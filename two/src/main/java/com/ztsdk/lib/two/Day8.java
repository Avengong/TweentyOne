package com.ztsdk.lib.two;

import android.os.Build;

import java.util.HashMap;

import androidx.annotation.RequiresApi;

//滑动窗口 双指针技巧
public class Day8 {

    public static void main(String[] args) {


    }

    // 环形链表
    public boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;


    }

    //链表的中间结点
    public ListNode middleNode(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;

    }

    //相交链表
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {

        ListNode p1 = headA, p2 = headB;
        while (p1 != p2) {
            if (p1 == null) {
                p1 = headB;
            } else {
                p1 = p1.next;
            }
            if (p2 == null) {
                p2 = headA;
            } else {
                p2 = p2.next;
            }

        }
        return p1;

//        ListNode p1 = headA, p2 = headB;
//        while (p1 != p2) {
//            if (p1 == null) {
//                p1 = headB;
//            } else {
//                p1 = p1.next;
//            }
//            if (p2 == null) {
//                p2 = headA;
//            } else {
//                p2 = p2.next;
//            }
//            if (p1.val == p2.val) {
//                return p1;
//            }
//
//        }
//        return null;

    }

    //删除链表的倒数第 N 个结点
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode listNode = new ListNode(0, head);
        ListNode p1 = head, p2 = listNode;
        //删除倒数第n个节点，相当于查找倒数第n+1个节点
        /*
         [1,2,3,4,5]  2
        我们要找倒数第2个元素,先让快指针走n步： p1=3--下标 2
        让p2指向 1--下标0.
        让p1走完，2步：p1=null--下标x。此时，p2也要走2步：p2=4-下标3
        因此，最好加个头结点。保护不溢出。
         */
        for (int i = 0; i < n; i++) {
            p1 = p1.next;
        }
        while (p1 != null) {
            p1 = p1.next;
            p2 = p2.next;
        }
        System.out.println("p2: " + p2.val);
        p2.next = p2.next.next;
        return listNode.next;


//        ListNode dummy = new ListNode(0, head);
//        ListNode first = head;
//        ListNode second = dummy;
//        for (int i = 0; i < n; ++i) {
//            first = first.next;
//        }
//        while (first != null) {
//            first = first.next;
//            second = second.next;
//        }
//        second.next = second.next.next;
//        ListNode ans = dummy.next;
//        return ans;

    }

    //    142. 环形链表 II
    //给定一个链表的头节点  head ，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
    //
    //如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。 为了表示给定链表中的环，评测系统内部使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 
    // 开始）。如果 pos 是 -1，则在该链表中没有环。注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。
    //
    //不允许修改 链表。
//    public ListNode detectCycle(ListNode head) {
//
//
//    }
}

package com.ztsdk.lib.two;

//双指针：原地修改数组和链表
// 秘诀： 相等就跳过，不等我就覆盖
public class Day1 {


    public static void main(String[] args) {


    }

    // 删除数组中的重复项
    public int removeDuplicates(int[] nums) {

        int slow = 0, fast = 0;
        while (fast < nums.length) {
            if (nums[slow] != nums[fast]) {
                slow++;
                nums[slow] = nums[fast];
            }
            fast++;
        }
        return slow++;
    }


    //删除给定值 元素
    public int removeElement(int[] nums, int val) {
        if (nums.length == 0) return 0;
        int slow = 0, fast = 0;
        while (fast < nums.length) {
            if (val != nums[fast]) {
                nums[slow] = nums[fast];
                slow++;

            }
            fast++;
        }
        return 0;
    }

    // 移动零
    public void moveZeroes(int[] nums) {
        // 相等跳，不等就替换，这里要改成交换
        if (nums.length == 0) return;
        int slow = 0, fast = 0;
        while (fast < nums.length) {
            if (nums[fast] != 0) {
                swap(slow, fast, nums);
                slow++;
            }
            fast++;

        }


    }

    public void swap(int a, int b, int[] nums) {
        int temp = 0;
        temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }


    // 删除链表中的重复元素
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) return null;
        //相等跳， 不等换  [1,1,2,3,3]
        ListNode slow = head, fast = head;
        while (fast != null) {
            if (fast.val != slow.val) {
                slow.next = fast;
                slow = slow.next;
            }
            fast = fast.next;
        }
        slow.next = null;
//        return slow; slow相当于已用而已，head也是引用，只是引用的节点位置不同而已。
        return head;

    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

}

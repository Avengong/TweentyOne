package com.ztsdk.lib.two;

//二分查找技巧
public class Day5 {

    public static void main(String[] args) {


    }

    public int binarySearchOne(int[] nums, int target) {
        int left = 0, right = nums.length - 1;

        while (left <= right) {

            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            }
        }
        return -1;
    }

    //找左侧边界  左闭右开 法
    public int binarySearchLeftSide(int[] nums, int target) {
        int left = 0, right = nums.length;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                right = mid;
            } else if (nums[mid] > target) {
                right = mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            }
        }
        //返回值的理解？
        // [2,3,4] =>1 : left没动，所以left=0，表示0个数小于1，没有找到。
        // [2,2，3,4] =>2 ： left=0，也没动啊，但是表示有这个数啊？ 因此，补救措施为target==nums[left]表示找到了，否则没有。
        //[2,2，3,4] =>4 : left=3, 表示找到了。
        //[2,2，3,4] =>5 : left=4, 超过了length，没有找到
        if (left == nums.length) return -1;
        return target == nums[left] ? left : -1;
    }

    //找左侧边界  左闭右闭 法
    public int binarySearchLeftSideLimit(int[] nums, int target) {
        int left = 0, right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                right = mid - 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            }
        }
        //返回值的理解？
        // [2,3,4] =>1 : left没动，所以left=0，表示0个数小于1，没有找到。
        // [2,2，3,4] =>2 ： left=0，也没动啊，但是表示有这个数啊？ 因此，补救措施为target==nums[left]表示找到了，否则没有。
        //[2,2，3,4] =>4 : left=3, 表示找到了。
        //[2,2，3,4] =>5 : left=4, 超过了length，没有找到

        //终极理解： 如果在右半边，那么left会增加，所以需要判断left越界情况。
        //如果存在左边，left不动，因此只需要判断相等与否。
        if (left >= nums.length || target != nums[left]) return -1; //从左到右，超过整个数组都没找到。
        return left;


    }


    ////找右侧边界  左闭右开 法
    public int binarySearchRightSide(int[] nums, int target) {

        int left = 0, right = nums.length;
        while (left < right) {

            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                left = mid + 1;

            } else if (nums[mid] > target) {
                right = mid;

            } else if (nums[mid] < target) {
                left = mid + 1;
            }

        }
        //错啦？
//        if (left ==0) return -1; //
//        return nums[right] == target ? right : -1;

        //[2,2，3,4,4] =>4 :

        if (right == 0 || nums[right] != target) return -1;
        return right - 1;
    }

    ////找右侧边界  左闭右闭 法

    public int binarySearchRightSideLimit(int[] nums, int target) {

        int left = 0, right = nums.length - 1;
        while (left <= right) {

            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                left = mid + 1;

            } else if (nums[mid] > target) {
                right = mid - 1;

            } else if (nums[mid] < target) {
                left = mid + 1;
            }
        }
        //终极理解： 如果在右半边，那么right不动，所以只需要判断是否有相等即可。
        //如果存在左半边，则right减少，因此，判断<0。越界。
        if (right < 0 || nums[right] != target) return -1;
        return right;
    }


}

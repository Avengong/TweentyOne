package com.ztsdk.lib.two;

import android.os.Build;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Keep;
import androidx.annotation.RequiresApi;

//前缀和技巧： 计算数组区间之内的和
// 想求和，先构造累加
public class Day2 {

    public static void main(String[] args) {


    }

    // 前缀和
    public int subarraySum(int[] nums, int k) {
        int[] preSum = new int[nums.length + 1];
        for (int i = 1; i <= nums.length; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }

        int count = 0;
        for (int m = 0; m < nums.length; m++) {
            for (int n = m; n < nums.length; n++) {
                if (k == rangeSum(m, n, preSum)) {
                    count++;
                }
            }
        }
        return count;

    }

    private int rangeSum(int left, int right, int[] preSum) {
        return preSum[right + 1] - preSum[left];

    }

    //给你一个整数数组 nums 和一个整数 k ，请你统计并返回该数组中和为 k 的连续子数组的个数。
    @RequiresApi(api = Build.VERSION_CODES.N)
    public int subarraySum2(int[] nums, int k) {


        int count = 0;
        int preSum = 0;
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(0, 1);
        for (int i = 0; i < nums.length; i++) {
            preSum += nums[i];

            if (map.containsKey(preSum - k)) {
                count += map.get(preSum - k);
            }

            if (map.containsKey(preSum)) {
                map.put(preSum, map.get(preSum) + 1);
            } else {
                map.put(preSum, 1);
            }
        }
        return count;

    }


    //给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
    //你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
    //你可以按任意顺序返回答案。
    public int[] twoSum(int[] nums, int target) {
// 本质上还是 利用hashmap来存储个数、下标等信息，因为我不需要真正的求出是哪两个数。
        int[] res = new int[2];
        HashMap<Integer, Integer> map = new HashMap<>();
        int index = 0;
        for (int num : nums) {
            if (map.containsKey(target - num)) {
                res[0] = index;
                res[1] = map.get(target - num);
                break;
            }
            map.put(num, index);
            index++;
        }
        return res;
    }


}

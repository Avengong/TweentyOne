package com.example.tweentyone;

class Day18 {

    public static void main(String[] asrgs) {
        int[] test = new int[]{4, 3, 2, 3, 5, 2, 1};
        Day18 day18 = new Day18();
        boolean b = day18.canPartitionKSubsets(test, 4);
        System.out.println("day18, res: " + b);

    }

    public boolean canPartitionKSubsets(int[] nums, int k) {

        if (k > nums.length) return false;
        int sum = 0;
        for (int n : nums) {
            sum += n;
        }
        if (sum % k != 0) return false;
        int target = sum / k;


        //先遍历桶，在遍历数组
        boolean[] used = new boolean[nums.length];

        return backTrack(k, nums, 0, used, target, 0);
    }

    private boolean backTrack(int k, int[] nums, int start, boolean[] used, int target,
                              int bucket) {
        System.out.println("k= " + k);
        if (k == 0) {
            return true;
        }
        if (target == bucket) {
            return backTrack(k - 1, nums, 0, used, target, 0);
        }

        for (int i = start; i < nums.length; i++) {
            int num = nums[i];
            if (used[i]) {
                continue;
            }
            if (bucket + num > target) {
                continue;
            }
            used[i] = true;
            bucket = bucket + num;
            if (backTrack(k, nums, i + 1, used, target, bucket)) {
                return true;
            }
            bucket = bucket - num;
            used[i] = false;

        }
        return false;
    }

}

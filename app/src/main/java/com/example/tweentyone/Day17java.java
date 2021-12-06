package com.example.tweentyone;

import android.util.Log;

import com.example.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class Day17java {
    ArrayList<List<Integer>> objects = new ArrayList<>();

    public static void main(String[] asrgs) {
        int[] test = new int[]{1, 2, 3};
        Day17java day17java = new Day17java();
        day17java.permute(test);
    }

    //全排列
    public List<List<Integer>> permute(int[] nums) {
        LinkedList<Integer> path = new LinkedList<>();

        backtrack(nums, path);
        System.out.println("list: " + Arrays.toString(objects.toArray()));
//        Log.d("aa","list: "+ Arrays.toString(objects.toArray()));
        return objects;

    }

    private void backtrack(int[] nums, LinkedList<Integer> path) {
        if (nums.length == path.size()) {
//            System.out.println("path: "+ Arrays.toString(path.toArray()));
            objects.add(new LinkedList<>(path));
            objects.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            System.out.println("path: " + Arrays.toString(path.toArray()) + ", i: " + i);
            System.out.println("===============");
            System.out.println("list: " + Arrays.toString(objects.toArray()) + ",\n");
            if (!path.contains(num)) {
                path.add(num);
                backtrack(nums, path);
//                path.remove(num); //移除最后一个，而不是当前数字。因为到这里已经是一条完整的路径了：1-2-3，
                //移除的是3，而不是1.
                path.remove(path.size() - 1);
            }
        }
    }

    //N 皇后
//    public List<List<String>> solveNQueens(int n) {
//
//    }


}

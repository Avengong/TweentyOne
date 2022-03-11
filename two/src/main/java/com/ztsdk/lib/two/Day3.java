package com.ztsdk.lib.two;

import android.os.Build;

import java.util.HashMap;

import androidx.annotation.RequiresApi;

//差分数组
public class Day3 {

    public static void main(String[] args) {


    }


    // 拼车问题
    public boolean carPooling(int[][] trips, int capacity) {

        int[] diff = new int[1000];

        for (int i = 0; i < trips.length; i++) {
            int[] cols = trips[i];
            int num = cols[0]; //>1
            int start = cols[1]; //必须要>0
            int end = cols[2];

            diff[start] += num;
            if (end < diff.length) {
                diff[end] -= num;
            }
            //开始反推
        }

        int[] res = result(diff);
        for (int n : res) {
            if (n > capacity) {
                return false;
            }
        }
        return true;

    }


    public int[] result(int[] diff) {
        int[] res = new int[diff.length];
        res[0] = diff[0];
        for (int i = 1; i < diff.length; i++) {
            res[i] = res[i - 1] + diff[i];
        }
        return res;
    }

    //航班系统
    public int[] corpFlightBookings(int[][] bookings, int n) {

        int[] diff = new int[n];

        for (int i = 0; i < bookings.length; i++) {
            int[] cols = bookings[i];
            int start = cols[0];
            int end = cols[1];
            int k = cols[2];

            //因为1 <= firsti <= lasti <= n，所以范围：[start-1,end-1]
            diff[start - 1] += k;
            if (end < diff.length) {
                diff[end] -= k;
            }
            //开始反推
        }
        int[] res = result(diff);
        return res;
    }
}

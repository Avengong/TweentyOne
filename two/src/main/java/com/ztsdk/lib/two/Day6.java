package com.ztsdk.lib.two;

//二分查找技巧
public class Day6 {

    public static void main(String[] args) {


    }


    // 爱吃香蕉的咳咳
    public int minEatingSpeed(int[] piles, int h) {

        if (piles.length == 0) return -1;
        int left = 1, right = 100001;
        while (left < right) {
            int mid = left + (right - left) / 2;
            int hours = fx(piles, mid);
            if (hours == h) {
                right = mid;
            } else if (hours > h) {
                left = mid + 1;
            } else if (hours < h) {
                right = mid;
            }
        }

        return left;
    }

    // 爱吃香蕉的咳咳2  有问题
    public int minEatingSpeed2(int[] piles, int h) {

        if (piles.length == 0) return -1;
        int left = 1, right = 100000;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int hours = fx(piles, mid);
            if (hours == h) {
                right = mid - 1;
            } else if (hours > h) {
                left = mid + 1;
            } else if (hours < h) {
                right = mid - 1;
            }
        }

        if (right < 0 || fx(piles, left) != h) {
            return -1;
        }
        return left;
    }

    private int fx(int[] piles, int k) {
        int hours = 0;
        for (int num : piles) {
            hours += num / k;
            if (num % k > 0) {
                hours++;
            }
        }
        return hours;
    }


    //在 D 天内送达包裹的能力
    public int shipWithinDays(int[] weights, int days) {

        int left = findMax(weights), right = sumV(weights) + 1;

        while (left < right) {
            int mid = left + (right - left) / 2;
            int costDay = fwx(weights, mid);

            if (costDay == days) {
                right = mid;
            } else if (costDay > days) {
                left = mid + 1;
            } else if (costDay < days) {
                right = mid;
            }
        }
        return left;

    }

    private int fwx(int[] weights, int caps) {

//        int day = 0;  这个计算是错误的。
//        int tempCap = cap;
//        for (int w : weights) {
//            if (tempCap >= w) {
//                tempCap -= w;
//            } else {
//                day++;
//                tempCap = cap;
//            }
//        }
//        return day;


        int days = 0;
        for (int i = 0; i < weights.length; ) {
            int cap = caps;
            while (i < weights.length) {
                if (weights[i] <= cap) {
                    cap -= weights[i];
                    i++;
                } else {
                    break;
                }
            }
            days++;

        }
        return days;
    }

    private int sumV(int[] weights) {
        int sum = 0;
        for (int v : weights) {
            sum += v;
        }

        return sum;
    }

    private int findMax(int[] weights) {
        int max = 0;
        for (int v : weights) {
            max = Math.max(max, v);
        }
        return max;
    }

}

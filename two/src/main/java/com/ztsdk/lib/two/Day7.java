package com.ztsdk.lib.two;

import android.os.Build;

import java.util.HashMap;

import androidx.annotation.RequiresApi;

//滑动窗口 双指针技巧
public class Day7 {

    public static void main(String[] args) {


    }

    //无重复字符的最长子串 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。

    /*
    "abcddab"
    abcd
    adcdd

     */

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int lengthOfLongestSubstring(String s) {
        int left = 0, right = 0;
        HashMap<Character, Integer> window = new HashMap<>();
        int len = 0;
        while (right < s.length()) {
//            len=Math.max(len,right-left); //在这里更新不行！
            char c = s.charAt(right);
            right++;
            window.put(c, window.getOrDefault(c, 0) + 1);

            while (window.get(c) == 2) {
                char leftC = s.charAt(left);
                window.put(leftC, window.get(leftC) - 1);
                left++;

            }
            len = Math.max(len, right - left);

        }

        return len;


    }

    //字符串的排列 给你两个字符串 s1 和 s2 ，写一个函数来判断 s2 是否包含 s1 的排列。如果是，返回 true ；否则，返回 false 。
    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean checkInclusion(String s1, String s2) {

        int left = 0, right = 0, count = 0;
        HashMap<Character, Integer> need = new HashMap<>();
        HashMap<Character, Integer> window = new HashMap<>();
        for (int i = 0; i < s1.length(); i++) {
            need.put(s1.charAt(i), need.getOrDefault(s1.charAt(i), 0) + 1);
        }

        while (right < s2.length()) {
            char c = s2.charAt(right);
            right++;
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (need.get(c) == window.get(c)) {
                    count++;
                }
            }
            while (right - left >= s1.length()) {

                if (count == need.size()) {
                    return true;
                }
                char leftC = s2.charAt(left);
                leftC++;
                if (need.containsKey(leftC)) {
                    if (window.get(leftC) == need.get(leftC)) {
                        count--;
                    }
                    window.put(leftC, window.getOrDefault(leftC, 0) - 1);
                }

            }

        }

        return false;


    }


}

package com.ztsdk.lib.two;

//回文串判断技巧
public class Day4 {

    public static void main(String[] args) {


    }

    public String longestPalindrome(String s) {

        String longest = "";
        for (int i = 0; i < s.length(); i++) {

            String str1 = findPalindrome(s, i, i);
            String str2 = findPalindrome(s, i, i + 1);

            String res = str1.length() > str2.length() ? str1 : str2;
            longest = res.length() > longest.length() ? res : longest;
        }
        return longest;

    }

    private String findPalindrome(String s, int left, int right) {
        StringBuilder res = new StringBuilder();
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            res.append(s.charAt(left));
            left--;
            right++;
        }
        return res.toString();

    }
}

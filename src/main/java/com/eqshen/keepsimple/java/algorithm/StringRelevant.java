package com.eqshen.keepsimple.java.algorithm;

/**
 * @Auther: eqshen
 * @Description 字符串相关
 * @Date: 2019/6/13 13:08
 */
public class StringRelevant {
    public static void main(String[] args) {
        System.out.println(longestPalindrome("ababccbcd"));
    }

    /**
     * 5
     * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
     * @param s
     * @return
     * babad
     * acbba
     */
    public static String longestPalindrome(String s) {
        if(s == null || s.length() == 0)return s;
        int pos1 = 0;
        int pos2 = 0;
        for (int i = 0; i < s.length(); i++) {
            //单字符中心
            int len1 = calcLen(s,i,i);
            //双字符对称中心
            int len2 = calcLen(s,i,i+1);
            int len = Math.max(len1,len2);
            if(pos2 - pos1 < len){
                pos1 = i - (len - 1)/2;
                pos2 = i + len/2;
            }
        }
        return s.substring(pos1,pos2+1);
    }

    private static  int calcLen(String s,int left,int right){
        while(left >=0 && right<s.length() && s.charAt(left) == s.charAt(right)){
            left--;
            right++;
        }
        return right - left - 1;
    }
}

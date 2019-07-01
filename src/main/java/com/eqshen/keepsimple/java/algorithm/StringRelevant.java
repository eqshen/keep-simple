package com.eqshen.keepsimple.java.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: eqshen
 * @Description 字符串相关
 * @Date: 2019/6/13 13:08
 */
public class StringRelevant {
    public static void main(String[] args) {
        String str = "ababccbcd";
        String result1 = longestPalindrome(str);
        String result2 = longestPalindrome2(str);
        System.out.println(result1);
        System.out.println(result2);

        System.out.println(isMatch("aabce","a.bce"));
        System.out.println("============================");
        System.out.println(isPalindrome(0));
        System.out.println("============================");
        System.out.println(intToRoman(1994));
        System.out.println(romanToInt("MCMXCIV"));
    }

    /**
     * 5
     * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
     * 中心扩散法
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


    /**
     * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
     * 线性规划方法，基于暴力方法的一种改良
     * @param s
     * @return
     */
    public static String longestPalindrome2(String s){
        int len = s.length();
        if(len == 0) return s;
        //记录之前的状态
        boolean dp[][] = new boolean[len][len];
        int maxLength = 1;
        String longestPalindrome = s.substring(0,1); //substring 是左闭右开的

        //j往左跑，i往右跑
        for (int i = 0; i < len; i++) {
            for (int j = i; j >=0 ; j--) {

                //状态转移方程，条件
                // s[i] == s[j]
                //(i-j<=2 || dp[j+1][i-1]) 是 两种情况的化简
                //     即(s[i] == s[j] && i-j <= 2) ||  (s[i] == s[j] && i-j > 2 && dp[j+1][i-1])
                if(s.charAt(i) == s.charAt(j) && (i-j<=2 || dp[j+1][i-1])){
                    dp[j][i] = true;
                    if(i - j + 1 > maxLength){
                        maxLength = i - j + 1;
                        longestPalindrome = s.substring(j,i+1);
                    }

                }
            }
        }
        return longestPalindrome;
    }

    /**
     * 给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
     * '.' 匹配任意单个字符
     * '*' 匹配零个或多个前面的那一个元素
     * @param text
     * @param pattern
     * @return
     */
    public static boolean isMatch(String text, String pattern) {
        if (pattern.length() == 0) {
            return text.length() == 0;
        }
        boolean preMatch = text.length() != 0
                && (text.charAt(0) == pattern.charAt(0) || pattern.charAt(0) == '.');
        if (pattern.length() >= 2 && pattern.charAt(1) == '*') {
            return isMatch(text, pattern.substring(2)) ||
                    (preMatch && isMatch(text.substring(1), pattern));
        } else {
            return preMatch && isMatch(text.substring(1), pattern.substring(1));
        }
    }


    public static boolean isPalindrome(int x) {
        if(x < 0) return false;
        int oldX = x;
        int newX = 0;
        do{
            int r = x % 10;
            newX = newX*10 + r;
            x = x/10;
        }while(x != 0);

        return oldX == newX;
    }

    /**
     * 12.数字转罗马
     * @param num
     * @return
     */
    public static String intToRoman(int num) {
        StringBuilder sb = new StringBuilder();
        while(num > 0){
            while(num<4&& num>0){
                sb.append("I");
                num--;
                continue;
            }

            if(num/1000 > 0){
                sb.append("M");
                num = num - 1000;
                continue;
            }
            if(num/900 >0){
                sb.append("CM");
                num = num - 900;
                continue;
            }
            if(num / 500 > 0){
                sb.append("D");
                num = num - 500;
                continue;
            }
            if(num/400 >0){
                sb.append("CD");
                num = num - 400;
                continue;
            }
            if(num/100 > 0){
                sb.append("C");
                num = num - 100;
                continue;
            }
            if(num/90 > 0){
                sb.append("XC");
                num = num - 90;
                continue;
            }
            if(num/50 > 0){
                sb.append("L");
                num = num - 50;
                continue;
            }
            if(num/40 > 0){
                sb.append("XL");
                num = num - 40;
                continue;
            }
            if(num/10 > 0){
                sb.append("X");
                num = num - 10;
                continue;
            }
            if(num/9 > 0){
                sb.append("IX");
                num = num - 9;
                continue;
            }
            if(num/5 > 0){
                sb.append("V");
                num = num - 5;
                continue;
            }
            if(num/4 > 0){
                sb.append("IV");
                num = num - 4;
                continue;
            }
        }
        return sb.toString();
    }

    /**
     * 13. 罗马数字转整数
     * @param s
     * @return
     */
    public static int romanToInt(String s) {
        Map<String, Integer> map = new HashMap<>();
        map.put("I", 1);
        map.put("IV", 4);
        map.put("V", 5);
        map.put("IX", 9);
        map.put("X", 10);
        map.put("XL", 40);
        map.put("L", 50);
        map.put("XC", 90);
        map.put("C", 100);
        map.put("CD", 400);
        map.put("D", 500);
        map.put("CM", 900);
        map.put("M", 1000);
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            if(i < s.length() - 1 && map.containsKey(s.substring(i,i+2))){
                val+= map.get(s.substring(i,i+2));
                i++;
            }else{
                val+= map.get(s.substring(i,i+1));
            }
        }
        return val;
    }
}

package com.eqshen.keepsimple.java.algorithm;

/**
 * KMP字符串查找算法实现
 */
public class KMPSearch {

    public static void main(String[] args) {
        System.out.println(search("aabaabaafa","aabaaf"));
    }

    public static int search(String text,String pattern) {

        if(pattern.length() == 0) return 0;

        //先构建next数组实现快速查找
        //时间复杂度 O(text.length + pattern.length)
        final int[] next = getNextArray(pattern);

        //pattern 索引
        int j = 0;

        for (int i = 0; i < text.length(); i++) {

            while (j>0 && text.charAt(i) != pattern.charAt(j)) {
                j = next[j-1];
            }


            if(text.charAt(i) == pattern.charAt(j)) {
              j++;
            }

            //找到目标
            if(j == pattern.length()){
                return i - pattern.length() + 1;
            }
        }
        return -1;
    }

    /**
     * 利用最长相等前后缀构建next数组
     * @param pattern
     * @return
     */
    private static int[] getNextArray(String pattern) {
        int[] next = new int[pattern.length()];
        next[0] = 0;

        //前缀结束位置
        int j = 0;
        //后缀结束位置
        int i = 1;

        for (; i < pattern.length(); i++) {
            //前后缀不匹配的情况,需要持续回退
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1];
            }
            //相等的情况
            if(pattern.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            //记录以i位置为后缀结束位置时，最长相等串的长度
            next[i] = j;
        }
        return next;
    }
}

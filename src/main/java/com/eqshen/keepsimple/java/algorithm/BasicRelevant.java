package com.eqshen.keepsimple.java.algorithm;

import com.eqshen.keepsimple.java.BaseTest;
import org.junit.Test;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: eqshen
 * @Description
 * @Date: 2019/8/7 13:23
 */
public class BasicRelevant extends BaseTest {
    /**
     * 50
     * 实现 pow(x, n) ，即计算 x 的 n 次幂函数。
     * @param x
     * @param n
     * @return
     */
    public double myPow(double x, int n) {
        if(n < 0){
            x = 1/x;
            n *=-1;
        }
        return  n%2 == 0? myPowHelper(x*x,n/2,1):myPowHelper(x*x,n/2,x);
    }

    private double myPowHelper(double x,int n,double remain){
        if(n == 0){
            return remain;
        }else if(n == 1){
            return x * remain;
        }
        return n%2 == 0? myPowHelper(x*x,n/2,remain):myPowHelper(x*x,n/2,x*remain);
    }

    @Test
    public void testMyPow(){
        System.out.println(myPow(7,3));
    }

    @Test
    public void testUniquePath(){
        int res = uniquePaths(51,9);
        System.out.println(res);
    }

    /**
     * 62
     * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
     *
     * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
     *
     * 问总共有多少条不同的路径？
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/unique-paths
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param m
     * @param n
     * @return
     */
    public int uniquePaths(int m, int n) {
        int [][]array = new int[m+1][n+1];
        return uniquePathsHelp(m,n,array);

    }

    private int uniquePathsHelp(int m, int n,int [][]array) {
//        System.out.println(m+" , "+n);
        if(1 == m || 1 == n){
            return 1;
        }else {
            if(array[m][n] > 0){
                return array[m][n];
            }
            array[m-1][n] =  uniquePaths(m-1,n);
            array[m][n-1] =  uniquePaths(m,n-1);
            array[m][n] = array[m-1][n]+array[m][n-1];
            return array[m][n];
        }
    }

    /**
     * 63
     * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
     *
     * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
     *
     * 现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/unique-paths-ii
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param obstacleGrid
     * @return
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        if(obstacleGrid[0][0] == 1){
            return 0;
        }
        obstacleGrid[0][0] = 1;

        for (int i = 1; i < m; i++) {
            obstacleGrid[i][0] = (obstacleGrid[i][0] == 0&&obstacleGrid[i-1][0]==1) ?1:0;
        }

        for (int i = 1; i < n; i++) {
            obstacleGrid[0][i] = (obstacleGrid[0][i]== 0&&obstacleGrid[0][i-1]==1)?1:0;
        }


        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if(obstacleGrid[i][j] == 0){
                    obstacleGrid[i][j] = obstacleGrid[i-1][j] + obstacleGrid[i][j-1];
                }else{
                    obstacleGrid[i][j] = 0;
                }
            }
        }
//        if(obstacleGrid[m-1][n-1] == 1){
//            return 0;
//        }
        return obstacleGrid[m-1][n-1];
    }

    /**
     * 64
     * 给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
     *
     * 说明：每次只能向下或者向右移动一步。
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid){
        int rows = grid.length;
        int cols = grid[0].length;
        for (int i = 0; i < rows ; i++) {
            for (int j = 0; j < cols; j++) {
                if(i == 0 && j == 0) continue;
                else if(i == 0 ){
                    //如果是第一行
                    grid[i][j] = grid[i][j] + grid[i][j -1];
                }else if(j == 0){
                    //如果是第一列
                    grid[i][j] = grid[i][j] + grid[i-1][j];
                }else{
                    //当前路径的最小值等于 当前位置值 + min(上方值，左方值)
                    grid[i][j] = grid[i][j] + Math.min(grid[i][j-1],grid[i-1][j]);
                }
            }
        }
        return grid[rows-1][cols-1];
    }

    /**
     * 72
     * 给定两个单词 word1 和 word2，计算出将 word1 转换成 word2 所使用的最少操作数 。
     *
     * 你可以对一个单词进行如下三种操作：
     *
     * 插入一个字符
     * 删除一个字符
     * 替换一个字符
     * @param word1
     * @param word2
     * @return
     */
    public int minDistance(String word1, String word2){
        //线性规划解法
        int n1 = word1.length();
        int n2 = word2.length();
        //dp[i][j]表示word1的前i个字符转换成word2的前j个字符，所需要的 步数（编辑距离）
        //其中 word2是不变的（模板）
        //为了理解方便，0号位置不用
        int dp[][] = new int[n1 + 1][n2 + 1];

        //第一行
        for (int j = 1; j <= n2; j++) {
            dp[0][j] = dp[0][j-1] + 1;
        }
        //第一列
        for (int i = 1; i <= n1 ; i++) {
            dp[i][0] = dp[i-1][0] + 1;
        }

        //
        for (int i = 1; i <= n1; i++) {
            for (int j = 1; j <= n2 ; j++) {
                if(word1.charAt(i-1) == word2.charAt(j-1)){
                    //如果 i,j两个位置的字符串相等，则编辑距离同 i-1到j-1
                    dp[i][j] = dp[i-1][j-1] ;
                }else{
                    //如果i,j两个位置字符不等，则需要从 删除、增加、替换 中选取一个
                    //总编辑距离最小的进行操作
                    //其中 dp[i-1][j] 表示 删除 （如何理解见补充）
                    //其中 dp[i][j-1] 表示 插入 （如何理解见补充）
                    //其中 dp[i-1][j-1] 表示  替换

                    /**
                     * 补充
                     * 因为题意要求从word1到word2，word是可变的，word2是不可变的。dp[i-1][j] 表示当前word1[i]与word2[j]是不匹配的，那我去找下word1[i-1]与word2[j]匹配的情况，如果该情况下最小，那就删除当前的word1[i]。dp[i][j-1]表示我去看看word1[i]与word2[j-1]的匹配情况，如果匹配的结果是最小的，那就增加一个word1[i+1] == word2[j]就行了。
                     */

                    dp[i][j] = Math.min(Math.min(dp[i-1][j],dp[i][j-1]),dp[i-1][j-1]) + 1;
                }
            }
        }
        return dp[n1][n2];
    }

    @Test
    public void testMinDistance(){
        System.out.println(minDistance("abcd","bcdef"));
    }

    @Test
    public void testMinWindow(){
        System.out.println(minWindow("ask_not_what_your_country_can_do_for_you_ask_what_you_can_do_for_your_country","ask_country"));
    }

    /**
     * 76
     * 给你一个字符串 S、一个字符串 T，请在字符串 S 里面找出：包含 T 所有字母的最小子串。
     * @param s
     * @param t
     * @return
     */
    public String minWindow(String s, String t) {
        //当前各个字母的个数，初始为0
        Map<Character,Integer> curCount = new HashMap<>();
        //目标计数值
        Map<Character,Integer> targetCount = new HashMap<>();

        for (char c : t.toCharArray()) {
            int count = targetCount.getOrDefault(c,0);
            targetCount.put(c,count+1);
        }

        int left = 0;
        int right = 0;
        String res = "";
        while(right < s.length()){
            //window add
            int count = curCount.getOrDefault(s.charAt(right),0);
            curCount.put(s.charAt(right),count + 1);
            right++;

            while(hasContain(curCount,targetCount)&& left<=right){
                // 如果这个窗口的子串更短，则更新 res
                if(calcMapDeepSize(curCount) < res.length() || res.equals("")){
                    res = s.substring(left,right);
                }
                //remove left
                char lch = s.charAt(left);
                if(curCount.getOrDefault(lch,0) > 1){
                    curCount.put(lch,curCount.get(lch) -1);
                }else{
                    curCount.remove(lch);
                }
                left++;
            }
        }
        return res;
    }

    private boolean hasContain(Map<Character,Integer> curCount,Map<Character,Integer> targetCount){
        if(curCount.size() < targetCount.size()) return false;
        for (Map.Entry<Character, Integer> entry : targetCount.entrySet()) {
            int cur = curCount.getOrDefault(entry.getKey(),0);
            if(cur < entry.getValue()){
                return false;
            }
        }
        return true;
    }

    private int calcMapDeepSize(Map<Character,Integer> map){
        if(map == null || map.size() == 0) return 0;
        int res = 0;
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            res += entry.getValue();
        }
        return res;
    }

    @Test
    public void testIsinterLeave(){
        String s1 = "aabcc";
        String s2 = "dbbca";
        String s3 = "aadbbbaccc";
        System.out.println(isInterleave(s1,s2,s3));
        System.out.println(isInterleave2(s1,s2,s3));
    }


    /**
     * 97
     * 给定三个字符串 s1, s2, s3, 验证 s3 是否是由 s1 和 s2 交错组成的。
     * dfs递归的方式，太低效，无法ac
     * @param s1
     * @param s2
     * @param s3
     * @return
     */
    public boolean isInterleave(String s1, String s2, String s3){
        if(s1.length() + s2.length() != s3.length()){
            return false;
        }
        return recurcive(s1,s2,s3,0,0,0);
    }

    private  boolean recurcive(String s1, String s2, String s3,int index1,int index2,int index3){
        System.out.println(index1 + "," + index2 + "," + index3);
        if(index3 >= s3.length()){
            return true;
        }

        char ch3 = s3.charAt(index3);


//        if(ch1 != ch3 && ch2 != ch3){
//            return false;
//        }

        boolean result1 = false;
        boolean result2 = false;
        if(index1 < s1.length()){
            char ch1 = s1.charAt(index1);
            if(ch1 == ch3){
                result1 = recurcive(s1,s2,s3,index1+1,index2,index3+1);
            }
        }
        if(index2 < s2.length()){
            char ch2 = s2.charAt(index2);
            if(ch2 == ch3){
                result2 = recurcive(s1,s2,s3,index1,index2+1,index3+1);
            }
        }
        return result1 || result2;

    }


    public boolean isInterleave2(String s1, String s2, String s3){
        int len1 = s1.length();
        int len2 = s2.length();
        int len3 = s3.length();
        if(len1 + len2 != len3){
            return false;
        }
        char[] char1 = s1.toCharArray();
        char[] char2 = s2.toCharArray();
        char[] char3 = s3.toCharArray();

        boolean dp[][] = new boolean[len1+1][len2+1];
        dp[0][0] = true;
        //初始化
        for (int i = 1; i <= len1; i++) {
            dp[i][0] = dp[i-1][0] && char1[i-1] == char3[i-1];
        }

        for (int i = 1; i <= len2; i++) {
            dp[0][i] = dp[0][i-1] && char2[i-1] == char3[i-1];
        }

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if(char1[i - 1] == char3[i+j-1]){
                    dp[i][j] |= dp[i-1][j];
                }
                if(char2[j-1] == char3[i+j-1]){
                    dp[i][j] |= dp[i][j-1];
                }
            }
        }
        return dp[len1][len2];
    }
}

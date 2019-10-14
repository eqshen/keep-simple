package com.eqshen.keepsimple.java.algorithm;

import com.eqshen.keepsimple.java.BaseTest;
import org.junit.Test;

import java.math.BigInteger;

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

}

package com.eqshen.keepsimple.java.algorithm;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: eqshen
 * @Description
 * @Date: 2019/4/17 13:16
 */
public class ArrayRevelant {
    public static void main(String[] args) {
        int height[] = {1,8,6,2,5,4,8,3,7};
        System.out.println(maxArea2(height));
    }

    /**
     * 给定 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0)。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/container-with-most-water
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param height
     * @return
     */
    public static int maxArea(int[] height) {
        int maxArea = 0;
        for (int i = 0; i < height.length - 1; i++) {
            for (int j = 1; j < height.length; j++) {
                int area = (j - i) * Math.min(height[i],height[j]);
                if(maxArea < area){
                    maxArea = area;
                }
            }
        }
        return maxArea;
    }

    public static int maxArea2(int [] height){
        int left = 0;
        int right = height.length -1;
        int maxArea = 0;
        while(left < right){
            int area =(right - left) * Math.min(height[left],height[right]);
            maxArea = maxArea < area ? area : maxArea;
            if(height[left] < height[right]){
                left++;
            }else{
                right--;
            }
        }
        return maxArea;
    }

}

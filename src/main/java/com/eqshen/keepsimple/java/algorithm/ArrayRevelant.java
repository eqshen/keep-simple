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
        try{
            System.out.println(111111);
            throw new Exception("some error happened");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("error");
        }
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2){
        //首先交换两个数组，保证nums1.length <= nums2.length 恒成立
        int m = nums1.length;
        int n = nums2.length;
        if(m > n){
            int tmp[] = nums1;
            nums1 = nums2;
            nums2 = tmp;

            m = nums1.length;
            n = nums2.length;

        }
        return 0d;
    }
}

package com.eqshen.keepsimple.java.algorithm;

/**
 * @Auther: eqshen
 * @Description 二分查找相关
 * @Date: 2019/4/15 13:10
 */
public class BinarySearchDemo {
    public static void main(String[] args) {
        int array[] = {1,2,5,8,11,11,11,11,67,89};
        System.out.println(binarySearch(array,12));
    }

    /**
     * 二分查找
     * @param array
     * @param target
     * @return
     */
    public static int binarySearch(int array[],int target){
        int left = 0;
        int right = array.length - 1;
        while (left < right){//搜索区间[left,right)
            int mid = left + (right - left) / 2;//防溢出
            if(array[mid] < target){
                left = mid + 1;
            }else{
                right = mid;
            }
        }
        //left 与right 相等
        if(array[left] == target) return left;
        return -1;
    }
}

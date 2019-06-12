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

        int nums1[] = {1,2};
        int nums2[] = {3,4};
        System.out.println(findMedianSortedArrays(nums1, nums2));
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


    /**
     * 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。
     *
     * 请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/median-of-two-sorted-arrays
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * 解题思路 来自：https://leetcode-cn.com/problems/median-of-two-sorted-arrays/solution/shuang-zhi-zhen-by-powcai/
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2){
            int n1 = nums1.length;
            int n2 = nums2.length;

            //保证nums1比nums2小
            if (n1 > n2){
                return findMedianSortedArrays(nums2,nums1);
            }
            //二分查找 nums1的割点
            //如果两个数组合并，中位数应该在的位置,为啥+1?和为奇数的情况除以2结果不对
            int k = (n1 + n2 + 1)/2;
            int left = 0;
            int right = n1;
            while(left < right){
                int m1 = left + (right - left)/2;
                int m2 = k - m1;//关键，nums1的割点和nums2的割点是有关联的
                //如果右1 < 左2，说明割点m1需要继续增大
                if(nums1[m1] < nums2[m2 -1]){
                    left = m1 + 1;
                }else{
                    right = m1;
                }
            }
            //上面是对left进行放缩的，跳出循环后，left指在正确割点
            int p1 = left;
            int p2 = k - left;

            //中位数应该由这两者产生  max(nums1[p1-1],nums2[p2-1]),min(nums1[p1],nums2[p2])
            //考虑p1 = 0 或 p2 = 0的情况, p1,p2越界的问题
            int c1 = Math.max(p1 <= 0 ? Integer.MIN_VALUE : nums1[p1-1],
                    p2 <= 0 ? Integer.MIN_VALUE : nums2[p2-1]);

            int c2 = Math.min(p1>= n1?Integer.MAX_VALUE:nums1[p1],
                    p2 >= n2?Integer.MAX_VALUE:nums2[p2]);
            //如果总长度是奇数，则
            if((n1 + n2) % 2 == 1){
                return c1;
            }
            return (c1 + c2) * 0.5;
    }
}

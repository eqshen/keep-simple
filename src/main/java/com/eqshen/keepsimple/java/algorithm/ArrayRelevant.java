package com.eqshen.keepsimple.java.algorithm;

import com.eqshen.keepsimple.java.BaseTest;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @Auther: eqshen
 * @Description
 * @Date: 2019/4/17 13:16
 */
public class ArrayRelevant extends BaseTest {
    public static void main(String[] args) {
        int height[] = {1,8,6,2,5,4,8,3,7};
        System.out.println(maxAreaPlus(height));
        int nums[] = {-1, 0, 1, 2, -1, -4};
        List<List<Integer>> resList = fourSum(nums,2);
        for (List<Integer> integers : resList) {
            System.out.println(integers);
        }
        System.out.println("============================");
        int nums2[] = {-1,2,1,-4};
        System.out.println(threeSumClosest(nums2,1));
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

    public static int maxAreaPlus(int [] height){
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

    /**
     * 15. 给定一个包含 n 个整数的数组 nums，判断 nums 
     * 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/3sum
     * @param nums
     * @return
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> resultList = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) { //-2是因为右边有双指针扫描

            if(i == 0 || (i >0 && nums[i-1]!= nums[i])){//去重，i-1 和i 相同就跳过了。
                int left = i+1;
                int right = nums.length -1;
                while(left < right){
                    int res = nums[i] + nums[left] + nums[right];
                    if(res == 0){
                        resultList.add(Arrays.asList(nums[i],nums[left],nums[right]));
                        while(left<right && nums[left] == nums[left+1]) left++;
                        while(left<right && nums[right] == nums[right-1])right--;
                        left++;
                        right--;
                    }else if(res > 0){
                        while(left<right && nums[right] == nums[right-1])right--;
                        right--;
                    }else{
                        while(left<right && nums[left] == nums[left+1]) left++;
                        left++;
                    }
                }
            }
        }
        return resultList;
    }

    /**
     * @param nums
     * @param target
     * @return
     */
    public static int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int result = nums[0] + nums[1] + nums[2];
        for (int i = 0; i < nums.length - 2; i++) { //-2是因为右边有双指针扫描

            if(i == 0 || (i >0 && nums[i-1]!= nums[i])){//去重，i-1 和i 相同就跳过了。
                int left = i+1;
                int right = nums.length -1;
                while(left < right){
                    int res = nums[i] + nums[left] + nums[right];
                    int disNew = Math.abs(res - target);
                    int disOld = Math.abs(result - target);
                    result = disNew < disOld ? res:result;
                    if(res == target){
                        return result;
                    }else if(res > target){
                        while(left<right && nums[right] == nums[right-1])right--;
                        right--;
                    }else{
                        while(left<right && nums[left] == nums[left+1]) left++;
                        left++;
                    }
                }
            }
        }
        return result;
    }


    /**
     * 给定一个包含 n 个整数的数组 nums 和一个目标值 target，判断 nums 中是否存在四个元素 a，b，c 和 d ，使得 a + b + c + d 的值与 target 相等？找出所有满足条件且不重复的四元组。
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/4sum
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param nums
     * @param target
     * @return
     */
    public static List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        if(nums.length < 4) return new ArrayList<>();
        Set<List<Integer>> resultSet = new HashSet<>();

        for (int i = 0; i < nums.length -1; i++) {
            for (int j = i + 1; j < nums.length -1; j++) {
                int left = j + 1;
                int right = nums.length - 1;
                while (left < right){
                    int res = nums[i] + nums[j] + nums[left] + nums[right];
                    if(res == target){
                        resultSet.add(Arrays.asList(nums[i],nums[j],nums[left],nums[right]));
                        left++;
                        right--;
                    }else if( res > target){
                        right --;
                    }else{
                        left ++;
                    }
                }
            }
        }
        return new ArrayList<>(resultSet);
    }


    /**
     * 27. 给定一个数组 nums 和一个值 val，你需要原地移除所有数值等于 val 的元素，返回移除后数组的新长度。
     *
     * 不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。
     *
     * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/remove-element
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param nums
     * @param val
     * @return
     */
    public int removeElement(int[] nums, int val) {
        if(nums.length == 0) return 0;
        int left = 0;
        int right = nums.length - 1;

        while (left < right){
            if(nums[right] == val){
                right--;
                continue;
            }

            if(nums[left] != val){
                left++;
                continue;
            }

            //swap
            int tmp = nums[left];
            nums[left] = nums[right];
            nums[right] = tmp;

            left++;
            right--;
        }
        int resultLen = 0;
        for (int num : nums) {
            if(num !=val){
                resultLen++;
            }
        }
        return resultLen;
    }


    /**
     * removeElement 升级版
     * @param nums
     * @param val
     * @return
     */
    public int removeElementPlus(int[] nums, int val){
        if(nums.length == 0){
            return 0;
        }
        int move = 0;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] != val){
                nums[move] = nums[i];
                move++;
            }
        }
        return move;
    }

    @Test
    public void testRemoveElement(){
        int array[] = {3,2,2,3};
        System.out.println(removeElementPlus(array,3));
        for (int item : array) {
            System.out.print("  " + item);
        }
    }


    /**
     * 实现获取下一个排列的函数，算法需要将给定数字序列重新排列成字典序中下一个更大的排列。
     *
     * 如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。
     *
     * 必须原地修改，只允许使用额外常数空间。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/next-permutation
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param nums
     */
    public void nextPermutation(int[] nums) {

        if(nums.length<=1){
            return;
        }
        int i = 0;
        for (i = nums.length-2; i >=0 ; i--) {
            if(nums[i]< nums[i+1]){
                break;
            }
        }

        int j = nums.length -1;
        for (j = nums.length-1; i >=0 ; j--){
            if(nums[j] > nums[i]){
                break;
            }
        }
        //swap i,j
        if(i>=0 && j >=0){
            int tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }

        //翻转i后面的
        int m = i+1;
        int n = nums.length - 1;
        while(m < n){
            int tmp2 = nums[n];
            nums[n] = nums[m];
            nums[m] = tmp2;
            m++;
            n--;
        }

    }

    @Test
    public void testNextPermutation(){
        int nums[] = {2,3,1};
        nextPermutation(nums);
        for (int num : nums) {
            System.out.print(num);
        }
        System.out.println();
    }

    @Test
    public void testSearch(){
        int nums[]={4,5,6,7,8,1,2,3};
        int nums2[]={3,1};
        int target =8;
        System.out.println(search(nums,target));
    }

    /**
     *
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        int len = nums.length;
        if (len == 0) {
            return -1;
        }

        int left = 0;
        int right = len - 1;
        // 注意：这里是等于
        while (left < right) {
            int mid = (left + right) >>> 1;

            if (nums[mid] > nums[right]) {
                // 前有序，包括中间数
                // 6 7 8 9 1 2
                if (nums[left] <= target && target <= nums[mid]) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            } else {
                // 注意：写这个 if 语句的时候，要让分支和上面一样
                // 后有序，包括中间数
                // 6 7 1 2 3 4 5 6
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
        }
        if (nums[left] == target) {
            return left;
        }
        return -1;
    }
}

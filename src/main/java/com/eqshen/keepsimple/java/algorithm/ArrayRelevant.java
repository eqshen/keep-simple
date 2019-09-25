package com.eqshen.keepsimple.java.algorithm;

import com.eqshen.keepsimple.java.BaseTest;
import org.junit.Test;
import org.springframework.test.context.TestPropertySource;

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

    /**
     * 34
     * 给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
     *
     * 你的算法时间复杂度必须是 O(log n) 级别。
     *
     * 如果数组中不存在目标值，返回 [-1, -1]。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/find-first-and-last-position-of-element-in-sorted-array
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param nums
     * @param target
     * @return
     */
    public int[] searchRange(int[] nums, int target) {
        if(nums.length == 0) return new int[]{-1,-1};
        int first = -1,last = -1;
        int left = 0;
        int right = nums.length -1;
        while(left < right){
            int mid = left + (right - left)/2;
            //不断放缩右指针
            if(nums[mid] < target){
                left = mid + 1;
            }else{
                right = mid;
            }
        }
        if(nums[left] == target){
            first = left;
        }

        left = 0;
        right=nums.length-1;
        while(left < right){
            int mid = left + (right - left+1)/2;
            //不断放缩left指针
            if(nums[mid]> target){
                right = mid -1;
            }else{
                left = mid;
            }
        }
        if(nums[right] == target){
            last = right;
        }

        return new int[]{first,last};
    }

    @Test
    public void testSearchRange(){
        int nums[] = {5,7,7,8,8,10};
        int target = 6;
        int result[] = searchRange(nums,target);
        for (int i : result) {
            System.out.print(i+",");
        }
        System.out.println();
    }

    /**
     * 35
     * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
     * 你可以假设数组中无重复元素。
     * @param nums
     * @param target
     * @return
     */
    public int searchInsert(int[] nums, int target) {
        if(nums.length == 0){
            return 0;
        }
        int left = 0;
        int right = nums.length ;
        while (left < right){
            int mid = left + (right - left)/2;
            if(target > nums[mid]){
                left = mid + 1;
            }else{
                right = mid;
            }
        }
        return left;
    }

    @Test
    public void testSearchInsert(){
        int nums[] = {1,3,5,7};
        int target = 6;
        System.out.println(searchInsert(nums,target));
    }

    @Test
    public void testSolveSudoKu(){
        char [][]board = {
                {'5','3','.','.','7','.','.','.','.'},
                {'6','.','.','1','9','5','.','.','.'},
                {'.','9','8','.','.','.','.','6','.'},
                {'8','.','.','.','6','.','.','.','3'},
                {'4','.','.','8','.','3','.','.','1'},
                {'7','.','.','.','2','.','.','.','6'},
                {'.','6','.','.','.','.','2','8','.'},
                {'.','.','.','4','1','9','.','.','5'},
                {'.','.','.','.','8','.','.','7','9'}
        };
        solveSudoku(board);
    }

    /**
     * 37
     * 编写一个程序，通过已填充的空格来解决数独问题。
     *
     * 一个数独的解法需遵循如下规则：
     *
     * 数字 1-9 在每一行只能出现一次。
     * 数字 1-9 在每一列只能出现一次。
     * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
     * 空白格用 '.' 表示。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/sudoku-solver
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param board
     */
    public void solveSudoku(char[][] board){
        boolean rowUsed[][] = new boolean[9][10]; //[x][0]不使用
        boolean colUsed[][] = new boolean[9][10];
        boolean boxUsed[][][] = new boolean[3][3][10];// 3*3个盒子
        //初始化目前已经被填好的位置
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                //i,j位置对应的数字，char转int
                if('.' != board[i][j]){
                    int num = board[i][j] - '0';
                    rowUsed[i][num] = true;
                    colUsed[j][num] = true;
                    boxUsed[i/3][j/3][num] = true;
                }
            }

        }
        //从头开始尝试，开干
        recusiveSolveSudoku(board,rowUsed,colUsed,boxUsed,0,0);
    }


    private boolean recusiveSolveSudoku(char[][]board, boolean[][]rowUsed, boolean[][]colUsed, boolean[][][]boxUsed, int row, int col){
        if(col == board[0].length){
            col = 0;
            row ++;
            if(row == board.length){
                return true;
            }
        }

        if('.' == board[row][col]){
            //尝试填充数字1-9
            for (int i = 1; i <= 9 ; i++) {
                boolean canBeUsed = !rowUsed[row][i] && !colUsed[col][i] && !boxUsed[row/3][col/3][i];
                if(canBeUsed){
                    //标记
                    rowUsed[row][i] = true;
                    colUsed[col][i] = true;
                    boxUsed[row/3][col/3][i] = true;

                    board[row][col] = (char)('0' + i);
                    //递归判断下一个位置
                    if(recusiveSolveSudoku(board,rowUsed,colUsed,boxUsed,row,col + 1)){
                        return true;
                    }
                    //如果下个位置不符合条件，那就回溯咯
                    rowUsed[row][i] = false;
                    colUsed[col][i] = false;
                    boxUsed[row/3][col/3][i] = false;

                    board[row][col] = '.';
                }

            }
        }else{
            return recusiveSolveSudoku(board,rowUsed,colUsed,boxUsed,row,col + 1);
        }
        return false;
    }

    @Test
    public void testCombinationSum(){
        int []candidates = {2,3,6,7};
        int target = 7;
        List<List<Integer>> result = combinationSum(candidates,target);
        System.out.println(result);


        int []candidates2 = {10,1,2,7,6,1,5};
        int target2 = 8;
        List<List<Integer>> result2 = combinationSum2(candidates2,target2);
        System.out.println(result2);
    }

    /**
     * 39
     * 给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
     *
     * candidates 中的数字可以无限制重复被选取。
     *
     * 说明：
     *
     * 所有数字（包括 target）都是正整数。
     * 解集不能包含重复的组合。 
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/combination-sum
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> combinationResult = new ArrayList<>();
        if(candidates.length == 0){
            return combinationResult;
        }
        Arrays.sort(candidates);
        recusiveCombinationSum(combinationResult,candidates,target,0,new Stack<>());
        return combinationResult;
    }

    /**
     * @param combinationResult  最终结果集
     * @param candidates 数组
     * @param curTarget 当前的目标值
     * @param start
     * @param chain
     */
    private void recusiveCombinationSum(List<List<Integer>> combinationResult,int[] candidates,int curTarget, int start,Stack<Integer> chain){
        if( curTarget == 0){
            combinationResult.add(new ArrayList<>(chain));
        }
        for (int i = start; i < candidates.length; i++) {
            if(curTarget - candidates[i] >=0){
                chain.add(candidates[i]);
                recusiveCombinationSum(combinationResult,candidates,curTarget - candidates[i],i,chain);
                //回溯
                chain.pop();
            }
        }
    }


    /**
     * 40
     * 给定一个数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
     *
     * candidates 中的每个数字在每个组合中只能使用一次。
     *
     * 说明：
     *
     * 所有数字（包括目标数）都是正整数。
     * 解集不能包含重复的组合。 
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/combination-sum-ii
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> combinationResult = new ArrayList<>();
        if(candidates.length == 0){
            return combinationResult;
        }
        Arrays.sort(candidates);
        recusiveCombinationSum2(combinationResult,candidates,target,0,new Stack<>());
        return combinationResult;
    }

    private void recusiveCombinationSum2(List<List<Integer>> combinationResult,int[] candidates,int curTarget, int start,Stack<Integer> chain){
        if( curTarget == 0){
            combinationResult.add(new ArrayList<>(chain));
        }
        for (int i = start; i < candidates.length; i++) {
            if(curTarget - candidates[i] >=0 ){
                //去重
                if(i>start && candidates[i-1] == candidates[i]){
                    continue;
                }
                chain.add(candidates[i]);
                recusiveCombinationSum2(combinationResult,candidates,curTarget - candidates[i],i + 1,chain);//i+1 同一元素不可重复使用
                //回溯
                chain.pop();
            }
        }
    }

    /**
     * 41
     * 给定一个未排序的整数数组，找出其中没有出现的最小的正整数。
     *
     * 示例 1:
     *
     * 输入: [1,2,0]
     * 输出: 3
     * 示例 2:
     *
     * 输入: [3,4,-1,1]
     * 输出: 2
     * 示例 3:
     *
     * 输入: [7,8,9,11,12]
     * 输出: 1
     * 说明:
     * 你的算法的时间复杂度应为O(n)，并且只能使用常数级别的空间。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/first-missing-positive
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param nums
     * @return
     */
    public int firstMissingPositive(int[] nums) {
        if(nums.length == 0){
            return 1;
        }

        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            while(num > 0 && num <=nums.length && num != nums[num - 1]){
                int tmp = nums[i];
                nums[i] = nums[num-1];
                nums[num-1] = tmp;

                num = nums[i];
            }
        }


        for (int j = 0; j < nums.length; j++) {
            if(nums[j] != j + 1){
                return j+1;
            }
        }
        return nums.length + 1;
    }


    @Test
    public void testFirstMissingPositive(){
        int []nums = {1,1};
        System.out.println(firstMissingPositive(nums));
    }

    /**
     * 42
     * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
     * @param height
     * @return
     */
    public int trap(int[] height) {
        int sum = 0;
        //最两端的列不用考虑，因为一定不会有水。所以下标从 1 到 length - 2
        for (int i = 1; i < height.length - 1; i++) {
            int max_left = 0;
            //找出左边最高
            for (int j = i - 1; j >= 0; j--) {
                if (height[j] > max_left) {
                    max_left = height[j];
                }
            }
            int max_right = 0;
            //找出右边最高
            for (int j = i + 1; j < height.length; j++) {
                if (height[j] > max_right) {
                    max_right = height[j];
                }
            }
            //找出两端较小的
            int min = Math.min(max_left, max_right);
            //只有较小的一段大于当前列的高度才会有水，其他情况不会有水
            if (min > height[i]) {
                sum = sum + (min - height[i]);
            }
        }
        return sum;
    }

    /**
     * 42
     * 动态规划解法
     * @param height
     * @return
     */
    public int trap2(int[] height){
        int maxLeft[] = new int[height.length];
        int maxRight[] = new int[height.length];
        for (int i = 1; i < height.length ; i++) {
            maxLeft[i] = Math.max(maxLeft[i - 1],height[i - 1]);
        }
        for (int i = height.length - 2; i >= 0 ; i--) {
            maxRight[i] = Math.max(maxRight[i + 1],height[ i+1]);
        }

        int sum = 0;
        for (int i = 1; i <height.length - 1 ; i++) {
            int min = Math.min(maxLeft[i],maxRight[i]);
            if(min > height[i]){
                sum+= min - height[i];
            }
        }
        return sum;
    }

    @Test
    public void testTrap(){
        int[] height = {0,1,0,2,1,0,1,3,2,1,2,1};
        System.out.println(trap2(height));
    }


    /**
     * 43
     * 给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。
     * @param num1
     * @param num2
     * @return
     */
    public String multiply(String num1, String num2) {
        if("0".equals(num1) || "0".equals(num2)){
            return "0";
        }
        int len = num1.length() + num2.length();
        int resultArray[] = new int[len];
        for (int i = num1.length() - 1; i >=0 ; i--) {
            for (int j = num2.length() - 1; j >= 0 ; j--) {
                int n1 = (num1.charAt(i)-48);
                int n2 = (num2.charAt(j)-48);

                resultArray[i+j+1] += n1* n2;
                //进位处理
                if(resultArray[i+j+1] >= 10){
                    resultArray[i+j] +=resultArray[i+j+1]/10;
                    resultArray[i+j+1] = resultArray[i+j+1] % 10;
                }
            }
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (int i = 0; i < len; i++) {
            if(resultArray[i] == 0 && !flag){
                continue;
            }else{
                result.append(resultArray[i]);
                flag = true;
            }

        }
        return result.toString();
    }

    @Test
    public void testMultiply(){
        System.out.println(multiply("10","456" ));
    }

    @Test
    public void testPermute(){
        int[]nums = {1,1,2};
        List<List<Integer>> result = permuteUnique(nums);
        for (List<Integer> integers : result) {
            System.out.println(integers);
        }
    }

    /**
     * 46
     * 给定一个没有重复数字的序列，返回其所有可能的全排列。
     * @param nums
     * @return
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> resultList = new ArrayList<>();
        int []used = new int[nums.length];
        doPermute(nums,used,new ArrayList<>(),resultList);
        return resultList;
    }

    private void doPermute(int nums[],int used[],List<Integer> singleResult,List<List<Integer>> resultList){

        if(singleResult.size() == nums.length){
            resultList.add(new ArrayList<>(singleResult));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if(used[i] == 1){
                continue;
            }
            singleResult.add(nums[i]);
            used[i] = 1;
            doPermute(nums,used,singleResult,resultList);
            used[i] = 0;
            singleResult.remove(singleResult.size()-1);
        }
    }

    /**
     * 47
     * 给定一个可包含重复数字的序列，返回所有不重复的全排列。
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> resultList = new ArrayList<>();
        int []used = new int[nums.length];
        Arrays.sort(nums);
        doPermuteUnique(nums,used,new ArrayList<>(),resultList);
        return resultList;
    }

    private void doPermuteUnique(int nums[],int used[],List<Integer> singleResult,List<List<Integer>> resultList){

        if(singleResult.size() == nums.length){
            resultList.add(new ArrayList<>(singleResult));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if((used[i] == 1) || (i > 0 && nums[i-1] == nums[i] && used[i-1] ==0 )){
                continue;
            }

            singleResult.add(nums[i]);
            used[i] = 1;
            doPermuteUnique(nums,used,singleResult,resultList);
            used[i] = 0;
            singleResult.remove(singleResult.size()-1);
        }
    }


    /**
     * n 皇后问题研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
     * @param n
     * @return
     */
    public List<List<String>> solveNQueens(int n) {
        int[] result = new int [n];
        List<List<String>> results = new ArrayList<>();
        backpackSolveNQueens(results,result,0, n);
        return results;
    }

    /**
     * 51
     * @param results
     * @param rowQueues 第n行棋子是在第result[n]列
     * @param row
     * @param n 皇后总数
     */
    private void backpackSolveNQueens(List<List<String>> results,int[] rowQueues,int row,int n){
        if(row == n){
            List<String> one = new ArrayList<>();
            for (int i = 0; i < rowQueues.length; i++){
                // 一行一个StringBuilder
                StringBuilder str = new StringBuilder();
                for (int column = 0; column < rowQueues.length; column++){
                    if (column == rowQueues[i]){
                        str.append("Q");
                    }else{
                        str.append(".");
                    }
                }
                one.add(str.toString());
            }
            results.add(one);
            return;
        }

        for (int col = 0; col < n; col++) {
            //判断当前列是否符合条件
            boolean isOk = true;
            for (int i = 0; i < row; i++) {
                //不在同列，当前选择的位置row行，col列，已经被i行用过
                //不在同一个主对角线上，主对角线 / ,特征 横纵坐标之和相同
                //不在同一副对角线上，副对角线 \  ,特征 横纵坐标只差相同
                if(col == rowQueues[i] || col + row == rowQueues[i]  + i || col - row == rowQueues[i] - i){
                    isOk = false;
                    break;
                }
            }
            if(isOk){
                rowQueues[row] = col;//
                //递归进入下一行判断
                backpackSolveNQueens(results,rowQueues,row+1,n);
            }
        }
    }

    @Test
    public void testSpiralOrder(){
        /*/int [][]array = {
                {1,2,3,4,5,6},
                {20,21,22,23,24,7},
                {19,32,33,34,25,8},
                {18,31,36,35,26,9},
                {17,30,29,28,27,10},
                {16,15,14,13,12,11},
        };*/
        int array[][]={{2,3,4},{5,6,7},{8,9,10},{11,12,13},{14,15,16}};
        System.out.println(spiralOrder(array));
    }

    /**
     * 54
     * 给定一个包含 m x n 个元素的矩阵（m 行, n 列），请按照顺时针螺旋顺序，返回矩阵中的所有元素。
     * @param matrix
     * @return
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        int row = matrix.length;
        if (row == 0) {
            return result;
        }
        int column = matrix[0].length;

        int size = row * column;
        //当前已经前进的步数
        int currentPosition = 0;
        //当前走到的行index
        int currentRow = 0;
        //当前走到的列index
        int currentColumn = 0;
        //当前走的圈数，right，down，left，up同时为0说明一圈已经走完，round++
        int round = 0;

        int right = column - 1 - round * 2;
        int down = row - 1 - round * 2;
        int left = column - 1 - round * 2;
        int up = row - 2 - round * 2;

        while (currentPosition < size) {
            result.add(matrix[currentRow][currentColumn]);
            //向右前进步数未完，继续向右前进
            if (right > 0) {
                currentColumn++;
                right--;
            } else if (down > 0) {
                //向下前进步数未完，继续向下前进
                currentRow++;
                down--;
            } else if (left > 0) {
                //向左前进步数未完，继续向左前进
                currentColumn--;
                left--;
            } else if (up > 0) {
                //向上前进步数未完，继续向上前进
                currentRow--;
                up--;
                if (up == 0) {
                    //right，down，left，up同时为0,一圈已经走完，round++,并重置下一圈四个方向要走的步数
                    round++;
                    right = column - round * 2;
                    down = row - 1 - round * 2;
                    left = column - 1 - round * 2;
                    up = row - 2 - round * 2;
                }
            }
            currentPosition++;
        }
        return result;
    }


    /**
     * 45
     * 给定一个非负整数数组，你最初位于数组的第一个位置。
     *
     * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
     *
     * 你的目标是使用最少的跳跃次数到达数组的最后一个位置。
     *
     * 思路：从第一个位置开始遍历，计算当前位置所能到达的范围内实际价值最大的那步
     *  如 [2,1,1,3,4]
     *  当前位置 0  则 最大步数 2 ，能选择的位置为 [1,1],但他们的实际价值是  [2,3]  (相对于当前位置0，产生的距离价值)
     *
     * @param nums
     * @return
     */
    public int jump(int[] nums) {
        int cur = 0;
        int jumpCount = 0;
        if(nums.length <= 1){
            return 0;
        }
        while (cur < nums.length){
            int maxStep = nums[cur];
            if(maxStep >= nums.length - 1 - cur && maxStep !=0){
                return ++jumpCount;
            }
            if(maxStep == 0){
                return 0;
            }
            int steps[] = new int[maxStep+1];//0位置不用
            for (int i = 1; i <= maxStep ; i++) {
               steps[i] =nums[cur+i]==0?0:i + nums[cur+i];
            }
            int maxV = steps[1];
            int newCur = cur + 1;
            for (int i = 1; i < steps.length; i++) {
                if(steps[i] > maxV ){
                    maxV = steps[i];
                    newCur = cur + i;
                }
            }
            if(cur != newCur){
                jumpCount++;
                cur = newCur;
            }
        }
        return jumpCount;
    }

    @Test
    public void testJump(){
        int nums[] = {0};
        System.out.println(jump(nums));
    }

    /**
     * 55
     * 给定一个非负整数数组，你最初位于数组的第一个位置。
     *
     * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
     *
     * 判断你是否能够到达最后一个位置。
     * @param nums
     * @return true or false
     */
    public boolean canJump(int[] nums) {
        //贪心算法
        if(nums.length <= 1){
            return true;
        }
        int maxPosition = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if(nums[i] == 0 && maxPosition <= i){
                return false;
            }
            maxPosition = Math.max(maxPosition,i +  nums[i]);
            if(maxPosition >= nums.length - 1){
                return true;
            }
        }
        return false;
    }

    @Test
    public void testCanJump(){
        int nums[] = {3,2,1,0,4};
        System.out.println(canJump(nums));
    }


    /**
     * 56
     * 给出一个区间的集合，请合并所有重叠的区间。
     *
     * 示例 1:
     *
     * 输入: [[1,3],[2,6],[8,10],[15,18]]
     * 输出: [[1,6],[8,10],[15,18]]
     * 解释: 区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
     * 示例 2:
     *
     * 输入: [[1,4],[4,5]]
     * 输出: [[1,5]]
     * 解释: 区间 [1,4] 和 [4,5] 可被视为重叠区间。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/merge-intervals
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param intervals
     * @return
     */
    public int[][] merge(int[][] intervals) {
        int n = intervals.length;
        if(n<=1){
            return intervals;
        }
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });

        List<int[]> res = new ArrayList<>();
        int []preArray = intervals[0];
        res.add(preArray);
        for (int i = 1; i < n; i++) {
            int []curArray = intervals[i];
            if(curArray[0] <= preArray[1]){
                int tmp[] = new int[]{preArray[0],Math.max(preArray[1],curArray[1])};
                if(res.size() > 0){
                    res.remove(res.size() -1);
                }
                res.add(tmp);
                preArray = tmp;
            }else{
                res.add(curArray);
                preArray = curArray;
            }

        }
        return res.toArray(new int[0][]);
    }

    @Test
    public void testMerge(){
        int array[][]= new int[][]{{1,4},{5,6}};
        for (int[] ints : merge(array)) {
            for (int anInt : ints) {
                System.out.print(anInt+",");
            }
            System.out.println();
        }
    }

    /**
     * 57
     * 给出一个无重叠的 ，按照区间起始端点排序的区间列表。
     *
     * 在列表中插入一个新的区间，你需要确保列表中的区间仍然有序且不重叠（如果有必要的话，可以合并区间）。
     * @param intervals
     * @param newInterval
     * @return
     */
    public int[][] insert(int[][] intervals, int[] newInterval) {
        int n = intervals.length;
        List<int[]> res = new LinkedList<>();
        if(n == 0 || intervals[0].length == 0){
            if(newInterval.length == 0){
                return intervals;
            }
            if(newInterval.length >0){
                res.add(newInterval);
            }
            return res.toArray(new int[0][0]);
        }
        if(newInterval.length == 0){
            return intervals;
        }

        int start = newInterval[0];
        int end = newInterval[1];


        int tmpStart = intervals[0][0];
        int tmpEnd = intervals[0][1];
        boolean flag = false;
        boolean everFlag = false;
        for (int[] interval : intervals) {
            if(interval[1] < start || interval[0] > end){
                if(flag){
                    res.add(new int[]{tmpStart,tmpEnd});
                    flag = false;
                }
                res.add(interval);
            }else{
                //有重合部分
                if(!flag){
                   tmpStart = interval[0];
                    tmpEnd = interval[1];
                }
                tmpStart = Math.min(tmpStart,start);
                tmpStart = Math.min(tmpStart,interval[0]);
                tmpEnd = Math.max(tmpEnd,end);
                tmpEnd = Math.max(tmpEnd,interval[1]);
                flag = true;
                everFlag = true;
            }
        }
        if(flag){
            res.add(new int[]{tmpStart,tmpEnd});
        }
        if(!everFlag){
            int index = 0;
            for (int[] re : res) {
                if(re[0] < newInterval[0]){
                    index++;
                }
            }
            res.add(index,newInterval);
        }
        return res.toArray(new int[0][0]);
    }

    @Test
    public void testInsert(){
        int[][] intervals = new int[][]{{1,3},{6,9}};
        int[] newInterval = new int[]{2,5};
        for (int[] ints : insert(intervals,newInterval)) {
            for (int anInt : ints) {
                System.out.print(anInt+",");
            }
            System.out.println();
        }
    }

    /**
     * 59
     * 给定一个正整数 n，生成一个包含 1 到 n2 所有元素，且元素按顺时针顺序螺旋排列的正方形矩阵
     * 输入: 3
     * 输出:
     * [
     *  [ 1, 2, 3 ],
     *  [ 8, 9, 4 ],
     *  [ 7, 6, 5 ]
     * ]
     * @param n
     * @return
     */
    public int[][] generateMatrix(int n) {
        int matrix[][] = new int[n][n];

        int left = 0,right = n - 1;
        int num = 1;
        while(left <= right){
            // 最中间的一个
            if(left==right) matrix[left][right] = num++;
            // 最上面一行 除去最后一个
            for(int i=left;i<right;i++) matrix[left][i] = num++;
            // 最右边一列 除去最后一个
            for(int i=left;i<right;i++) matrix[i][right] = num++;
            // 最下面一行 除去最后一个（逆序）
            for(int i=right;i>left;i--) matrix[right][i] = num++;
            // 最左边一列 除去最后一个（逆序）
            for(int i=right;i>left;i--) matrix[i][left] = num++;
            // 一圈遍历结束 遍历下一圈
            left++;
            right--;
        }
        return matrix;
    }
}

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

    @Test
    public void testTrap(){
        int[] height = {0,1,0,2,1,0,1,3,2,1,2,1};
        System.out.println(trap(height));
    }

}

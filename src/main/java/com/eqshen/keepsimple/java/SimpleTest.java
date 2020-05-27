package com.eqshen.keepsimple.java;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class SimpleTest {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> list = new ArrayList<>();
        list.addAll(Arrays.asList(1,2,3,4,2,3,4,6,8,9));
        list.stream().distinct()
                .limit(5)
                .forEach(System.out::println);
        //
        List<String> strList = Arrays.asList("tom","hendy","james");
        strList.stream().map(String::length)
                .forEach(System.out::print);
        System.out.println("==========");
        System.out.println("==========");
        strList.stream()
                .flatMap(word -> Arrays.stream(word.split("")))
                .distinct().forEach(System.out::print);
        System.out.println("==========");

        System.out.println("sum1:" + list.stream().reduce(0, (a, b) -> a + b));

    }

    public static int[] removeDuplicates(int[] nums) {
        int len = nums.length;
        int up = 0;
        for (int i = len-1; i >=0 ; i--) {
            int n = nums[i];
            if(i== len-1){
                nums[i] = (n + 1) % 10;
                up = (n+1)/10;
            }else{
                nums[i] = (n + up) %10;
                up = (n+up)/10;
            }
        }
        if(up == 0){
            return nums;
        }else {
            List<Integer> res = Arrays.stream(nums).boxed().collect(Collectors.toList());
            res.add(0,up);
            return res.stream().mapToInt(Integer::intValue).toArray();
        }
    }

//    public static void main(String[] args) {
//        int []nums = {1,5,8,11,24,1235};
//        System.out.println(binarySearch(nums,24));
//        System.out.println("===============");
//        int nums2[]= {4,2,1,8,10,5};
//        fastSort(nums2);
//        for (int i : nums2) {
//            System.out.print(i+", ");
//        }
//        System.out.println();
//        System.out.println("===============");
//        int[] nums3 = {1,2,3,3};
//        List<List<Integer>> res = permute(nums3);
//        System.out.println("++++>"+ res.size());
//        for (List<Integer> re : res) {
//            for (Integer integer : re) {
//                System.out.print(integer+",");
//            }
//            System.out.println();
//        }
//    }

    public static int binarySearch(int[]nums,int target){
        if(nums.length == 0) return -1;
        int left = 0;
        int right = nums.length-1;
        while (left<right){
            int mid = left + (right-left)/2;
            if(nums[mid] < target){
                left = mid+1;
            }else{
                right = mid;
            }
        }
        return nums[left] == target?left:-1;
    }

    public static void fastSort(int []nums){
        if(nums.length <= 1) return;
        fastSortHelp(nums,0,nums.length-1);
    }

    public static void fastSortHelp(int []nums,int start,int end){
        if(start >= end) return;
        int target = nums[start];
        int left = start;
        int right = end;
        while (start < end){
            if(nums[start]< target){
                start++;
                continue;
            }
            if(nums[end] > target){
                end--;
                continue;
            }
            //swap
            int tmp = nums[start];
            nums[start] = nums[end];
            nums[end] = tmp;
        }

        fastSortHelp(nums,left,start-1);
        fastSortHelp(nums,start+1,right);
    }

    /**
     * 全排列
     * @param nums
     * @return
     */
    public static List<List<Integer>> permute(int[] nums) {
        Map<Integer,Integer> countMap = new HashMap<>();
        for (int num : nums) {
            countMap.put(num,countMap.getOrDefault(num,0)+1);
        }

        List<Integer> curList = new ArrayList<>();
        List<List<Integer>> resLIst = new LinkedList<>();
        permuteHelp(nums,countMap,curList,resLIst);
        return resLIst;
    }

    public static void permuteHelp(int[] nums,Map<Integer,Integer> used,List<Integer> curList,List<List<Integer>>resList){
        if(curList.size() == nums.length){
            resList.add(new ArrayList<>(curList));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            int curCount = used.get(nums[i]);
            if(curCount == 0){
                continue;
            }
            used.put(nums[i],curCount-1);
            curList.add(nums[i]);
            permuteHelp(nums,used,curList,resList);
            used.put(nums[i],curCount);
            curList.remove(curList.size()-1);
        }
    }
}

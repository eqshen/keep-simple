package com.eqshen.keepsimple.java;

import java.util.*;

public class Solution {
    public int findKthLargest(Integer nums[],int k){
        PriorityQueue<Integer> queue = new PriorityQueue<>((o1, o2) -> o1 - o2);

        for (int num : nums) {
           queue.add(num);
           if(queue.size() > k){
               queue.poll();
           }
        }
        return queue.poll();
    }

    public List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer,Integer> map = new HashMap<>();
        for (int num : nums) {
           map.put(num,map.getOrDefault(num,0)+1);
        }
        Integer[] values = map.values().toArray(new Integer[]{});
        List<Integer> buckets[] = new ArrayList[nums.length+1];

        for (int i = 0; i < nums.length; i++) {
            int fre = map.get(nums[i]);
            List<Integer> list = buckets[fre];
            if(list == null){
                list = new ArrayList<>();
                buckets[fre] = list;
            }
            list.add(nums[i]);
        }


        Set<Integer> result = new HashSet<>();
        int len = buckets.length;
        int j = k;
        for (int i = len -1; i >= 0 ; i--) {
            if(buckets[i] == null || j <= 0){
                continue;
            }
            for (Integer integer : buckets[i]) {
                if(j<=0){
                    break;
                }
                int sizeBefore = result.size();
                result.add(integer);
                j-=(result.size() - sizeBefore);
            }
        }
        return new ArrayList<>(result);
    }



    public static void main(String[] args) {
        int[] nums = {1,1,1,2,2,3};
        Solution solution = new Solution();
        System.out.println(solution.topKFrequent(nums,2));
    }

    public int findKthLargest2(int nums[],int k){
        int kIndex = nums.length - k;
        int left = 0;
        int right = nums.length - 1;
        while (left <= right){
            int index = partion(nums,left,right);
            if(index == kIndex){
              return nums[index];
            }else if(index < kIndex){
                left = index +1;
            }else{
                right = index -1;
            }
        }
        return 0;
    }


    public int partion(int nums[],int left,int right){
        int pivot = nums[left];
        int j = left;//始终指向小于pivot的最大index
        for (int i = left+1; i <=right ; i++) {
            if(nums[i] < pivot){
                swap(nums,i,++j);
            }
        }
        //
        swap(nums,left,j);
        return j;
    }


    private void swap(int[] nums, int index1, int index2) {
        int temp = nums[index1];
        nums[index1] = nums[index2];
        nums[index2] = temp;
    }

}

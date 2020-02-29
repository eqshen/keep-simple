package com.eqshen.keepsimple.java.algorithm;

import com.eqshen.keepsimple.java.BaseTest;
import com.eqshen.keepsimple.java.Solution;

import java.util.*;

/**
 * @Auther: eqshen
 * @Description 排序，堆等
 * @Date: 2020/2/28 23:15
 */
public class SortRelevant {
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

    /**
     * 451
     * @param s
     * @return
     */
    public String frequencySort(String s){
        char[] chars = s.toCharArray();
        //1.先统计每个字符的出现次数
        Map<Character,Integer> countMap = new HashMap<>();
        for (char aChar : chars) {
            int count = countMap.getOrDefault(aChar,0);
            countMap.put(aChar,count+1);
        }
        //2.定义s.length+1个桶，第i个桶里装着出现i次的元素；
        List<Character> buckets[] = new LinkedList[s.length()+1];
        countMap.forEach((k,v)->{
            List<Character> list = buckets[v];
            if(list == null){
                list = new LinkedList<>();
            }
            list.add(k);
            buckets[v] = list;
        });
        //3.从后往前遍历桶，得到的是就频率出现从高到低的；
        List<Character> list = null;
        StringBuilder resultBuiler = new StringBuilder();
        for (int i = buckets.length-1; i >0; i--) {
            list = buckets[i];
            //如果没有出现 i 次的元素则桶的i位置是null;
            if(list == null) continue;
            for (Character character : list) {
                for (int j = 0; j < i; j++) {
                    resultBuiler.append(character);
                }
            }
        }
        return resultBuiler.toString();
    }

    public static void main(String[] args) {

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

    /**
     * 75
     * @param nums
     */
    public void sortColors(int[] nums) {
        int zero = -1;
        int two = nums.length;
        int one = 0;


        while (one < two){
            if(nums[one] == 0){
                swap(nums,one++,++zero);
            }else if(nums[one] == 2){
                swap(nums,--two,one);
            }else{
                one++;
            }
        }

    }


    //====================

    private void swap(int[] nums, int index1, int index2) {
        int temp = nums[index1];
        nums[index1] = nums[index2];
        nums[index2] = temp;
    }

}

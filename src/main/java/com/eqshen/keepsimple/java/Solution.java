package com.eqshen.keepsimple.java;

import lombok.SneakyThrows;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Solution {

    /**
     * age desc
     */
    private static volatile int age = 1;

    public static void main(String[] args) {

        String s1 = "abc";
        String s2 = "kkk";
        String s3 = "abckkk";
        String s4 = "abc" + s2;
        String s5 = "abc" + "kkk";
        System.out.println(s3 == s4);
        System.out.println(s3 == s5);

        List<Integer> list = new ArrayList<>();
        list.add(null);
    }

    public static int bsLeft(int[] array,int target){
        int left = 0;
        int right = array.length-1;
        //[left,right]
        while (left < right){
            int mid = left + (right - left)/2;
            if(array[mid] < target){
                left = mid + 1;
            }else if(array[mid] > target){
                right = mid - 1;
            } else {
                //继续收缩右侧
                right = mid;
            }
        }
        //结束条件： left = right
        return array[left] == target?left:-1;
    }
}

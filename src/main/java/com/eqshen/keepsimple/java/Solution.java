package com.eqshen.keepsimple.java;

import lombok.SneakyThrows;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Solution {

    private static volatile int age = 1;

    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();

        //=====
        int[] array = {1,2,4,6,6,6,7,8};
        System.out.println(bsLeft(array,6));
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

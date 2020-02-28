package com.eqshen.keepsimple.java;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
}

package com.eqshen.keepsimple.java;

import lombok.SneakyThrows;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Solution {
    public static void main(String[] args) throws InterruptedException {
        Object list = new ArrayList<>(Arrays.asList("1","2","3","4","5","6"));
        System.out.println(list instanceof List);
        HashMap<String,String> map = new HashMap<>();
        map.put("name","111");

        ConcurrentHashMap<String,String> cmap = new ConcurrentHashMap<>();
        cmap.put("end","111");
        cmap.get("end");


        Thread thread = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                int count = 0;
                while (true) {
                    System.out.println(count);
                    Thread.sleep(500);
                    if (++count > 10) {
                        Thread.yield();
                    }
                    System.out.println(System.currentTimeMillis());
                }
            }
        });
        thread.start();
        thread.join();
        System.out.println("-------");
        System.out.println("end");
    }


}

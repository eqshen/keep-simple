package com.eqshen.keepsimple.java.algorithm;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache {
    private Map<Integer,Integer> map;
    private int capacity;
    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new LinkedHashMap(capacity,0.75f,true){
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > capacity;
            }
        };
    }

    public int get(int key) {
        Integer res =  map.get(key);
        return res == null?-1:res;
    }

    public void put(int key, int value) {
        map.put(key,value);
    }
}

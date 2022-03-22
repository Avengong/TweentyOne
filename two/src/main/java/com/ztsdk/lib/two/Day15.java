package com.ztsdk.lib.two;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

//滑动窗口 双指针技巧
public class Day15 {

    public static void main(String[] args) {


    }


    //146. LRU 缓存
    class LRUCache {
        LinkedHashMap<Integer, Integer> cache;
        int cap;

        public LRUCache(int capacity) {
            cache = new LinkedHashMap<Integer, Integer>(capacity);
            cap = capacity;
        }

        public int get(int key) {
            if (!cache.containsKey(key)) {
                return -1;
            }
            Integer value = cache.get(key);
            cache.remove(key);
            cache.put(key, value);
            return cache.get(key);
        }

        public void put(int key, int value) {
            if (cache.containsKey(key)) {
                cache.remove(key);
                cache.put(key, value);
                return;
            }
            if (cache.size() >= cap) {
                cache.remove(cache.keySet().iterator().next());
            }
            cache.put(key, value);
        }
    }
}




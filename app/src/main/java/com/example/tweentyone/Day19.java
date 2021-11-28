package com.example.tweentyone;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class Day19 {

    public static void main(String[] asrgs) {
        int[] test = new int[]{4, 3, 2, 3, 5, 2, 1};
        HashMap<String, String> map = new HashMap<>();
        Set<Map.Entry<String, String>> entries =
                map.entrySet();
        Set<String> keys = map.keySet();
        Collection<String> values = map.values();


        Iterator<Map.Entry<String, String>> iterator1 = map.entrySet().iterator();
        Iterator<String> iterator = map.keySet().iterator();
        Iterator<String> iterator2 = map.values().iterator();

    }


    public void test() {
        //16--16
        //3? 理论上应该是>=3的最小的2的幂： 就是4啦？ 是的！！！
        // 5-->8？？ 是的！！
        HashMap<String, String> stringStringHashMap = new HashMap<>(5);
    }
}

package com.talesdev.copsandcrims;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Test {

    public static void main(String[] args) {
        boolean useArrayList = true;

        List<String> list = useArrayList ? new ArrayList<String>() : new LinkedList<String>();
        for (int i = 0; i < 100000; i++) {
            list.add(UUID.randomUUID().toString());
        }

        int sum = 0;

        long start = System.currentTimeMillis();
        Iterator<String> iter = list.iterator();
        while (iter.hasNext()) {
            String s = iter.next();
            sum += s.length();
        }
        System.out.println(System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        for (String s : list) {
            sum += s.length();
        }
        System.out.println(System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            sum += s.length();
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}
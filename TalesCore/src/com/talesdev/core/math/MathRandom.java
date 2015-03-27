package com.talesdev.core.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Set of random number
 *
 * @author MoKunz
 */
public class MathRandom {
    public static boolean randomPercent(double percent) {
        double random = Math.random() * 100;
        return random <= percent;
    }

    public static int randomRange(Range range) {
        Random random = new Random();
        return range.getStart() + random.nextInt((range.getEnd() - range.getStart()) + 1);
    }

    public static int randomRange(int start, int end) {
        return randomRange(new Range(start, end));
    }

    public static int randomRange(int range) {
        return randomRange(new Range(0, range));
    }

    public static List<Integer> randomNumberList(int min, int max, int size) {
        List<Integer> rangedNumberList = new ArrayList<>();
        for (int i = min; i <= max; i++) {
            rangedNumberList.add(i);
        }
        List<Integer> randomNumberList = new ArrayList<>();
        while (randomNumberList.size() < size) {
            int index = randomRange(rangedNumberList.size() - 1);
            int e = rangedNumberList.get(index);
            randomNumberList.add(e);
        }
        return randomNumberList;
    }
    public static List<Integer> randomNumberList(Range range,int size){
        return randomNumberList(range.getStart(),range.getEnd(),size);
    }
}

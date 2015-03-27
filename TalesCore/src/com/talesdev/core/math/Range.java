package com.talesdev.core.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represent a range of number
 *
 * @author MoKunz
 */
public class Range {
    private int start;
    private int end;
    private List<Integer> filteredNumber;

    public Range(int start, int end) {
        this.start = start;
        this.end = end;
        this.filteredNumber = new ArrayList<>();
    }

    public Range filter(Integer... filteredNumber) {
        for (int i : filteredNumber) {
            if (!this.filteredNumber.contains(i)) this.filteredNumber.add(i);
        }
        return this;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int[] asArray() {
        List<Integer> numberList = asList();
        int[] numberArray = new int[numberList.size()];
        for (int i = 0; i < numberList.size(); i++) {
            numberArray[i] = numberList.get(i);
        }
        return numberArray;
    }

    public List<Integer> asList() {
        List<Integer> numberList = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            if (!filteredNumber.contains(i)) numberList.add(i);
        }
        return numberList;
    }
}

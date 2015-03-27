package com.talesdev.core.math;

import junit.framework.TestCase;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class RangeTest {

    @Test
    public void testFilter() throws Exception {
        Range range = new Range(-100,100);
        List<Integer> exceptionList = new ArrayList<>();
        exceptionList.add(1); exceptionList.add(2);
        exceptionList.add(5); exceptionList.add(9);
        exceptionList.add(-1); exceptionList.add(-2);
        exceptionList.add(-5); exceptionList.add(-9);
        for(int number : range.filter(1,2,5,9,-1,-2,-5,-9).asList()){
            if(exceptionList.contains(number)){
                fail("Range mustn't contains" + number);
            }
        }
    }
}
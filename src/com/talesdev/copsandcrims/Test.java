package com.talesdev.copsandcrims;

import com.talesdev.core.math.DigitalTimer;

/**
 * Main class for testing algorithm
 *
 * @author MoKunz
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        DigitalTimer timer = new DigitalTimer(90);
        for (int i = 0; i < 90; i++) {
            timer.oneSecond();
            System.out.println(timer.digitalTime());
            Thread.sleep(1000);
        }
    }
}
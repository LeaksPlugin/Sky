package com.talesdev.core.math;

/**
 * Time counter
 *
 * @author MoKunz
 */
public class DigitalTime {
    private int time;
    private boolean timesUp = false;

    public DigitalTime(int time) {
        this.time = time;
    }

    public void oneSecond() {
        time--;
        if (time <= 0) {
            timesUp = true;
        }
    }

    public String digitalTime() {
        String zero = "0";
        int fistDigit = (int) (time / 60D);
        int secondsDigit = time % 60;
        return (fistDigit < 10 ? (zero + fistDigit) : fistDigit) + ":" + (secondsDigit < 10 ? (zero + secondsDigit) : secondsDigit);
    }

    public boolean timesUp() {
        return timesUp;
    }
}

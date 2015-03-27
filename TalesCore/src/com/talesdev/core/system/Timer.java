package com.talesdev.core.system;

/**
 * Timer for profiling code performance
 * @author MoKunz
 */
public class Timer {
    private long startTime;
    private long lastTime;
    public void start(){
        this.startTime = get();
        this.lastTime = startTime;
    }
    public long getStartTime(){
        return startTime;
    }
    public long get(){
        return System.nanoTime();
    }
    public void record(){
        this.lastTime = get();
    }
    public long getRecordedTime(){
        return lastTime;
    }
    public long getDeltaTime(){
        return getRecordedTime() - getStartTime();
    }
    public double getMS(){
        return get()/1000000D;
    }
    public double getDeltaMS(){
        return getDeltaTime()/1000000D;
    }
}

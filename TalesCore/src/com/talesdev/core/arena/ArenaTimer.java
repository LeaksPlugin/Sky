package com.talesdev.core.arena;

import com.talesdev.core.math.DigitalTime;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Arena timer
 *
 * @author MoKunz
 */
public class ArenaTimer extends BukkitRunnable {
    private GameArena gameArena;
    private volatile boolean taskStarted;
    private AtomicInteger atomicInteger;
    private boolean increment = false;
    private volatile boolean pause = false;
    private Runnable update;
    private Runnable stop;
    private int initialTime = 300;

    public ArenaTimer(GameArena gameArena, int initialTime, boolean increment) {
        this.gameArena = gameArena;
        this.taskStarted = false;
        this.increment = increment;
        this.initialTime = initialTime;
        this.atomicInteger = new AtomicInteger(initialTime);
    }

    public void setUpdate(Runnable runnable) {
        this.update = runnable;
    }

    public void onStop(Runnable runnable) {
        this.stop = runnable;
    }

    public void start() {
        if (!taskStarted) {
            taskStarted = true;
            runTaskTimer(getGameArena().getPlugin(), 0, 20);
        } else {
            throw new IllegalStateException("Timer already started!");
        }
    }

    public void stop() {
        stop.run();
        cancel();
        taskStarted = false;
        pause = false;
        increment = false;
        atomicInteger = new AtomicInteger(initialTime);
    }

    public GameArena getGameArena() {
        return gameArena;
    }

    public boolean isTaskStarted() {
        return taskStarted;
    }

    @Override
    public void run() {
        if (pause) return;
        if (increment) {
            atomicInteger.incrementAndGet();
        } else {
            if (atomicInteger.decrementAndGet() <= 0) {
                stop();
            }
        }
        if (update != null) update.run();
    }

    public int getTime() {
        return atomicInteger.get();
    }

    public String formattedTime() {
        return new DigitalTime(getTime()).digitalTime();
    }

    public boolean isIncrement() {
        return increment;
    }

    public void pause() {
        pause = true;
    }

    public void resume() {
        pause = false;
    }

    public void resetTo(int time) {
        pause();
        atomicInteger = new AtomicInteger(time);
        resume();
    }

    public boolean isPause() {
        return pause;
    }
}

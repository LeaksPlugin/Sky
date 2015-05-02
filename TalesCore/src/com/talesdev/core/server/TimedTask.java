package com.talesdev.core.server;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Timed task
 *
 * @author MoKunz
 */
public abstract class TimedTask extends BukkitRunnable {
    private int time;
    private int timePassed;
    private boolean started = false;
    private Plugin plugin;

    public TimedTask(Plugin plugin, int time) {
        this.time = time;
        this.plugin = plugin;
        this.timePassed = 0;
    }

    public void start() {
        if (started) {
            throw new IllegalStateException("Task already stared!");
        }
        runTaskTimer(plugin, 0, 20);
        started = true;
    }

    public void cancel() {
        started = false;
        super.cancel();
    }

    protected abstract void timePassed();

    @Override
    public void run() {
        timePassed();
        timePassed++;
        if (timePassed >= time) {
            this.cancel();
        }
    }

    public int getTime() {
        return timePassed;
    }
}

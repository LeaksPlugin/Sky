package com.talesdev.core.item;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * Durability progress task
 *
 * @author MoKunz
 */
public class DurabilityProgressTask extends BukkitRunnable {
    private int tickCounter = 0;
    private int maxOpTick;
    private DurabilityProgress progress;

    public DurabilityProgressTask(DurabilityProgress progress, int maxOpTick) {
        this.progress = progress;
        this.maxOpTick = maxOpTick;
    }

    public void oneTick() {
        tickCounter++;
    }

    @Override
    public void run() {
        if (tickCounter > maxOpTick) {
            cancel();
            return;
        }
        System.out.println(tickCounter);
        System.out.println((tickCounter / maxOpTick));
        progress.apply(Math.round((tickCounter / maxOpTick) * 100F));
        oneTick();
    }
    // 0 run 1
    // 1 run 2
}

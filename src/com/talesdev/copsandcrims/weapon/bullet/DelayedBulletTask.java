package com.talesdev.copsandcrims.weapon.bullet;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * Delayed Bullet task
 *
 * @author MoKunz
 */
public class DelayedBulletTask extends BukkitRunnable {
    @Override
    public void run() {
        this.cancel();
    }
}

package com.talesdev.copsandcrims.weapon.bullet;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * Delayed Bullet task
 *
 * @author MoKunz
 */
public class DelayedBulletTask extends BukkitRunnable {
    private DelayedBullet bullet;

    public DelayedBulletTask(DelayedBullet bullet) {
        this.bullet = bullet;
        this.bullet.startProcessing();
    }
    @Override
    public void run() {
        if (bullet.isCancel()) {
            bullet.finished();
            this.cancel();
        }
        bullet.process();
    }
}

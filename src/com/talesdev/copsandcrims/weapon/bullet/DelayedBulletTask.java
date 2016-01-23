package com.talesdev.copsandcrims.weapon.bullet;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * Delayed Bullet task
 *
 * @author MoKunz
 */
public class DelayedBulletTask extends BukkitRunnable {
    private DelayedBullet bullet;
    private long time;

    public DelayedBulletTask(DelayedBullet bullet) {
        this.bullet = bullet;
        System.out.println("Start profiling " + bullet.getWeapon());
        time = System.currentTimeMillis();
        this.bullet.startProcessing();
    }
    @Override
    public void run() {
        if (bullet.isCancel()) {
            bullet.finished();
            System.out.println("Finished " + bullet.getWeapon() + " , time used : " + (System.currentTimeMillis() - time));
            this.cancel();
        }
        bullet.process();
    }
}

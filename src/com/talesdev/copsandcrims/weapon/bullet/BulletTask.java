package com.talesdev.copsandcrims.weapon.bullet;

import org.bukkit.scheduler.BukkitRunnable;

/**
* Created by MoKunz on 3/26/2015.
*/
public class BulletTask extends BukkitRunnable {
    private int tickCounter = 1;
    private Bullet bullet;
    private int count = 3;
    public BulletTask(Bullet bullet, int count){
        this.bullet = bullet;
        this.count = count;
    }
    @Override
    public void run() {
        if (tickCounter > count) {
            this.cancel();
            return;
        }
        bullet.fire();
        tickCounter++;
    }
}

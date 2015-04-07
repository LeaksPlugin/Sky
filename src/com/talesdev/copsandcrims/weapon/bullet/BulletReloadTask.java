package com.talesdev.copsandcrims.weapon.bullet;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.player.CvCPlayer;
import com.talesdev.copsandcrims.weapon.Weapon;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Task for reloading bullet
 *
 * @author MoKunz
 */
public class BulletReloadTask extends BukkitRunnable {
    private int tickCounter = 0;
    private CvCPlayer player;
    private CopsAndCrims plugin;

    public BulletReloadTask(CvCPlayer player, Weapon weapon) {
        this.player = player;
        this.plugin = CopsAndCrims.getPlugin();
    }

    public void oneTick() {
        tickCounter++;
    }

    @Override
    public void run() {
        oneTick();
    }
}

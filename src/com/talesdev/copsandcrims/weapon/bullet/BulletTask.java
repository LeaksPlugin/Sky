package com.talesdev.copsandcrims.weapon.bullet;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.player.CvCPlayer;
import com.talesdev.copsandcrims.weapon.Weapon;
import com.talesdev.copsandcrims.weapon.module.ShootingModule;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/**
* Created by MoKunz on 3/26/2015.
*/
public class BulletTask extends BukkitRunnable {
    private int tickCounter = 1;
    private CvCPlayer player;
    private Bullet bullet;
    private Weapon weapon;
    private int count = 3;

    public BulletTask(Bullet bullet, int count, Weapon weapon) {
        this.bullet = bullet;
        this.count = count;
        this.player = CopsAndCrims.getPlugin().getServerCvCPlayer().getPlayer(bullet.getPlayer());
        this.weapon = weapon;
    }
    @Override
    public void run() {
        if (tickCounter > count) {
            this.cancel();
            return;
        }
        player.getPlayerBullet().getBullet(weapon.getName()).usedBullet(1);
        updateBulletCount(player);
        bullet.fire();
        updateBulletCount(player);
        if (!handleReloadBullet()) {
            this.cancel();
            return;
        }
        tickCounter++;
    }

    private void updateBulletCount(CvCPlayer player) {
        player.getPlayer().getItemInHand().setAmount(player.getPlayerBullet().getBullet(weapon.getName()).getBulletCount());
    }

    private boolean handleReloadBullet() {
        // find an item
        ItemStack itemStack = player.getWeapon(weapon.getClass());
        // reload if run out of bullet
        if (player.getPlayerBullet().getBullet(weapon.getName()).getBulletCount() <= 0) {
            (new BulletReloadTask(player, weapon, itemStack, weapon.getModule(ShootingModule.class).getReloadTime())).runTaskTimer(CopsAndCrims.getPlugin(), 0, 1);
            return false;
        }
        return true;
    }
}

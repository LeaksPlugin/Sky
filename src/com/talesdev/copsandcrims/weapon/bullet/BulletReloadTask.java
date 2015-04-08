package com.talesdev.copsandcrims.weapon.bullet;

import com.talesdev.copsandcrims.player.CvCPlayer;
import com.talesdev.copsandcrims.weapon.Weapon;
import com.talesdev.copsandcrims.weapon.WeaponBullet;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Task for reloading bullet
 *
 * @author MoKunz
 */
public class BulletReloadTask extends BukkitRunnable {
    private int tickCounter = 0;
    private CvCPlayer player;
    private Weapon weapon;
    private ItemStack itemStack;

    public BulletReloadTask(CvCPlayer player, Weapon weapon, ItemStack itemStack) {
        this.player = player;
        this.weapon = weapon;
        this.itemStack = itemStack;
        player.getPlayer().sendMessage(ChatColor.GREEN + "Reloading...");
        player.getPlayerBullet().getBullet(weapon.getName()).reload();
    }

    public void oneTick() {
        tickCounter++;
    }

    public int getTickCounter() {
        return tickCounter;
    }

    @Override
    public void run() {
        if (player.getWeapon(weapon.getClass()).getType().equals(Material.AIR)) {
            player.getPlayerBullet().getBullet(weapon.getName()).finishedReloading();
            cancel();
            return;
        }
        if (getTickCounter() > 5) {
            WeaponBullet weaponBullet = player.getPlayerBullet().getBullet(weapon.getName());
            weaponBullet.setBulletCount(weaponBullet.getMaxBullet());
            weaponBullet.finishedReloading();
            player.getPlayer().sendMessage(ChatColor.GREEN + "Reload completed");
            player.getWeapon(weapon.getClass()).setAmount(30);
            cancel();
            return;
        }
        oneTick();
    }
}

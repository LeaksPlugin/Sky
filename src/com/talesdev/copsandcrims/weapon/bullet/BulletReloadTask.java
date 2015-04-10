package com.talesdev.copsandcrims.weapon.bullet;

import com.talesdev.copsandcrims.player.CvCPlayer;
import com.talesdev.copsandcrims.weapon.Weapon;
import com.talesdev.copsandcrims.weapon.WeaponBullet;
import com.talesdev.core.player.ActionBar;
import org.bukkit.ChatColor;
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
    private int reloadTime;

    public BulletReloadTask(CvCPlayer player, Weapon weapon, ItemStack itemStack, int reloadTime) {
        this.player = player;
        this.weapon = weapon;
        this.itemStack = itemStack;
        this.reloadTime = reloadTime;
        ActionBar actionBar = new ActionBar(ChatColor.GREEN + "Reloading");
        actionBar.send(player.getPlayer());
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
        if ((!weapon.isWeapon(player.getWeapon(weapon.getClass()))) || player.getPlayerBullet().getBullet(weapon.getName()).isCancel()) {
            player.getPlayerBullet().getBullet(weapon.getName()).respondCancel();
            player.getPlayerBullet().getBullet(weapon.getName()).finishedReloading();
            ActionBar actionBar = new ActionBar(ChatColor.RED + "Reloading cancelled!");
            actionBar.send(player.getPlayer());
            cancel();
            return;
        }
        if (getTickCounter() > reloadTime) {
            WeaponBullet weaponBullet = player.getPlayerBullet().getBullet(weapon.getName());
            weaponBullet.setBulletCount(weaponBullet.getMaxBullet());
            weaponBullet.finishedReloading();
            ActionBar actionBar = new ActionBar(ChatColor.GREEN + "Reloading completed!");
            actionBar.send(player.getPlayer());
            player.getWeapon(weapon.getClass()).setAmount(weaponBullet.getMaxBullet());
            cancel();
            return;
        }
        oneTick();
    }
}

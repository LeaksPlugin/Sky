
package com.talesdev.copsandcrims.guns;

import com.talesdev.copsandcrims.weapon.Weapon;
import com.talesdev.copsandcrims.weapon.WeaponType;
import com.talesdev.copsandcrims.weapon.bullet.Accuracy;
import com.talesdev.copsandcrims.weapon.bullet.BulletAccuracy;
import com.talesdev.copsandcrims.weapon.module.*;
import com.talesdev.core.math.Range;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Desert Eagle
 *
 * @author sonSunnoi
 */
 public class HK45 extends Weapon {
 
    public HK45() {
        super("HK45", ChatColor.GREEN + "HK45", blankAliases(), WeaponType.PISTOL);
        ShootingModule shootingModule = new ShootingModule();
        ItemControlModule controlModule = new ItemControlModule();
        DeathMessageModule deathMessageModule = new DeathMessageModule();
        shootingModule.setDamage(7.6);
        shootingModule.setHeadShotDamage(25.4);
        shootingModule.setRecoil(4.0D);
        shootingModule.setMaxBullet(13);
        shootingModule.setBulletDelay(4);
        shootingModule.setCooldownTime(4);
        shootingModule.setReloadTime(54);
        shootingModule.setAccuracy(new BulletAccuracy(
                new Accuracy(new Range(-11, 11), new Range(-11, 11), new Range(-11, 11)), // default
                new Accuracy(new Range(5, 5), new Range(5, 5), new Range(5, 5)), // sneaking
                new Accuracy(new Range(-23, 23), new Range(-23, 23), new Range(-23, 23)), // walking
                new Accuracy(new Range(-80, 80), new Range(-80, 80), new Range(-80, 80)), // sprinting
                new Accuracy(new Range(-100, 100), new Range(-100, 100), new Range(-100, 100)) // jumping
        ));
        addModule(shootingModule);
        addModule(controlModule);
        addModule(deathMessageModule);

    @Override
    public ItemStack createItemStack() {
        ItemStack itemStack = new ItemStack(Material.WOOD_PICKAXE, 13);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getDisplayName());
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public boolean isWeapon(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getType().equals(Material.WOOD_PICKAXE)) {
            return true;
        }
        return super.isWeapon(itemStack);
    }
}

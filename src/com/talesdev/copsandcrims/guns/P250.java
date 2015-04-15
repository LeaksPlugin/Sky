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
 * P250
 *
 * @author sonSunnoi
 */
public class P250 extends Weapon {

    public P250() {
        super("P250", ChatColor.GREEN + "P250", blankAliases(), WeaponType.PISTOL);
        ShootingModule shootingModule = new ShootingModule();
        ItemControlModule controlModule = new ItemControlModule();
        DeathMessageModule deathMessageModule = new DeathMessageModule();
        shootingModule.setDamage(7.8);
        shootingModule.setHeadShotDamage(24.2);
        shootingModule.setUpperLegDamage(6.2);
        shootingModule.setLowerLegDamage(5.1);
        shootingModule.setRecoil(7.0D);
        shootingModule.setMaxBullet(13);
        shootingModule.setBulletDelay(4);
        shootingModule.setCooldownTime(4);
        shootingModule.setReloadTime(44);
        shootingModule.setAccuracy(new BulletAccuracy(
                new Accuracy(new Range(-12, 12), new Range(-12, 12), new Range(-12, 12)), // default
                new Accuracy(new Range(4, 4), new Range(4, 4), new Range(4, 4)), // sneaking
                new Accuracy(new Range(-20, 20), new Range(-20, 20), new Range(-20, 20)), // walking
                new Accuracy(new Range(-80, 80), new Range(-80, 80), new Range(-80, 80)), // sprinting
                new Accuracy(new Range(-100, 100), new Range(-100, 100), new Range(-100, 100)) // jumping
        ));
        addModule(shootingModule);
        addModule(controlModule);
        addModule(deathMessageModule);
    }

    @Override
    public ItemStack createItemStack() {
        ItemStack itemStack = new ItemStack(Material.DIAMOND_HOE, 13);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getDisplayName());
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public boolean isWeapon(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getType().equals(Material.DIAMOND_HOE)) {
            return true;
        }
        return super.isWeapon(itemStack);
    }
}

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
 * AK-47
 *
 * @author sonSunnoi
 */
public class AK47 extends Weapon {

    public AK47() {
        super("AK47", ChatColor.GREEN + "AK-47", blankAliases(), WeaponType.ASSAULT_RIFLE);
        ShootingModule shootingModule = new ShootingModule();
        ItemControlModule controlModule = new ItemControlModule();
        DeathMessageModule deathMessageModule = new DeathMessageModule();
        shootingModule.setDamage(5.3);
        shootingModule.setHeadShotDamage(17.4);
        shootingModule.setUpperLegDamage(4.8);
        shootingModule.setLowerLegDamage(3.5);
        shootingModule.setArmorPenetration(73.5);
        shootingModule.setRecoil(5.0D);
        shootingModule.setMaxRecoil(24.0D)
        shootingModule.setMaxBullet(30);
        shootingModule.setBulletDelay(2);
        shootingModule.setBulletCount(2);
        shootingModule.setCooldownTime(2);
        shootingModule.setReloadTime(50);
        shootingModule.setAccuracy(new BulletAccuracy(
                new Accuracy(new Range(-12, 12), new Range(-12, 12), new Range(-12, 12)), // default
                new Accuracy(new Range(6, 6), new Range(6, 6), new Range(6, 6)), // sneaking
                new Accuracy(new Range(-20, 20), new Range(-20, 20), new Range(-20, 20)), // walking
                new Accuracy(new Range(-35, 35), new Range(-35, 35), new Range(-35, 35)), // sprinting
                new Accuracy(new Range(-50, 50), new Range(-50, 50), new Range(-50, 50)) // jumping
        ));
        addModule(shootingModule);
        addModule(controlModule);
        addModule(deathMessageModule);
    }

    @Override
    public ItemStack createItemStack() {
        ItemStack itemStack = new ItemStack(Material.WOOD_SPADE, 30);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getDisplayName());
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public boolean isWeapon(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getType().equals(Material.WOOD_SPADE)) {
            return true;
        }
        return super.isWeapon(itemStack);
    }
}

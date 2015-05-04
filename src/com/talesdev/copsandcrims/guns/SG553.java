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
 * SG 553
 *
 * @author sonSunnoi
 */
public class SG553 extends Weapon {

    public SG553() {
        super("SG553", ChatColor.GREEN + "SG 553", blankAliases(), WeaponType.ASSAULT_RIFLE);
        ShootingModule shootingModule = new ShootingModule();
        ScopeModule scopeModule = new ScopeModule();
        ItemControlModule controlModule = new ItemControlModule();
        DeathMessageModule deathMessageModule = new DeathMessageModule();
        shootingModule.setDamage(5.5);
        shootingModule.setHeadShotDamage(20);
        shootingModule.setUpperLegDamage(5);
        shootingModule.setLowerLegDamage(4);
        shootingModule.setArmorPenetration(100);
        shootingModule.setRecoil(4.35D);
        shootingModule.setMaxBullet(30);
        shootingModule.setBulletDelay(2);
        shootingModule.setBulletCount(2);
        shootingModule.setCooldownTime(2);
        shootingModule.setReloadTime(56);
        shootingModule.setAccuracy(new BulletAccuracy(
                new Accuracy(new Range(-14, 14), new Range(-14, 14), new Range(-14, 14)), // default
                new Accuracy(new Range(3, 3), new Range(3, 3), new Range(3, 3)), // sneaking
                new Accuracy(new Range(-20, 20), new Range(-20, 20), new Range(-20, 20)), // walking
                new Accuracy(new Range(-80, 80), new Range(-80, 80), new Range(-80, 80)), // sprinting
                new Accuracy(new Range(-100, 100), new Range(-100, 100), new Range(-100, 100)) // jumping
        ));
        scopeModule.setZoomLevel(3);
        addModule(shootingModule);
        addModule(controlModule);
        addModule(deathMessageModule);
        addModule(scopeModule);
    }

    @Override
    public ItemStack createItemStack() {
        ItemStack itemStack = new ItemStack(Material.IRON_PICKAXE, 30);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getDisplayName());
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public boolean isWeapon(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getType().equals(Material.IRON_PICKAXE)) {
            return true;
        }
        return super.isWeapon(itemStack);
    }
}

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
 * AWP
 *
 * @author sonSunnoi
 */
public class AWP extends Weapon {

    public AWP() {
        super("AWP", ChatColor.GREEN + "AWP", blankAliases(), WeaponType.SNIPER_RIFLE);
        ShootingModule shootingModule = new ShootingModule();
        ScopeModule scopeModule = new ScopeModule();
        ItemControlModule controlModule = new ItemControlModule();
        DeathMessageModule deathMessageModule = new DeathMessageModule();
        shootingModule.setDamage(18.4);
        shootingModule.setHeadShotDamage(62.4);
        shootingModule.setUpperLegDamage(13.9);
        shootingModule.setLowerLegDamage(8.6);
        shootingModule.setArmorPenetration(92.5);
        shootingModule.setRecoil(0.0D);
        shootingModule.setMaxBullet(10);
        shootingModule.setBulletDelay(30);
        shootingModule.setCooldownTime(30);
        shootingModule.setReloadTime(72);
        shootingModule.setAccuracy(new BulletAccuracy(
                new Accuracy(new Range(-75, 75), new Range(-75, 75), new Range(-75, 75)), // default
                new Accuracy(new Range(0, 0), new Range(0, 0), new Range(0, 0)), // sneaking
                new Accuracy(new Range(-110, 110), new Range(-110, 110), new Range(-110, 110)), // walking
                new Accuracy(new Range(-140, 140), new Range(-140, 140), new Range(-140, 140)), // sprinting
                new Accuracy(new Range(-140, 140), new Range(-140, 140), new Range(-140, 140)) // jumping
        ));
        shootingModule.setFiringMode(FiringMode.BOLT);
        scopeModule.setZoomLevel(3);
        addModule(shootingModule);
        addModule(controlModule);
        addModule(deathMessageModule);
        addModule(scopeModule);
    }

    @Override
    public ItemStack createItemStack() {
        ItemStack itemStack = new ItemStack(Material.GOLD_SPADE, 10);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getDisplayName());
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public boolean isWeapon(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getType().equals(Material.GOLD_SPADE)) {
            return true;
        }
        return super.isWeapon(itemStack);
    }
}

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
 * @author MoKunz & sonSunnoi
 */
public class DesertEagle extends Weapon {

    public DesertEagle() {
        super("DesertEagle", ChatColor.GREEN + "Desert Eagle", blankAliases(), WeaponType.PISTOL);
        ShootingModule shootingModule = new ShootingModule();
        ItemControlModule controlModule = new ItemControlModule();
        DeathMessageModule deathMessageModule = new DeathMessageModule();
        shootingModule.setDamage(7.7);
        shootingModule.setHeadShotDamage(18.4);
        shootingModule.setUpperLegDamage(6.2);
        shootingModule.setLowerLegDamage(4.5);
        shootingModule.setArmorPenetration(85.0);
        shootingModule.setRecoil(9.0D);
        shootingModule.setMaxRecoil(27.5D);
        shootingModule.setMaxBullet(7);
        shootingModule.setBulletDelay(6);
        shootingModule.setCooldownTime(6);
        shootingModule.setReloadTime(44);
        shootingModule.setAccuracy(new BulletAccuracy(
                new Accuracy(new Range(-15, 15), new Range(-15, 15), new Range(-15, 15)), // default
                new Accuracy(new Range(-5, 5), new Range(-5, 5), new Range(-5, 5)), // sneaking
                new Accuracy(new Range(-25, 25), new Range(-25, 25), new Range(-25, 25)), // walking
                new Accuracy(new Range(-45, 45), new Range(-45, 45), new Range(-45, 45)), // sprinting
                new Accuracy(new Range(-70, 70), new Range(-70, 70), new Range(-70, 70)) // jumping
        ));
        addModule(shootingModule);
        addModule(controlModule);
        addModule(deathMessageModule);
    }

    @Override
    public ItemStack createItemStack() {
        ItemStack itemStack = new ItemStack(Material.GOLD_HOE, 7);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getDisplayName());
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public boolean isWeapon(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getType().equals(Material.GOLD_HOE)) {
            return true;
        }
        return super.isWeapon(itemStack);
    }
}

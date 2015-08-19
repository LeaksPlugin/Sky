package com.talesdev.copsandcrims.guns;

import com.talesdev.copsandcrims.weapon.Weapon;
import com.talesdev.copsandcrims.weapon.WeaponType;
import com.talesdev.copsandcrims.weapon.bullet.Accuracy;
import com.talesdev.copsandcrims.weapon.bullet.BulletAccuracy;
import com.talesdev.copsandcrims.weapon.module.DeathMessageModule;
import com.talesdev.copsandcrims.weapon.module.ItemControlModule;
import com.talesdev.copsandcrims.weapon.module.ShootingModule;
import com.talesdev.core.math.Range;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Galil-AR
 *
 * @author sonSunnoi
 */
public class GalilAR extends Weapon {

    public GalilAR() {
        super("GalilAR", ChatColor.GREEN + "Galil-AR", blankAliases(), WeaponType.ASSAULT_RIFLE);
        ShootingModule shootingModule = new ShootingModule();
        ItemControlModule controlModule = new ItemControlModule();
        DeathMessageModule deathMessageModule = new DeathMessageModule();
        shootingModule.setDamage(4.6);
        shootingModule.setHeadShotDamage(12.9);
        shootingModule.setUpperLegDamage(4.0);
        shootingModule.setLowerLegDamage(3.1);
        shootingModule.setArmorPenetration(70.0);
        shootingModule.setRecoil(5.25D);
        shootingModule.setMaxRecoil(22.0D);
        shootingModule.setMaxBullet(35);
        shootingModule.setBulletDelay(2);
        shootingModule.setBulletCount(2);
        shootingModule.setCooldownTime(2);
        shootingModule.setReloadTime(60);
        shootingModule.setAccuracy(new BulletAccuracy(
                new Accuracy(new Range(-20, 20), new Range(-20, 20), new Range(-20, 20)), // default
                new Accuracy(new Range(-12, 12), new Range(-12, 12), new Range(-12, 12)), // sneaking
                new Accuracy(new Range(-40, 40), new Range(-40, 40), new Range(-40, 40)), // walking
                new Accuracy(new Range(-60, 60), new Range(-60, 60), new Range(-60, 60)), // sprinting
                new Accuracy(new Range(-75, 75), new Range(-75, 75), new Range(-75, 75)) // jumping
        ));
        addModule(shootingModule);
        addModule(controlModule);
        addModule(deathMessageModule);
    }

    @Override
    public ItemStack createItemStack() {
        ItemStack itemStack = new ItemStack(Material.STONE_PICKAXE, 35);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getDisplayName());
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public boolean isWeapon(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getType().equals(Material.STONE_PICKAXE)) {
            return true;
        }
        return super.isWeapon(itemStack);
    }
}

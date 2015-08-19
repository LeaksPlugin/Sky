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
 * M4A1
 *
 * @author sonSunnoi
 */
public class M4A1 extends Weapon {

    public M4A1() {
        super("M4A1", ChatColor.GREEN + "M4A1", blankAliases(), WeaponType.ASSAULT_RIFLE);
        ShootingModule shootingModule = new ShootingModule();
        ItemControlModule controlModule = new ItemControlModule();
        DeathMessageModule deathMessageModule = new DeathMessageModule();
        shootingModule.setDamage(4.8);
        shootingModule.setHeadShotDamage(13.2);
        shootingModule.setUpperLegDamage(4.1);
        shootingModule.setLowerLegDamage(3.5);
        shootingModule.setArmorPenetration(70);
        shootingModule.setRecoil(3.75D);
        shootingModule.setMaxRecoil(17.5D);
        shootingModule.setMaxBullet(30);
        shootingModule.setBulletDelay(2);
        shootingModule.setBulletCount(2);
        shootingModule.setCooldownTime(2);
        shootingModule.setReloadTime(61);
        shootingModule.setAccuracy(new BulletAccuracy(
                new Accuracy(new Range(-10, 10), new Range(-10, 10), new Range(-10, 10)), // default
                new Accuracy(new Range(-5, 5), new Range(-5, 5), new Range(-5, 5)), // sneaking
                new Accuracy(new Range(-17, 17), new Range(-17, 17), new Range(-17, 17)), // walking
                new Accuracy(new Range(-27, 27), new Range(-27, 27), new Range(-27, 27)), // sprinting
                new Accuracy(new Range(-40, 40), new Range(-40, 40), new Range(-40, 40)) // jumping
        ));
        addModule(shootingModule);
        addModule(controlModule);
        addModule(deathMessageModule);
    }

    @Override
    public ItemStack createItemStack() {
        ItemStack itemStack = new ItemStack(Material.STONE_SPADE, 30);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getDisplayName());
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public boolean isWeapon(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getType().equals(Material.STONE_SPADE)) {
            return true;
        }
        return super.isWeapon(itemStack);
    }
}

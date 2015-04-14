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
        shootingModule.setDamage(7.2);
        shootingModule.setHeadShotDamage(27.4);
        shootingModule.setRecoil(6.0D);
        shootingModule.setMaxBullet(30);
        shootingModule.setBulletDelay(3);
        shootingModule.setBulletCount(2);
        shootingModule.setCooldownTime(3);
        shootingModule.setReloadTime(50);
        shootingModule.setAccuracy(new BulletAccuracy(
                new Accuracy(new Range(-12, 12), new Range(-12, 12), new Range(-12, 12)), // default
                new Accuracy(new Range(3, 3), new Range(3, 3), new Range(3, 3)), // sneaking
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

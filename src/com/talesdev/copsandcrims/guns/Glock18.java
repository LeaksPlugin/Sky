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
 * Glock-18
 *
 * @author MoKunz
 */
public class Glock18 extends Weapon {

    public Glock18() {
        super("Glock18", ChatColor.GREEN + "Glock-18", blankAliases(), WeaponType.PISTOL);
        ShootingModule shootingModule = new ShootingModule();
        ItemControlModule controlModule = new ItemControlModule();
        DeathMessageModule deathMessageModule = new DeathMessageModule();
        shootingModule.setDamage(6);
        shootingModule.setHeadShotDamage(19.6);
        shootingModule.setRecoil(5.0D);
        shootingModule.setMaxBullet(20);
        shootingModule.setBulletDelay(4);
        shootingModule.setCooldownTime(4);
        shootingModule.setReloadTime(44);
        shootingModule.setAccuracy(new BulletAccuracy(
                new Accuracy(new Range(-13, 13), new Range(-13, 13), new Range(-13, 13)), // default
                new Accuracy(new Range(5, 5), new Range(5, 5), new Range(5, 5)), // sneaking
                new Accuracy(new Range(-25, 25), new Range(-25, 25), new Range(-25, 25)), // walking
                new Accuracy(new Range(-80, 80), new Range(-80, 80), new Range(-80, 80)), // sprinting
                new Accuracy(new Range(-100, 100), new Range(-100, 100), new Range(-100, 100)) // jumping
        ));
        addModule(shootingModule);
        addModule(controlModule);
        addModule(deathMessageModule);
    }

    @Override
    public ItemStack createItemStack() {
        ItemStack itemStack = new ItemStack(Material.STONE_PICKAXE, 20);
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

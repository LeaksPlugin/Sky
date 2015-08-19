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
 * Steyr AUG
 *
 * @author sonSunnoi
 */
public class AUG extends Weapon {

    public AUG() {
        super("AUG", ChatColor.GREEN + "Steyr Aug", blankAliases(), WeaponType.ASSAULT_RIFLE);
        ShootingModule shootingModule = new ShootingModule();
        ScopeModule scopeModule = new ScopeModule();
        ItemControlModule controlModule = new ItemControlModule();
        DeathMessageModule deathMessageModule = new DeathMessageModule();
        shootingModule.setDamage(4.4);
        shootingModule.setHeadShotDamage(13.0);
        shootingModule.setUpperLegDamage(3.9);
        shootingModule.setLowerLegDamage(3.2);
        shootingModule.setArmorPenetration(83.5);
        shootingModule.setRecoil(4.0D);
        shootingModule.setMaxRecoil(17.0D);
        shootingModule.setMaxBullet(30);
        shootingModule.setBulletDelay(2);
        shootingModule.setBulletCount(2);
        shootingModule.setCooldownTime(2);
        shootingModule.setReloadTime(76);
        shootingModule.setAccuracy(new BulletAccuracy(
                new Accuracy(new Range(-15, 15), new Range(-15, 15), new Range(-15, 15)), // default
                new Accuracy(new Range(-1, 1), new Range(-1, 1), new Range(-1, 1)), // sneaking
                new Accuracy(new Range(-22, 22), new Range(-22, 22), new Range(-22, 22)), // walking
                new Accuracy(new Range(-40, 40), new Range(-40, 40), new Range(-40, 40)), // sprinting
                new Accuracy(new Range(-65, 65), new Range(-65, 65), new Range(-65, 65)) // jumping
        ));
        scopeModule.setZoomLevel(1);
        addModule(shootingModule);
        addModule(controlModule);
        addModule(deathMessageModule);
        addModule(scopeModule);
    }

    @Override
    public ItemStack createItemStack() {
        ItemStack itemStack = new ItemStack(Material.GOLD_PICKAXE, 30);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getDisplayName());
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public boolean isWeapon(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getType().equals(Material.GOLD_PICKAXE)) {
            return true;
        }
        return super.isWeapon(itemStack);
    }
}

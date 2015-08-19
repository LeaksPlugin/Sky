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
 * SSG 08
 *
 * @author sonSunnoi
 */
public class SSG08 extends Weapon {

    public SSG08() {
        super("SSG08", ChatColor.GREEN + "SSG 08", blankAliases(), WeaponType.SNIPER_RIFLE);
        ShootingModule shootingModule = new ShootingModule();
        ScopeModule scopeModule = new ScopeModule();
        ItemControlModule controlModule = new ItemControlModule();
        DeathMessageModule deathMessageModule = new DeathMessageModule();
        shootingModule.setDamage(10.6);
        shootingModule.setHeadShotDamage(19.7);
        shootingModule.setUpperLegDamage(8.0);
        shootingModule.setLowerLegDamage(6.5);
        shootingModule.setArmorPenetration(83.5);
        shootingModule.setRecoil(0.0D);
        shootingModule.setMaxBullet(10);
        shootingModule.setBulletDelay(16);
        shootingModule.setCooldownTime(16);
        shootingModule.setReloadTime(74);
        shootingModule.setAccuracy(new BulletAccuracy(
                new Accuracy(new Range(-100, 100), new Range(-100, 100), new Range(-100, 100)), // default
                new Accuracy(new Range(0, 0), new Range(0, 0), new Range(0, 0)), // sneaking
                new Accuracy(new Range(-150, 150), new Range(-150, 150), new Range(-150, 150)), // walking
                new Accuracy(new Range(-300, 300), new Range(-300, 300), new Range(-300, 300)), // sprinting
                new Accuracy(new Range(-100, 100), new Range(-200, 200), new Range(-100, 100)) // jumping
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
        ItemStack itemStack = new ItemStack(Material.DIAMOND_PICKAXE, 10);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getDisplayName());
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public boolean isWeapon(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getType().equals(Material.DIAMOND_PICKAXE)) {
            return true;
        }
        return super.isWeapon(itemStack);
    }
}

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
        shootingModule.setDamage(23);
        shootingModule.setHeadShotDamage(82.1);
        shootingModule.setRecoil(15.0D);
        shootingModule.setMaxBullet(10);
        shootingModule.setBulletDelay(25);
        shootingModule.setCooldownTime(25);
        shootingModule.setReloadTime(72);
        shootingModule.setAccuracy(new BulletAccuracy(
                new Accuracy(new Range(-300, 300), new Range(-300, 200), new Range(-300, 300)), // default
                new Accuracy(new Range(0, 0), new Range(0, 0), new Range(0, 0)), // sneaking
                new Accuracy(new Range(-500, 500), new Range(-500, 500), new Range(-500, 500)), // walking
                new Accuracy(new Range(-700, 700), new Range(-700, 700), new Range(-700, 700)), // sprinting
                new Accuracy(new Range(-700, 700), new Range(-700, 700), new Range(-700, 700)) // jumping
        ));
        shootingModule.setFiringMode(FiringMode.BOLT);
        scopeModule.setZoomLevel(5);
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

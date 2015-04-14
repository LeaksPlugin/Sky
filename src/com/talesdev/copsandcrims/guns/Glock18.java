package com.talesdev.copsandcrims.guns;

import com.talesdev.copsandcrims.weapon.Weapon;
import com.talesdev.copsandcrims.weapon.WeaponType;
import com.talesdev.copsandcrims.weapon.bullet.Accuracy;
import com.talesdev.copsandcrims.weapon.bullet.BulletAccuracy;
import com.talesdev.copsandcrims.weapon.module.AlternativeFireModule;
import com.talesdev.copsandcrims.weapon.module.DeathMessageModule;
import com.talesdev.copsandcrims.weapon.module.ItemControlModule;
import com.talesdev.copsandcrims.weapon.module.ShootingModule;
import com.talesdev.core.math.Range;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Glock-18
 *
 * @author MoKunz & sonSunnoi
 */
public class Glock18 extends Weapon {

    public Glock18() {
        super("Glock18", ChatColor.GREEN + "Glock-18", blankAliases(), WeaponType.PISTOL);
        ShootingModule shootingModule = new ShootingModule();
        ItemControlModule controlModule = new ItemControlModule();
        DeathMessageModule deathMessageModule = new DeathMessageModule();
        AlternativeFireModule alternativeFireModule = new AlternativeFireModule(true);
        shootingModule.setDamage(5);
        shootingModule.setHeadShotDamage(14.9);
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
        alternativeFireModule.setAlternativeFireBullet(3);
        alternativeFireModule.setAlternativeFireDelay(1);
        alternativeFireModule.setAlternativeFireCooldown(20);
        alternativeFireModule.setEnabled(true);
        addModule(shootingModule);
        addModule(alternativeFireModule);
        addModule(controlModule);
        addModule(deathMessageModule);
    }

    @Override
    public ItemStack createItemStack() {
        ItemStack itemStack = new ItemStack(Material.STONE_HOE, 20);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getDisplayName());
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public boolean isWeapon(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getType().equals(Material.STONE_HOE)) {
            return true;
        }
        return super.isWeapon(itemStack);
    }
}

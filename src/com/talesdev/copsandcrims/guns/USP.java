
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
 * USP
 *
 * @author sonSunnoi
 */
 public class USP extends Weapon {
 
    public USP() {
        super("USP", ChatColor.GREEN + "USP", blankAliases(), WeaponType.PISTOL);
        ShootingModule shootingModule = new ShootingModule();
        ItemControlModule controlModule = new ItemControlModule();
        DeathMessageModule deathMessageModule = new DeathMessageModule();
        shootingModule.setDamage(4.6);
        shootingModule.setHeadShotDamage(8.9);
        shootingModule.setUpperLegDamage(3.9);
        shootingModule.setLowerLegDamage(3.1);
        shootingModule.setArmorPenetration(60.0);
        shootingModule.setRecoil(5.0D);
        shootingModule.setMaxRecoil(22.0D);
        shootingModule.setMaxBullet(13);
        shootingModule.setBulletDelay(4);
        shootingModule.setCooldownTime(4);
        shootingModule.setReloadTime(54);
        shootingModule.setAccuracy(new BulletAccuracy(
                new Accuracy(new Range(-13, 13), new Range(-13, 13), new Range(-13, 13)), // default
                new Accuracy(new Range(-6, 6), new Range(-6, 6), new Range(-6, 6)), // sneaking
                new Accuracy(new Range(-23, 23), new Range(-23, 23), new Range(-23, 23)), // walking
                new Accuracy(new Range(-40, 40), new Range(-40, 40), new Range(-40, 40)), // sprinting
                new Accuracy(new Range(-65, 65), new Range(-65, 65), new Range(-65, 65)) // jumping
        ));
        addModule(shootingModule);
        addModule(controlModule);
        addModule(deathMessageModule);
    }
    @Override
    public ItemStack createItemStack() {
        ItemStack itemStack = new ItemStack(Material.WOOD_HOE, 13);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getDisplayName());
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public boolean isWeapon(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getType().equals(Material.WOOD_HOE)) {
            return true;
        }
        return super.isWeapon(itemStack);
    }
}

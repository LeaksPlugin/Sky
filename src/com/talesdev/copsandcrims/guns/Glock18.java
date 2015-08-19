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
        shootingModule.setDamage(3.1);
        shootingModule.setHeadShotDamage(6.5);
        shootingModule.setUpperLegDamage(2.8);
        shootingModule.setLowerLegDamage(2.0);
        shootingModule.setArmorPenetration(55);
        shootingModule.setRecoil(2.75D);
        shootingModule.setMaxRecoil(13.0D);
        shootingModule.setMaxBullet(20);
        shootingModule.setBulletDelay(4);
        shootingModule.setCooldownTime(4);
        shootingModule.setReloadTime(44);
        shootingModule.setAccuracy(new BulletAccuracy(
                new Accuracy(new Range(-15, 15), new Range(-15, 15), new Range(-15, 15)), // default
                new Accuracy(new Range(-7, 7), new Range(-7, 7), new Range(-7, 7)), // sneaking
                new Accuracy(new Range(-27, 27), new Range(-27, 27), new Range(-27, 27)), // walking
                new Accuracy(new Range(-35, 35), new Range(-35, 35), new Range(-35, 35)), // sprinting
                new Accuracy(new Range(-40, 40), new Range(-40, 40), new Range(-40, 40)) // jumping
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

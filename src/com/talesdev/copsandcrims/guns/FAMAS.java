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
 * FAMAS
 *
 * @author MoKunz & sonSunnoi
 */
public class FAMAS extends Weapon {

    public FAMAS() {
        super("FAMAS", ChatColor.GREEN + "FAMAS", blankAliases(), WeaponType.ASSAULT_RIFLE);
        ShootingModule shootingModule = new ShootingModule();
        ItemControlModule controlModule = new ItemControlModule();
        DeathMessageModule deathMessageModule = new DeathMessageModule();
        AlternativeFireModule alternativeFireModule = new AlternativeFireModule();
        shootingModule.setDamage(4.1);
        shootingModule.setHeadShotDamage(11.9);
        shootingModule.setUpperLegDamage(3.7);
        shootingModule.setLowerLegDamage(2.9);
        shootingModule.setArmorPenetration(66.5);
        shootingModule.setRecoil(4.75D);
        shootingModule.setMaxRecoil(24.0D);
        shootingModule.setMaxBullet(25);
        shootingModule.setBulletDelay(2);
        shootingModule.setBulletCount(2);
        shootingModule.setCooldownTime(2);
        shootingModule.setReloadTime(66);
        shootingModule.setAccuracy(new BulletAccuracy(
                new Accuracy(new Range(-16, 16), new Range(-16, 16), new Range(-16, 16)), // default
                new Accuracy(new Range(-5, 5), new Range(-5, 5), new Range(-5, 5)), // sneaking
                new Accuracy(new Range(-30, 30), new Range(-30, 30), new Range(-30, 30)), // walking
                new Accuracy(new Range(-50, 50), new Range(-50, 50), new Range(-50, 50)), // sprinting
                new Accuracy(new Range(-70, 70), new Range(-70, 70), new Range(-70, 70)) // jumping
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
        ItemStack itemStack = new ItemStack(Material.WOOD_PICKAXE, 25);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getDisplayName());
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public boolean isWeapon(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.getType().equals(Material.WOOD_PICKAXE)) {
            return true;
        }
        return super.isWeapon(itemStack);
    }
}

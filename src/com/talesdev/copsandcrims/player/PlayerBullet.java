package com.talesdev.copsandcrims.player;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.weapon.Weapon;
import com.talesdev.copsandcrims.weapon.WeaponBullet;
import com.talesdev.copsandcrims.weapon.module.ShootingModule;

import java.util.HashMap;
import java.util.Map;

/**
 * Class represent a player bullet
 *
 * @author MoKunz
 */
public class PlayerBullet {
    private Map<String, WeaponBullet> bulletAmount;

    public PlayerBullet() {
        this.bulletAmount = new HashMap<>();
        init();
    }

    private void init() {
        for (Weapon weapon : CopsAndCrims.getPlugin().getWeaponFactory().getAllWeapon()) {
            int startBulletAmount = 10;
            if (weapon.containsModule(ShootingModule.class)) {
                startBulletAmount = weapon.getModule(ShootingModule.class).getMaxBullet();
            }
            getBulletMap().put(weapon.getName(), new WeaponBullet(weapon.getName(), startBulletAmount));
        }
    }

    public Map<String, WeaponBullet> getBulletMap() {
        return bulletAmount;
    }
}
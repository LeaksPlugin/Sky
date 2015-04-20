package com.talesdev.copsandcrims.weapon;

import java.util.List;
import java.util.Random;

/**
 * Random Weapon
 *
 * @author MoKunz
 */
public class RandomWeapon {
    private WeaponFactory weaponFactory;

    public RandomWeapon(WeaponFactory weaponFactory) {
        this.weaponFactory = weaponFactory;
    }

    public Weapon randomWeapon() {
        List<Weapon> weaponList = weaponFactory.getAllWeapon();
        return weaponList.get(new Random().nextInt(weaponList.size()));
    }

    public Class<? extends Weapon> randomWeaponClass() {
        return randomWeapon().getClass();
    }

    public String randomWeaponName() {
        return randomWeapon().getName();
    }
}

package com.talesdev.copsandcrims.weapon;

import java.util.Collections;
import java.util.List;

/**
 * Weapon slot
 */
public enum WeaponSlot {
    PRIMARY(WeaponType.ASSAULT_RIFLE, WeaponType.SNIPER_RIFLE, WeaponType.SHOTGUN, WeaponType.SUB_MACHINE_GUN),
    SECONDARY(WeaponType.PISTOL),
    MELEE(WeaponType.MELEE),
    UNKNOWN();
    private List<WeaponType> weaponTypeList;

    WeaponSlot(WeaponType... weaponType) {
        Collections.addAll(weaponTypeList, weaponType);
    }

    public static WeaponSlot getSlot(Weapon weapon) {
        if (PRIMARY.getApplicableType().contains(weapon.getWeaponType())) {
            return PRIMARY;
        } else if (SECONDARY.getApplicableType().contains(weapon.getWeaponType())) {
            return SECONDARY;
        } else if (MELEE.getApplicableType().contains(weapon.getWeaponType())) {
            return MELEE;
        } else {
            return UNKNOWN;
        }
    }

    public List<WeaponType> getApplicableType() {
        return weaponTypeList;
    }
}

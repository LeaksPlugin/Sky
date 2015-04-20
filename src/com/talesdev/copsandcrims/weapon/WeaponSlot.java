package com.talesdev.copsandcrims.weapon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Weapon slot
 */
public enum WeaponSlot {
    PRIMARY(0, WeaponType.ASSAULT_RIFLE, WeaponType.SNIPER_RIFLE, WeaponType.SHOTGUN, WeaponType.SUB_MACHINE_GUN),
    SECONDARY(1, WeaponType.PISTOL),
    MELEE(2, WeaponType.MELEE),
    UNKNOWN(8);
    private List<WeaponType> weaponTypeList;
    private int inventorySlot;

    WeaponSlot(int inventorySlot, WeaponType... weaponType) {
        weaponTypeList = new ArrayList<>();
        this.inventorySlot = inventorySlot;
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

    public int getInventorySlot() {
        return inventorySlot;
    }

    public List<WeaponType> getApplicableType() {
        return weaponTypeList;
    }
}

package com.talesdev.copsandcrims.weapon;

import com.talesdev.copsandcrims.CopsAndCrims;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Weapon factory
 *
 * @author MoKunz
 */
public class WeaponFactory {
    private List<Weapon> weapons;
    private CopsAndCrims plugin;

    public WeaponFactory(CopsAndCrims plugin) {
        this.plugin = plugin;
        weapons = new ArrayList<>();
    }

    public void addWeapon(Weapon weapon) {
        if (!weapons.contains(weapon)) {
            weapons.add(weapon);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Weapon> T getWeapon(Class<T> weaponClass) {
        for (Weapon weapon : weapons) {
            if (weapon.getClass().getName().equals(weaponClass.getName())) {
                return (T) weapon;
            }
        }
        return null;
    }

    public Weapon getWeapon(String weaponName) {
        for (Weapon weapon : weapons) {
            if (weapon.getName().equalsIgnoreCase(weaponName)) {
                return weapon;
            }
        }
        return null;
    }

    public <T extends Weapon> ItemStack createWeaponItem(Class<T> weaponClass) {
        T weapon = getWeapon(weaponClass);
        if (weapon != null) {
            return weapon.createItemStack();
        } else {
            return new ItemStack(Material.AIR);
        }
    }
}

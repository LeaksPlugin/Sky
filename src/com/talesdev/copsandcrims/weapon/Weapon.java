package com.talesdev.copsandcrims.weapon;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.weapon.module.WeaponItem;
import com.talesdev.copsandcrims.weapon.module.WeaponModule;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represent a weapon
 * @author MoKunz
 */
public abstract class Weapon implements WeaponItem {
    protected String name;
    protected String displayName;
    protected List<String> lore;

    private List<WeaponModule> modules;

    public Weapon(String name, String displayName, List<String> lore) {
        this.name = name;
        this.displayName = displayName;
        this.lore = lore;
    }

    public String getName() {
        return name;
    }

    public List<String> getLore() {
        return lore;
    }

    public static List<String> blankAliases() {
        return new ArrayList<>();
    }

    public void addModule(WeaponModule weaponModule) {
        for (WeaponModule module : modules) {
            // don't allow duplicate module
            if (weaponModule.getClass().getName().equals(module.getClass().getName())) {
                return;
            }
        }
        weaponModule.linkWeapon(this);
        modules.add(weaponModule);
        Bukkit.getServer().getPluginManager().registerEvents(weaponModule, CopsAndCrims.getPlugin());
    }

    @SuppressWarnings("unchecked")
    public <T extends WeaponModule> T getModule(Class<T> tClass) {
        for (WeaponModule module : modules) {
            if (module.getClass().getName().equals(tClass.getName())) {
                return (T) module;
            }
        }
        return null;
    }

    @Override
    public boolean isWeapon(ItemStack itemStack) {
        if (itemStack != null) {
            return itemStack.isSimilar(createItemStack());
        }
        return false;
    }
    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Weapon) {
            if (((Weapon) obj).getName().equals(this.getName())) {
                return true;
            }
        }
        return false;
    }
}

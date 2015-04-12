package com.talesdev.copsandcrims.guns;

import com.talesdev.copsandcrims.weapon.Weapon;
import com.talesdev.copsandcrims.weapon.WeaponType;
import com.talesdev.copsandcrims.weapon.module.MeleeModule;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Knife
 *
 * @author MoKunz
 */
public class Knife extends Weapon {
    public Knife() {
        super("Knife", "Knife", blankAliases(), WeaponType.MELEE);
        MeleeModule meleeModule = new MeleeModule(4, 8);
        addModule(meleeModule);
    }

    @Override
    public ItemStack createItemStack() {
        return new ItemStack(Material.IRON_SWORD);
    }

    @Override
    public boolean isWeapon(ItemStack itemStack) {
        if (itemStack != null) return itemStack.getType().equals(Material.IRON_SWORD);
        return false;
    }
}

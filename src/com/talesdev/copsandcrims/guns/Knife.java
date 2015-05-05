package com.talesdev.copsandcrims.guns;

import com.talesdev.copsandcrims.weapon.Weapon;
import com.talesdev.copsandcrims.weapon.WeaponType;
import com.talesdev.copsandcrims.weapon.module.DeathMessageModule;
import com.talesdev.copsandcrims.weapon.module.MeleeModule;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Knife
 *
 * @author MoKunz
 */
public class Knife extends Weapon {
    public Knife() {
        super("Knife", ChatColor.GREEN + "Knife", blankAliases(), WeaponType.MELEE);
        MeleeModule meleeModule = new MeleeModule(5, 15, 30, 75);
        DeathMessageModule messageModule = new DeathMessageModule();
        addModule(messageModule);
        addModule(meleeModule);
    }

    @Override
    public ItemStack createItemStack() {
        ItemStack itemStack = new ItemStack(Material.IRON_AXE);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(getDisplayName());
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public boolean isWeapon(ItemStack itemStack) {
        if (itemStack != null) return itemStack.getType().equals(Material.IRON_AXE);
        return false;
    }
}

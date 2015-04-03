package com.talesdev.copsandcrims.guns;

import com.talesdev.copsandcrims.weapon.Weapon;
import com.talesdev.copsandcrims.weapon.WeaponType;
import com.talesdev.copsandcrims.weapon.module.DummyModule;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Desert Eagle
 * @author MoKunz
 */
public class DesertEagle extends Weapon {

    public DesertEagle() {
        super("DesertEagle", "Desert Eagle", blankAliases(), WeaponType.PISTOL);
        addModule(new DummyModule());
    }

    @Override
    public ItemStack createItemStack() {
        return new ItemStack(Material.GOLD_HOE);
    }
}

package com.talesdev.copsandcrims.armor;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

/**
 * Armor part
 *
 * @author MoKunz
 */
public enum ArmorPart {
    HELMET,
    CHESTPLATE,
    LEGGINGS,
    BOOTS;

    public ItemStack getFrom(LivingEntity entity) {
        switch (this) {
            case HELMET:
                return entity.getEquipment().getHelmet();
            case CHESTPLATE:
                return entity.getEquipment().getChestplate();
            case LEGGINGS:
                return entity.getEquipment().getLeggings();
            case BOOTS:
                return entity.getEquipment().getBoots();
            default:
                return new ItemStack(Material.AIR);
        }
    }
}

package com.talesdev.core.item;

import org.bukkit.Material;

/**
 * Right clickable interface
 *
 * @author MoKunz
 */
public class RightClickable implements MaterialComparatorInterface {
    /**
     * Check if given material is contain in the specific set of material.
     *
     * @param material A Material to be compared
     * @return the boolean value , true or false.
     */
    @Override
    public boolean contain(Material material) {
        return material.equals(Material.DISPENSER) ||
                material.equals(Material.NOTE_BLOCK) ||
                material.equals(Material.BED) ||
                material.equals(Material.CHEST) ||
                material.equals(Material.WORKBENCH) ||
                material.equals(Material.FURNACE) ||
                material.equals(Material.BURNING_FURNACE) ||
                material.equals(Material.WOODEN_DOOR) ||
                material.equals(Material.LEVER) ||
                material.equals(Material.JUKEBOX) ||
                material.equals(Material.DIODE_BLOCK_OFF) ||
                material.equals(Material.DIODE_BLOCK_ON) ||
                material.equals(Material.TRAP_DOOR) ||
                material.equals(Material.FENCE_GATE) ||
                material.equals(Material.ENCHANTMENT_TABLE) ||
                material.equals(Material.BREWING_STAND) ||
                material.equals(Material.CAULDRON) ||
                material.equals(Material.ENDER_CHEST) ||
                material.equals(Material.COMMAND) ||
                material.equals(Material.BEACON) ||
                material.equals(Material.ANVIL) ||
                material.equals(Material.TRAPPED_CHEST) ||
                material.equals(Material.REDSTONE_COMPARATOR_OFF) ||
                material.equals(Material.REDSTONE_COMPARATOR_ON) ||
                material.equals(Material.HOPPER) ||
                material.equals(Material.DROPPER) ||
                material.equals(Material.STONE_BUTTON) ||
                material.equals(Material.ACACIA_DOOR) ||
                material.equals(Material.SPRUCE_DOOR) ||
                material.equals(Material.BIRCH_DOOR) ||
                material.equals(Material.JUNGLE_DOOR) ||
                material.equals(Material.DARK_OAK_DOOR) ||
                material.equals(Material.ACACIA_FENCE_GATE) ||
                material.equals(Material.SPRUCE_FENCE_GATE) ||
                material.equals(Material.BIRCH_FENCE_GATE) ||
                material.equals(Material.JUNGLE_FENCE_GATE) ||
                material.equals(Material.DARK_OAK_FENCE_GATE) ||
                material.equals(Material.SPRUCE_FENCE_GATE) ||
                material.equals(Material.WOOD_BUTTON);
    }
}

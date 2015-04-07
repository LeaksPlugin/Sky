package com.talesdev.core.item;

import org.bukkit.Material;

/**
 * Item comparator interface
 * Created by MoKunz on 3/6/2015.
 */
public interface MaterialComparatorInterface {
    /**
     * Check if given material is contain in the specific set of material.
     *
     * @param material A Material to be compared
     * @return the boolean value , true or false.
     */
    public boolean contain(Material material);
}

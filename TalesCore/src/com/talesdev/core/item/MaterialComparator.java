package com.talesdev.core.item;

import org.bukkit.Material;

/**
 * Item Comparator
 * Created by MoKunz on 3/6/2015.
 */
public class MaterialComparator {
    private MaterialComparatorInterface comparatorInterface;

    public MaterialComparator(MaterialComparatorInterface comparatorInterface) {
        this.comparatorInterface = comparatorInterface;
    }

    public boolean containThisMaterial(Material material) {
        return comparatorInterface.contain(material);
    }

    public boolean notContainThisMaterial(Material material) {
        return !containThisMaterial(material);
    }
}

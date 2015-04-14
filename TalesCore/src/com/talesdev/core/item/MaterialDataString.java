package com.talesdev.core.item;

import org.bukkit.Material;
import org.bukkit.material.MaterialData;

/**
 * Material data string
 *
 * @author MoKunz
 */
public class MaterialDataString {
    private MaterialData data;

    public MaterialDataString(MaterialData materialData) {
        this.data = materialData;
    }

    public MaterialData getData() {
        return data;
    }

    public void setData(MaterialData data) {
        this.data = data;
    }

    public String toString() {
        return data.getItemType().toString() + ":" + Integer.toHexString(data.getData());
    }

    public static MaterialDataString fromString(String materialDataString) {
        String regex = "\\:";
        String[] data = materialDataString.split(regex);
        if (data.length > 1) {
            Material material = Material.getMaterial(data[0]);
            if (material == null) material = Material.AIR;
            MaterialData materialData = new MaterialData(material);
            try {
                materialData.setData((byte) Integer.parseInt(data[1], 16));
            } catch (NumberFormatException e) {
                materialData.setData((byte) 0);
            }
            return new MaterialDataString(materialData);
        }
        return null;
    }
}
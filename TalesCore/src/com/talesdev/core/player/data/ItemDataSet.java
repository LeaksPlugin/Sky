package com.talesdev.core.player.data;

import com.talesdev.core.player.CorePlayer;

import java.util.HashSet;
import java.util.Set;

/**
 * Item data list
 *
 * @author MoKunz
 */
public class ItemDataSet {
    private String itemName;
    private CorePlayer corePlayer;
    private Set<ItemData> itemDataSet;

    public ItemDataSet(CorePlayer corePlayer, String itemName) {
        itemDataSet = new HashSet<>();
        this.itemName = itemName;
        this.corePlayer = corePlayer;
    }

    public Set<ItemData> getAll() {
        return new HashSet<>(itemDataSet);
    }

    public ItemData get(String type) {
        for (ItemData itemData : itemDataSet) {
            if (itemData.dataType().equals(type)) {
                return itemData;
            }
        }
        return null;
    }

    public <T> T get(Class<T> dataTypeClass) {
        for (ItemData itemData : itemDataSet) {
            if (itemData.getClassType().getName().equals(dataTypeClass.getName())) {
                return dataTypeClass.cast(itemData);
            }
        }
        return null;
    }

    public void add(ItemData data) {
        itemDataSet.add(data);
    }

    public String getItemName() {
        return itemName;
    }

    @Override
    public int hashCode() {
        return itemName.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof String && itemName.equals(object);
    }

    public CorePlayer getCorePlayer() {
        return corePlayer;
    }
}

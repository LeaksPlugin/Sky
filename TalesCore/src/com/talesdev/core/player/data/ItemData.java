package com.talesdev.core.player.data;

/**
 * Item data
 *
 * @author MoKunz
 */
public interface ItemData {
    public String forItem();

    public String dataType();

    public Class<? extends ItemData> getClassType();
}

package com.talesdev.core;

import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;

import java.util.List;

/**
 * Metadata wrapper
 *
 * @author MoKunz
 */
public class MetaData {
    private Metadatable object;
    private Plugin plugin;

    public MetaData(Metadatable object, Plugin plugin) {
        this.object = object;
        this.plugin = plugin;
    }

    public void setMetadata(String key, Object value) {
        object.setMetadata(key, new FixedMetadataValue(plugin, value));
    }

    public Object getMetadata(String key) {
        List<MetadataValue> values = object.getMetadata(key);
        for (MetadataValue value : values) {
            // Plugins are singleton objects, so using == is safe here
            if (value.getOwningPlugin() == plugin) {
                return value.value();
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T getMetadata(String key, Class<T> type) {
        return (T) getMetadata(key);
    }
}

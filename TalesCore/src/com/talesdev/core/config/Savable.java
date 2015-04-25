package com.talesdev.core.config;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Represent savable object
 *
 * @author MoKunz
 */
public interface Savable {
    public void loadFrom(FileConfiguration config);

    public void saveTo(FileConfiguration config);

    public String getName();
}

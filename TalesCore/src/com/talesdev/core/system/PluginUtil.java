package com.talesdev.core.system;

import org.bukkit.Bukkit;

/**
 * Plugin Status util
 * @author MoKunz
 */
public class PluginUtil {
    public static boolean isAvailable(String name){
        return Bukkit.getPluginManager().getPlugin(name) != null;
    }
}

package com.talesdev.corebungee;

import net.md_5.bungee.api.plugin.Plugin;

/**
 * Plugin main class
 *
 * @author MoKunz
 */
public class CoreBungee extends Plugin {
    @Override
    public void onEnable() {
        getLogger().info("CoreBungee has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("CoreBungee has been disabled!");
    }
}

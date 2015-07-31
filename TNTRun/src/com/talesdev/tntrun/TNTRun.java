package com.talesdev.tntrun;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author MoKunz
 */
public class TNTRun extends JavaPlugin {
    private TNTRunGameArena game;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        game = new TNTRunGameArena(this);
        getLogger().info("TNTRun has been enabled!");
    }

    @Override
    public void onDisable() {
        game.destroy();
        saveConfig();
        getLogger().info("TNTRun has been disabled!");
    }
}

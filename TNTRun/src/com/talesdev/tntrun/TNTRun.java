package com.talesdev.tntrun;

import com.talesdev.core.arena.DedicatedArenaCommand;
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
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getCommand("tntrun").setExecutor(new DedicatedArenaCommand(game, "tntrun"));
        getLogger().info("TNTRun has been enabled!");
    }

    @Override
    public void onDisable() {
        game.destroy();
        saveConfig();
        getLogger().info("TNTRun has been disabled!");
    }
}

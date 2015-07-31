package com.talesdev.core.arena;

import com.talesdev.core.arena.world.ArenaWorld;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Arena Map essentials configuration
 *
 * @author MoKunz
 */
public class ArenaMapConfig {
    private GameArena gameArena;
    private FileConfiguration config;

    public ArenaMapConfig(GameArena gameArena) {
        this.gameArena = gameArena;
        this.config = gameArena.getConfig();
    }

    public void onLoad() {
        ArenaWorld world = new GeneralArenaWorld(
                gameArena,
                config.getString("map-name"), config.getString("map-author")
        );
        gameArena.setArenaWorld(world);
    }

    public void onDestroy() {
        if (!config.contains("arena-locked")) config.set("arena-locked", true);
        if (!config.contains("max-players")) config.set("max-players", gameArena.getMaxPlayers());
        if (!config.contains("map-name")) config.set("map-name", gameArena.getArenaName());
        if (!config.contains("map-author")) config.set("map-author", "Unknown");
    }
}

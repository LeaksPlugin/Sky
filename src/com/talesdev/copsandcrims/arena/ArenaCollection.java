package com.talesdev.copsandcrims.arena;

import com.talesdev.copsandcrims.ServerCvCArena;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Collection of arena stored in file format
 *
 * @author MoKunz
 */
public class ArenaCollection {
    private ServerCvCArena arena;

    public ArenaCollection(ServerCvCArena arena) {
        this.arena = arena;
    }

    public List<CvCArena> getAll() {
        List<CvCArena> arenaList = new ArrayList<>();
        Set<String> arenas = getConfig().getConfigurationSection("Arena").getKeys(false);
        if (arenas.size() > 0) {
            for (String arena : arenas) {

            }
        }
        return arenaList;
    }

    protected FileConfiguration getConfig() {
        return arena.getConfig();
    }
}

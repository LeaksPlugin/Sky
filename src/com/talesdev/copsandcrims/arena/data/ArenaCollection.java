package com.talesdev.copsandcrims.arena.data;

import com.talesdev.copsandcrims.ServerCvCArena;
import com.talesdev.copsandcrims.arena.CvCArena;
import com.talesdev.copsandcrims.arena.system.CvCArenaController;
import org.bukkit.configuration.ConfigurationSection;
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
        if (!getConfig().isSet("Arena")) getConfig().set("Arena", null);
        ConfigurationSection section = getConfig().getConfigurationSection("Arena");
        if (section != null) {
            Set<String> arenas = section.getKeys(false);
            if (arenas.size() > 0) {
                for (String arena : arenas) {
                    String type = getConfig().getString("Arena" + "." + arena);
                    CvCArenaController arenaController = this.arena.getController(type);
                    if (arenaController != null) {
                        arenaList.add(new CvCArena(this.arena, arena, arenaController));
                    }
                }
            }
        }
        return arenaList;
    }

    public void saveAll(List<CvCArena> arenaList) {
        if (arenaList != null && arenaList.size() > 0) {
            for (CvCArena arena : arenaList) {
                String baseNode = "Arena" + "." + arena.getArenaName();
                getConfig().set(baseNode, arena.getArenaController().getArenaType());
            }
        }
    }

    protected FileConfiguration getConfig() {
        return arena.getConfig();
    }
}

package com.talesdev.copsandcrims.arena;

import com.talesdev.copsandcrims.ServerCvCArena;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Collection of arena stored in file format
 *
 * @author MoKunz
 */
public class ArenaFileCollection {
    private File dir;
    private ServerCvCArena arena;

    public ArenaFileCollection(ServerCvCArena arena, File directory) {
        this.dir = directory;
        this.arena = arena;
    }

    public List<CvCArena> getAll() {
        List<CvCArena> arenaList = new ArrayList<>();
        Set<String> arenas = arena.getConfig().getConfigurationSection("Arena").getKeys(false);
        if (arenas.size() > 0) {
            for (String arenaName : arenas) {

            }
        }
        return arenaList;
    }
}

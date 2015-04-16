package com.talesdev.copsandcrims.arena.data;

import com.talesdev.copsandcrims.ServerCvCArena;
import com.talesdev.copsandcrims.arena.CvCArena;
import com.talesdev.copsandcrims.arena.system.CvCArenaController;
import org.bukkit.configuration.MemorySection;
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
        Object section = getConfig().get("Arena");
        if (section instanceof MemorySection) {
            Set<String> arenas = ((MemorySection) section).getKeys(false);
            for (String arena : arenas) {
                CvCArenaController arenaController = this.arena.getController(getConfig().getString("Arena." + arena));
                if (arenaController != null) {
                    arenaList.add(new CvCArena(this.arena, arena, arenaController));
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

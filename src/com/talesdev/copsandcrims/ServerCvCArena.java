package com.talesdev.copsandcrims;

import com.talesdev.copsandcrims.arena.ArenaFileCollection;
import com.talesdev.copsandcrims.arena.CvCArena;
import com.talesdev.core.config.ConfigFile;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * ServerCvCArena
 *
 * @author MoKunz
 */
public class ServerCvCArena {
    private ConfigFile configFile;
    private List<CvCArena> arenaList;
    private CopsAndCrims plugin;

    public ServerCvCArena(CopsAndCrims plugin) {
        this.plugin = plugin;
        File dir = new File("plugins/CopsAndCrims/arena");
        if (!dir.exists()) {
            boolean mkdirs = dir.mkdirs();
            if (!mkdirs) System.out.println("Error while creating directory plugins/CopsAndCrims/arena !");
        }
        this.configFile = new ConfigFile("plugins/CopsAndCrims/arena.yml");
        this.arenaList = new ArrayList<>();
        // begin arena loading
        ArenaFileCollection collection = new ArenaFileCollection(this, new File("plugins/CopsAndCrims/arena"));
        collection.getAll().forEach(this::addArena);
    }

    public void addArena(CvCArena arena) {
        if (arena != null) {
            if (!containsArena(arena.getArenaName())) {
                getArenaList().add(arena);
            }
        }
    }

    public CvCArena getArena(String arenaName) {
        if (getArenaList().size() > 0) {
            for (CvCArena cArena : getArenaList()) {
                if (cArena.getArenaName().equals(arenaName)) {
                    return cArena;
                }
            }
        }
        return null;
    }

    public void removeArena(CvCArena arena) {
        getArenaList().remove(arena);
    }

    public int totalArena() {
        return getArenaList().size();
    }

    public boolean containsArena(String arena) {
        if (getArenaList().size() > 0) {
            for (CvCArena cArena : getArenaList()) {
                if (cArena.getArenaName().equals(arena)) {
                    return true;
                }
            }
        }
        return false;
    }

    public FileConfiguration getConfig() {
        return configFile.getConfig();
    }

    public List<CvCArena> getArenaList() {
        return arenaList;
    }

    public void shutDown() {
        getArenaList().forEach(CvCArena::shutDown);
    }
}

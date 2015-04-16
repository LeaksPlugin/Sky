package com.talesdev.copsandcrims.arena.system;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.arena.CvCArena;
import com.talesdev.copsandcrims.arena.data.ArenaCommandProtocol;
import com.talesdev.copsandcrims.player.CvCPlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

/**
 * Arena Controller for different game mode
 *
 * @author MoKunz
 */
public abstract class CvCArenaController implements Listener, ArenaSpawnLocation, ArenaCommandProtocol {
    protected CvCArena arena;
    private String arenaType;
    private boolean locked = false;

    public CvCArenaController(String arenaType) {
        this.arenaType = arenaType;
    }

    public CvCArenaController(CvCArenaController cvCArenaController) {
        this(cvCArenaController.getArenaType());
        cvCArenaController.setControlledArena(cvCArenaController.getArena());
    }
    public void setControlledArena(CvCArena arena) {
        if (!locked) this.arena = arena;
        this.locked = true;
    }

    public String getArenaType() {
        return arenaType;
    }

    protected CvCArena getArena() {
        return arena;
    }

    protected CopsAndCrims getPlugin() {
        return CopsAndCrims.getPlugin();
    }

    protected FileConfiguration getConfig() {
        return getArena().getConfig();
    }

    public void arenaLoaded() {
    }

    public abstract CvCArenaController createController();

    public abstract void startArena();

    public abstract void endArena();

    public void shutdown() {
        save();
    }

    public void save() {

    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CvCArenaController) {
            return ((CvCArenaController) obj).getArenaType().equalsIgnoreCase(getArenaType());
        }
        return false;
    }
}

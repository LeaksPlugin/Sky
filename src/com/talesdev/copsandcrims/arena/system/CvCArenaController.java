package com.talesdev.copsandcrims.arena.system;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.arena.CvCArena;
import com.talesdev.copsandcrims.player.CvCPlayer;
import org.bukkit.event.Listener;

/**
 * Arena Controller for different game mode
 *
 * @author MoKunz
 */
public abstract class CvCArenaController implements Listener, ArenaSpawnLocation {
    protected CvCArena arena;
    private String arenaType;
    private boolean locked = false;

    public CvCArenaController(String arenaType) {
        this.arenaType = arenaType;
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

    public abstract void joinArena(CvCPlayer cPlayer);

    public abstract void leaveArena(CvCPlayer cPlayer);

    public void arenaLoaded() {
    }

    public abstract void startArena();

    public abstract void endArena();

    public void shutdown() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CvCArenaController) {
            return ((CvCArenaController) obj).getArenaType().equalsIgnoreCase(getArenaType());
        }
        return false;
    }
}

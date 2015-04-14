package com.talesdev.copsandcrims.arena;

import org.bukkit.event.Listener;

/**
 * Arena Controller for different game mode
 * @author MoKunz
 */
public abstract class CvCArenaController implements Listener{
    private String arenaType;
    protected CvCArena arena;
    public CvCArenaController(String arenaType){
        this.arenaType = arenaType;
    }
    public void setControlledArena(CvCArena arena){
        this.arena = arena;
    }
    public String getArenaType(){
        return arenaType;
    }
    public abstract void startArena();
    public abstract void endArena();

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CvCArenaController) {
            return ((CvCArenaController) obj).getArenaType().equalsIgnoreCase(getArenaType());
        }
        return false;
    }
}

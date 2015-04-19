package com.talesdev.copsandcrims.arena.system;

import com.talesdev.copsandcrims.player.CvCPlayer;

/**
 * Represent an arena with custom joining/leaving system
 *
 * @author MoKunz
 */
public interface ArenaJoinLeave {
    public void joinArena(CvCPlayer player, boolean showMessage);

    public void joinArena(CvCPlayer player);

    public void leaveArena(CvCPlayer player, boolean showMessage, boolean removePlayer);

    public void leaveArena(CvCPlayer player, boolean showMessage);

    public void leaveArena(CvCPlayer player);

    public int getMaxPlayers();

    public void setMaxPlayers(int maxPlayers);
}

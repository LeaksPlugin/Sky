package com.talesdev.copsandcrims.arena.system;

import com.talesdev.copsandcrims.player.CvCPlayer;

/**
 * Represent an arena with custom joining/leaving system
 *
 * @author MoKunz
 */
public interface ArenaJoinLeave {
    public void joinArena(CvCPlayer player);

    public void leaveArena(CvCPlayer player);
}

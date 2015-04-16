package com.talesdev.copsandcrims.arena.data;

import com.talesdev.copsandcrims.player.CvCPlayer;

/**
 * Player arena data
 *
 * @author MoKunz
 */
public class PlayerArenaData {
    private CvCPlayer player;
    private PlayerArenaStatus status;

    public PlayerArenaData(CvCPlayer player) {
        this.player = player;
    }

    public CvCPlayer getPlayer() {
        return player;
    }

    public PlayerArenaStatus getStatus() {
        return status;
    }

    public void setStatus(PlayerArenaStatus status) {
        this.status = status;
    }
}

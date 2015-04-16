package com.talesdev.copsandcrims.arena.data;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.arena.CvCArena;
import com.talesdev.copsandcrims.player.CvCPlayer;
import org.bukkit.GameMode;

/**
 * Player arena data
 *
 * @author MoKunz
 */
public class PlayerArenaData {
    private CvCPlayer player;
    private String playingArena;
    private GameMode lastGameMode = player.getPlayer().getServer().getDefaultGameMode();
    private PlayerArenaStatus status = PlayerArenaStatus.NOT_PLAYING;

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

    public GameMode getLastGameMode() {
        return lastGameMode;
    }

    public void setLastGameMode(GameMode lastGameMode) {
        this.lastGameMode = lastGameMode;
    }

    public CvCArena getPlayingArena() {
        return CopsAndCrims.getPlugin().getServerCvCArena().getArena(playingArena);
    }

    public void setPlayingArena(CvCArena arena) {
        this.playingArena = arena.getArenaName();
    }
}

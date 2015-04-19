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
    private GameMode lastGameMode = GameMode.ADVENTURE;
    private PlayerArenaStatus status = PlayerArenaStatus.NOT_PLAYING;
    private int kills = 0;
    private int deaths = 0;
    private int assist = 0;
    private boolean isSpectator = false;

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
        this.playingArena = arena != null ? arena.getArenaName() : null;
    }

    public boolean isSpectator() {
        return isSpectator;
    }

    public void setSpectator(boolean isSpectator) {
        this.isSpectator = isSpectator;
    }

    public void addKill() {
        kills++;
    }

    public void addDeath() {
        deaths++;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getAssist() {
        return assist;
    }

    public void addAssist() {
        assist++;
    }
}

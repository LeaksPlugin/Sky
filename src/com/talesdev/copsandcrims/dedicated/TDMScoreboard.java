package com.talesdev.copsandcrims.dedicated;

import com.talesdev.core.TalesCore;
import com.talesdev.core.arena.scoreboard.DisplayScoreboard;
import com.talesdev.core.player.CorePlayer;
import org.bukkit.entity.Player;

/**
 * TDM Scoreboard
 *
 * @author MoKunz
 */
public class TDMScoreboard implements DisplayScoreboard {
    private TDMGameArena gameArena;

    public TDMScoreboard(TDMGameArena gameArena) {
        this.gameArena = gameArena;
    }

    @Override
    public void start(Player player) {

    }

    @Override
    public void update(Player player) {
        CorePlayer corePlayer = TalesCore.getPlugin().getCorePlayer(player);
    }

    @Override
    public Class<? extends DisplayScoreboard> getType() {
        return TDMScoreboard.class;
    }

    @Override
    public TDMGameArena getGameArena() {
        return gameArena;
    }
}

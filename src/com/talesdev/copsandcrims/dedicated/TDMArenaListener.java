package com.talesdev.copsandcrims.dedicated;

import com.talesdev.core.arena.GameArena;
import com.talesdev.core.arena.event.GeneralArenaListener;
import com.talesdev.core.arena.event.PlayerJoinArenaEvent;
import com.talesdev.core.arena.phase.LobbyPhase;
import com.talesdev.core.arena.scoreboard.LobbyScoreboard;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;

/**
 * TDM Arena Listener
 *
 * @author MoKunz
 */
public class TDMArenaListener extends GeneralArenaListener {

    public TDMArenaListener(GameArena gameArena) {
        super(gameArena);
        getGameArena().dispatchPhase(new LobbyPhase());
        LobbyScoreboard lobbyScoreboard = new LobbyScoreboard();
        lobbyScoreboard.setTitle(ChatColor.GREEN + "");
        getGameArena().applyScoreboard(new LobbyScoreboard());
    }

    @EventHandler
    public void onArenaJoin(PlayerJoinArenaEvent event) {
        if (isArena(event)) {

        }
    }
}

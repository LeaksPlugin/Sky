package com.talesdev.core.arena.phase;

import com.talesdev.core.arena.GameArena;
import com.talesdev.core.arena.GameState;
import com.talesdev.core.arena.scoreboard.LobbyScoreboard;
import org.bukkit.ChatColor;

/**
 * Lobby phase
 *
 * @author MoKunz
 */
public class LobbyPhase implements GamePhase {
    @Override
    public void dispatch(GameArena arena) {
        arena.setGameState(GameState.WAITING);
        LobbyScoreboard lobbyScoreboard = new LobbyScoreboard();
        lobbyScoreboard.setTitle(ChatColor.GREEN + "");
        arena.applyScoreboard(new LobbyScoreboard());
    }
}

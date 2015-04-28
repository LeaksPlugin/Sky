package com.talesdev.core.arena.phase;

import com.talesdev.core.arena.GameArena;
import com.talesdev.core.arena.GamePhase;
import com.talesdev.core.arena.GameState;

/**
 * Lobby phase
 *
 * @author MoKunz
 */
public class LobbyPhase implements GamePhase {
    @Override
    public void dispatch(GameArena arena) {
        arena.setGameState(GameState.WAITING);
    }
}

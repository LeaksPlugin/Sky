package com.talesdev.core.arena.phase;

import com.talesdev.core.arena.GameArena;
import com.talesdev.core.arena.GameState;

/**
 * Start game phase
 *
 * @author MoKunz
 */
public class StartGamePhase implements GamePhase {
    @Override
    public void dispatch(GameArena arena) {
        arena.setGameState(GameState.STARTED);
        arena.startGame();
    }
}

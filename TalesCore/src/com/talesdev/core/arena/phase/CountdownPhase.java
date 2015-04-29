package com.talesdev.core.arena.phase;

import com.talesdev.core.arena.Countdown;
import com.talesdev.core.arena.GameArena;
import com.talesdev.core.arena.GameState;

/**
 * Countdown phase
 *
 * @author MoKunz
 */
public class CountdownPhase implements GamePhase {

    @Override
    public void dispatch(GameArena arena) {
        arena.setGameState(GameState.COUNTDOWN);
        Countdown countdown = new Countdown(arena);
        countdown.start();
    }
}

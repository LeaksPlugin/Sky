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
        if (arena.isLocked()) arena.systemMessage("You can't start locked arena!");
        if (arena.getGameState().equals(GameState.WAITING)) {
            arena.setGameState(GameState.COUNTDOWN);
            Countdown countdown = new Countdown(arena, 30);
            countdown.onFinish(() -> arena.dispatchPhase(new StartGamePhase()));
            countdown.start();
        }
    }
}

package com.talesdev.core.arena;

/**
 * Represent current status of an arena
 *
 * @author MoKunz
 */
public enum GameState {
    // waiting for more player to start countdown
    WAITING,
    // counting down to start a game
    COUNTDOWN,
    // game has been started
    STARTED,
    // game has been ended but player isn't get kicked
    END,
    // arena locked for cleaning up
    RESET,
    // locked for other reason
    LOCKED;

    public boolean canJoin() {
        return this == WAITING || this == COUNTDOWN;
    }

    public boolean locked() {
        return this == RESET || this == LOCKED;
    }
}

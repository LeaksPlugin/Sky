package com.talesdev.core.arena.phase;

import com.talesdev.core.arena.GameArena;

/**
 * Game phase
 *
 * @author MoKunz
 */
public interface GamePhase {
    void dispatch(GameArena arena);
}

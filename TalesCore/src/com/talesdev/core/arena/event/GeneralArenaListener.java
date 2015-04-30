package com.talesdev.core.arena.event;

import com.talesdev.core.arena.GameArena;
import com.talesdev.core.arena.GameArenaListener;

/**
 * General arena listener
 *
 * @author MoKunz
 */
public abstract class GeneralArenaListener<T extends GameArena> implements GameArenaListener {

    private T gameArena;

    public GeneralArenaListener(T gameArena) {
        this.gameArena = gameArena;
    }

    public T getGameArena() {
        return gameArena;
    }

    protected boolean isArena(ArenaInterface arenaInterface) {
        return arenaInterface.getGameArena().getArenaName().equals(getGameArena().getArenaName());
    }
}

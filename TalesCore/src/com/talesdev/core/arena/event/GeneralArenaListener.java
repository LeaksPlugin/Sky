package com.talesdev.core.arena.event;

import com.talesdev.core.arena.GameArena;
import com.talesdev.core.arena.GameArenaListener;

/**
 * General arena listener
 *
 * @author MoKunz
 */
public abstract class GeneralArenaListener implements GameArenaListener {

    private GameArena gameArena;

    public GeneralArenaListener(GameArena gameArena) {
        this.gameArena = gameArena;
    }

    public GameArena getGameArena() {
        return gameArena;
    }

    protected boolean isArena(ArenaInterface arenaInterface) {
        return arenaInterface.getGameArena().getArenaName().equals(getGameArena().getArenaName());
    }
}

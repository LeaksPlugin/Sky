package com.talesdev.core.arena.event;

import com.talesdev.core.arena.GameArena;
import org.bukkit.event.Event;

/**
 * Arena event
 *
 * @author MoKunz
 */
public abstract class ArenaEvent extends Event implements ArenaInterface {
    protected GameArena gameArena;

    public ArenaEvent(GameArena gameArena) {
        this.gameArena = gameArena;
    }

    public GameArena getGameArena() {
        return gameArena;
    }
}

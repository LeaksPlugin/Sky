package com.talesdev.core.arena.event;

import com.talesdev.core.arena.GameArena;
import org.bukkit.event.HandlerList;

/**
 * Called when an arena is going to start
 *
 * @author MoKunz
 */
public class ArenaPreStartEvent extends ArenaEvent {
    private static final HandlerList handlers = new HandlerList();

    public ArenaPreStartEvent(GameArena gameArena) {
        super(gameArena);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }
}

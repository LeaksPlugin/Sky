package com.talesdev.core.arena.event;

import com.talesdev.core.arena.GameArena;
import org.bukkit.event.HandlerList;

/**
 * Called when arena starting operation is finished
 *
 * @author MoKunz
 */
public class ArenaPostStartEvent extends ArenaEvent {
    private static final HandlerList handlers = new HandlerList();

    public ArenaPostStartEvent(GameArena gameArena) {
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

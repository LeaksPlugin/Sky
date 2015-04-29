package com.talesdev.core.arena.event;

import com.talesdev.core.arena.GameArena;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * Called when player leave an arena
 */
public class PlayerLeaveArenaEvent extends PlayerArenaEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private LeaveCause leaveCause = LeaveCause.UNKNOWN;
    private boolean cancel = false;
    private boolean showLeaveMessage = false;

    public PlayerLeaveArenaEvent(GameArena gameArena, Player player) {
        super(gameArena, player);
    }

    public PlayerLeaveArenaEvent(GameArena gameArena, Player player, LeaveCause cause) {
        super(gameArena, player);
        this.leaveCause = cause;
    }

    public PlayerLeaveArenaEvent(GameArena gameArena, Player player, LeaveCause cause, boolean showLeaveMessage) {
        super(gameArena, player);
        this.leaveCause = cause;
        this.showLeaveMessage = showLeaveMessage;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public boolean isCancelled() {
        return cancel;
    }

    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public LeaveCause getLeaveCause() {
        return leaveCause;
    }

    public boolean isShowLeaveMessage() {
        return showLeaveMessage;
    }

    public void setShowLeaveMessage(boolean showLeaveMessage) {
        this.showLeaveMessage = showLeaveMessage;
    }
}

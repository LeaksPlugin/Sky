package com.talesdev.core.arena.event;

import com.talesdev.core.arena.GameArena;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * Called when player join an arena
 *
 * @author MoKunz
 */
public class PlayerJoinArenaEvent extends PlayerArenaEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancel = false;
    private int oldPlayerCount;
    private Runnable afterRun;

    public PlayerJoinArenaEvent(GameArena gameArena, Player player, int oldPlayerCount) {
        super(gameArena, player);
        this.oldPlayerCount = oldPlayerCount;
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

    public int getOldPlayerCount() {
        return oldPlayerCount;
    }

    public int getNewPlayerCount() {
        return oldPlayerCount + 1;
    }

    public Runnable getAfterRun() {
        return afterRun;
    }

    public void setAfterRun(Runnable afterRun) {
        this.afterRun = afterRun;
    }
}

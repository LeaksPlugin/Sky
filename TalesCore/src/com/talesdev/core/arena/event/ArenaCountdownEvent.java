package com.talesdev.core.arena.event;

import com.talesdev.core.arena.GameArena;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * Countdown event
 *
 * @author MoKunz
 */
public class ArenaCountdownEvent extends ArenaEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancel;
    private int countdownTo;
    private int currentCountdown;
    private boolean finished = false;

    public ArenaCountdownEvent(GameArena gameArena, int currentCountdown, int countdownTo, boolean finished) {
        super(gameArena);
        this.currentCountdown = currentCountdown;
        this.countdownTo = countdownTo;
        this.finished = finished;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    public int getCountdownTo() {
        return countdownTo;
    }

    public void setCountdownTo(int countdownTo) {
        this.countdownTo = countdownTo;
    }

    public int getCurrentCountdown() {
        return currentCountdown;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}

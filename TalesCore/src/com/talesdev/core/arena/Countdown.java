package com.talesdev.core.arena;

import com.talesdev.core.arena.event.ArenaCountdownEvent;
import com.talesdev.core.arena.scoreboard.LobbyScoreboard;
import com.talesdev.core.arena.team.GlobalScoreboard;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Countdown system
 *
 * @author MoKunz
 */
public class Countdown extends BukkitRunnable {
    private GameArena gameArena;
    private int currentCountDown;

    public Countdown(GameArena gameArena) {
        this.gameArena = gameArena;
    }

    public void start() {
        gameArena.getTask().store("countdown", runTaskTimer(gameArena.getPlugin(), 0, 1));
    }

    public void stop() {
        cancel();
    }

    @Override
    public void run() {
        ArenaCountdownEvent event = createEvent();
        gameArena.callEvent(event);
        if (event.isCancelled()) {
            return;
        }
        if (event.isFinished()) {
            cancel();
            return;
        }
        updateScoreboard();
        currentCountDown--;
    }

    public GameArena getGameArena() {
        return gameArena;
    }

    public int getCurrentCountDown() {
        return currentCountDown;
    }

    public int getCountdownTo() {
        return currentCountDown - 1;
    }

    private void updateScoreboard() {
        GlobalScoreboard globalScoreboard = gameArena.getGlobalScoreboard();
        if (globalScoreboard.displaying(LobbyScoreboard.class)) {
            LobbyScoreboard lobbyScoreboard = globalScoreboard.getDisplayScoreboard(LobbyScoreboard.class);
            lobbyScoreboard.setCountdown(getCurrentCountDown());
        }
    }

    private ArenaCountdownEvent createEvent() {
        return new ArenaCountdownEvent(gameArena, currentCountDown, currentCountDown - 1, currentCountDown - 1 < 1);
    }
}

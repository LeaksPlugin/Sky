package com.talesdev.core.arena;

import com.talesdev.core.arena.event.ArenaCountdownEvent;
import com.talesdev.core.world.sound.Sound;
import com.talesdev.core.world.sound.SoundEffect;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Countdown system
 *
 * @author MoKunz
 */
public class Countdown extends BukkitRunnable {
    private GameArena gameArena;
    private int currentCountDown = 30;
    private Runnable onFinish;

    public Countdown(GameArena gameArena, int countdown) {
        this.gameArena = gameArena;
        if (countdown > 2) {
            this.currentCountDown = countdown;
        }
    }

    public void start() {
        gameArena.getTask().store("countdown", runTaskTimer(gameArena.getPlugin(), 0, 20));
        gameArena.getPlayerSet().forEach(this::playTickSound);
        gameArena.systemMessage("The game will be started in " + currentCountDown + " seconds!");
    }

    public void stop() {
        cancel();
    }

    public void onFinish(Runnable runnable) {
        this.onFinish = runnable;
    }

    @Override
    public void run() {
        ArenaCountdownEvent event = createEvent();
        gameArena.callEvent(event);
        if (event.isCancelled()) {
            return;
        }
        if (event.isFinished()) {
            gameArena.setGameState(GameState.STARTED);
            gameArena.getScheduler().runTask(gameArena.getPlugin(), onFinish);
            cancel();
            return;
        }
        if (currentCountDown <= 10) {
            gameArena.getPlayerSet().forEach(this::playTickSound);
            gameArena.systemMessage("The game will be started in " + currentCountDown + " seconds!");
        }
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

    private ArenaCountdownEvent createEvent() {
        return new ArenaCountdownEvent(gameArena, currentCountDown, currentCountDown - 1, currentCountDown - 1 < 0);
    }

    private void playTickSound(Player player) {
        Sound sound = new Sound(SoundEffect.NOTE_HAT, 1.0F, 1.0F);
        sound.playSound(player);
    }
}

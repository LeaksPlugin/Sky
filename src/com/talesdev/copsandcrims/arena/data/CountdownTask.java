package com.talesdev.copsandcrims.arena.data;

import com.talesdev.copsandcrims.arena.CvCArena;
import com.talesdev.copsandcrims.arena.system.CountdownSystem;
import com.talesdev.copsandcrims.player.CvCPlayer;
import com.talesdev.core.world.Sound;
import com.talesdev.core.world.SoundEffect;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Countdown task
 */
public class CountdownTask extends BukkitRunnable {
    private GameScoreboard scoreboard;
    private CvCArena arena;
    private int countdown;
    private boolean alreadyFirstRun = false;

    public CountdownTask(GameScoreboard scoreboard) {
        this.scoreboard = scoreboard;
        this.arena = scoreboard.getArena();
        this.countdown = 10;
    }

    @Override
    public void run() {
        if (!arena.getStatus().equals(ArenaStatus.COUNTDOWN)) {
            this.cancel();
            return;
        }
        if (!alreadyFirstRun) {
            arena.broadcastMessage("The game wil be started in " + countdown + " seconds!");
            arena.getPlayers().forEach(this::playTickSound);
            alreadyFirstRun = true;
        }
        if (countdown <= 5) {
            arena.getPlayers().forEach(this::playTickSound);
            arena.broadcastMessage("The game wil be started in " + countdown + (countdown == 1 ? " second!" : " seconds!"));
        }
        for (CvCPlayer player : arena.getPlayers()) {
            scoreboard.updateCountdown(player, countdown);
        }
        countdown--;
        if (countdown < 0) {
            if (arena.getArenaController() instanceof CountdownSystem)
                ((CountdownSystem) arena.getArenaController()).countdownFinished();
            this.cancel();
        }
    }

    private void playTickSound(CvCPlayer player) {
        Sound sound = new Sound(SoundEffect.NOTE_HAT, 1.0F, 1.0F);
        sound.playSound(player.getPlayer());
    }
}

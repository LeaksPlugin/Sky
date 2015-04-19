package com.talesdev.copsandcrims.arena.data;

import com.talesdev.copsandcrims.arena.game.TDMArenaController;
import com.talesdev.copsandcrims.player.CvCPlayer;
import com.talesdev.core.player.ActionBar;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * RespawnTask
 */
public class RespawnTask extends BukkitRunnable {
    private int seconds = 5;
    private CvCPlayer player;
    private TDMArenaController arenaController;

    public RespawnTask(TDMArenaController controller, CvCPlayer player) {
        seconds = 5;
        this.player = player;
        this.arenaController = controller;
    }

    @Override
    public void run() {
        ActionBar actionBar = new ActionBar(ChatColor.GREEN + "Respawning in " + seconds + " " + (seconds == 1 ? "second" : "seconds"));
        actionBar.send(player.getPlayer());
        seconds--;
        if (seconds < 0) {
            arenaController.tpToTeamSpawn(player.getPlayer());
            player.getPlayer().setGameMode(GameMode.SURVIVAL);
            this.cancel();
            return;
        }
    }
}

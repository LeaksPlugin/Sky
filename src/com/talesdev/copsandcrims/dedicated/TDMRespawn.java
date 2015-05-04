package com.talesdev.copsandcrims.dedicated;

import com.talesdev.core.TalesCore;
import com.talesdev.core.arena.GameArena;
import com.talesdev.core.arena.GameState;
import com.talesdev.core.player.CleanedPlayer;
import com.talesdev.core.player.CorePlayer;
import com.talesdev.core.player.message.ActionBar;
import com.talesdev.core.server.TimedTask;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

/**
 * TDM Respawn message
 *
 * @author MoKunz
 */
public class TDMRespawn extends TimedTask {
    private GameArena gameArena;
    private Player player;
    private int seconds;

    public TDMRespawn(GameArena gameArena, Player player) {
        super(gameArena.getPlugin(), 5);
        this.seconds = 5;
        this.gameArena = gameArena;
        this.player = player;
    }

    @Override
    protected void timePassed() {
        ActionBar actionBar = new ActionBar(ChatColor.GREEN + "Respawning in " + seconds + " " + (seconds == 1 ? "second" : "seconds"));
        actionBar.send(player.getPlayer());
        if (seconds <= 1 || gameArena.getGameState().equals(GameState.END)) {
            player.getPlayer().setGameMode(GameMode.SURVIVAL);
            gameArena.getArenaSpawn().spawn(player);
            ActionBar bar = new ActionBar(ChatColor.GREEN + "You are now respawn");
            bar.send(player);
            CleanedPlayer cleanedPlayer = new CleanedPlayer(player);
            cleanedPlayer.clean();
            String name = gameArena.getTeam().getTeam(player).getName();
            TDMKitItem item = new TDMKitItem(player, name == null ? "" : name);
            item.give();
            CorePlayer corePlayer = TalesCore.getPlugin().getCorePlayer(player);
            corePlayer.getPlayerDamage().setGod(true);
            corePlayer.getPlayerDamage().clearDamageData();
            gameArena.getScheduler().runTaskLater(gameArena.getPlugin(), () -> {
                corePlayer.getPlayerDamage().setGod(false);
            }, 85);
            cancel();
        }
        seconds--;
    }
}

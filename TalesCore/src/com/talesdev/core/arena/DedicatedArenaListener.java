package com.talesdev.core.arena;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author MoKunz
 */
public class DedicatedArenaListener implements Listener {
    private GameArena gameArena;

    public DedicatedArenaListener(GameArena gameArena) {
        this.gameArena = gameArena;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        gameArena.leave(event.getPlayer());
    }

    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent event) {
        if (!gameArena.getGameState().canJoin()) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatColor.RED + "The game has already started!");
        } else if (gameArena.playing() >= gameArena.getMaxPlayers()) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_FULL, ChatColor.RED + "Server is full!");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().teleport(event.getPlayer().getWorld().getSpawnLocation());
        if (!gameArena.isLocked()) {
            gameArena.join(event.getPlayer());
        }
    }

}

package com.talesdev.copsandcrims;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Cops And Crimes Listener
 * @author MoKunz
 */
public class CopsAndCrimsListener implements Listener {
    private CopsAndCrims plugin;

    public CopsAndCrimsListener(CopsAndCrims copsAndCrims) {
        this.plugin = copsAndCrims;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.getServerCvCPlayer().addNewPlayer(plugin.getServerCvCPlayer().createPlayer(event.getPlayer()));
        // DEBUG
        System.out.println(plugin.getServerCvCPlayer().getAllPlayers());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.getServerCvCPlayer().removePlayer(plugin.getServerCvCPlayer().getPlayer(event.getPlayer()));
        // DEBUG
        System.out.println(plugin.getServerCvCPlayer().getAllPlayers());
    }
}

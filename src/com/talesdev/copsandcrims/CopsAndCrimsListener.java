package com.talesdev.copsandcrims;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

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
    public void onClick(PlayerInteractEvent event) {

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.getServerCvCPlayer().addNewPlayer(plugin.getServerCvCPlayer().createPlayer(event.getPlayer()));
    }
}

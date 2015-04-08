package com.talesdev.copsandcrims;

import com.talesdev.copsandcrims.player.CvCPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
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
        plugin.getServerCvCPlayer().addNewPlayer(plugin.getServerCvCPlayer().loadUserData(event.getPlayer()));
        // DEBUG
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.getServerCvCPlayer().saveUserData(plugin.getServerCvCPlayer().getPlayer(event.getPlayer()));
        plugin.getServerCvCPlayer().removePlayer(plugin.getServerCvCPlayer().getPlayer(event.getPlayer()));
        // DEBUG
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        CvCPlayer player = plugin.getServerCvCPlayer().getPlayer(event.getPlayer());
        if (player != null) {
            player.setWalking(true);
            player.updateLastWalkingTime();
        }
    }
}

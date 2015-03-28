package com.talesdev.copsandcrims;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Cops And Crimes Listener
 * @author MoKunz
 */
public class CopsAndCrimsListener implements Listener {
    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (event.getItem() != null) {
                if (event.getItem().getType().equals(Material.BLAZE_ROD)) {

                }
            }
        }
    }
}

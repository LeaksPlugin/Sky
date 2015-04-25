package com.talesdev.core.arena.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.Plugin;

/**
 * Player Listener for arena usage
 *
 * @author MoKunz
 */
public class PlayerListener implements Listener {
    private Plugin plugin;

    public PlayerListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void chatEvent(AsyncPlayerChatEvent event) {

    }

    @EventHandler
    public void preLoginEvent(AsyncPlayerPreLoginEvent event) {

    }

    @EventHandler
    public void loginEvent(PlayerLoginEvent event) {

    }

    @EventHandler
    public void joinEvent(PlayerJoinEvent event) {

    }

    @EventHandler
    public void quit(PlayerQuitEvent event) {

    }

    @EventHandler
    public void interactEvent(PlayerInteractEvent event) {

    }

    @EventHandler
    public void changeWorldEvent(PlayerChangedWorldEvent event) {

    }

    @EventHandler
    public void commandEvent(PlayerCommandPreprocessEvent event) {

    }

    @EventHandler
    public void dropItemEvent(PlayerDropItemEvent event) {

    }


    @EventHandler
    public void playerExpChangeEvent(PlayerExpChangeEvent event) {

    }

    @EventHandler
    public void playerFishEvent(PlayerFishEvent event) {

    }

    @EventHandler
    public void consumeEvent(PlayerItemConsumeEvent event) {

    }

    @EventHandler
    public void itemHeldEvent(PlayerItemHeldEvent event) {

    }

    @EventHandler
    public void interactEntity(PlayerInteractEntityEvent event) {

    }

    @EventHandler
    public void levelChange(PlayerLevelChangeEvent event) {

    }

    @EventHandler
    public void move(PlayerMoveEvent event) {

    }

    @EventHandler
    public void pickupItem(PlayerPickupItemEvent event) {

    }

    @EventHandler
    public void portal(PlayerPortalEvent event) {

    }

    @EventHandler
    public void respawn(PlayerRespawnEvent event) {

    }

    @EventHandler
    public void teleport(PlayerTeleportEvent event) {

    }

}

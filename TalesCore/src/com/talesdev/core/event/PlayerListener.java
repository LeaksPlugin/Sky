package com.talesdev.core.event;

import com.talesdev.core.TalesCore;
import com.talesdev.core.player.CorePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.*;

/**
 * Player Listener for CorePlayer
 *
 * @author MoKunz
 */
public class PlayerListener implements Listener {
    private TalesCore plugin;

    public PlayerListener(TalesCore plugin) {
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
    public void kick(PlayerKickEvent event) {

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
    public void playerDeath(PlayerDeathEvent event) {

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

    @EventHandler(priority = EventPriority.MONITOR)
    public void move(PlayerMoveEvent event) {
        // CorePlayer PlayerLocation
        double dX = Math.abs(event.getTo().getX() - event.getFrom().getX());
        double dZ = Math.abs(event.getTo().getZ() - event.getFrom().getZ());
        double xzDistance = dX * dX + dZ * dZ;
        if (xzDistance > 0) {
            CorePlayer corePlayer = plugin.getCorePlayer(event.getPlayer());
            corePlayer.getPlayerLocation().setWalking(true);
            corePlayer.getPlayerLocation().updateLastWalkingTime();
        }
    }

    @EventHandler
    public void pickupItem(PlayerPickupItemEvent event) {

    }

    @EventHandler
    public void portal(PlayerPortalEvent event) {

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void respawn(PlayerRespawnEvent event) {

    }

    @EventHandler
    public void teleport(PlayerTeleportEvent event) {

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void damage(EntityDamageEvent event) {
        if (checkPlayer(event)) {

        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void damageByEntity(EntityDamageByEntityEvent event) {
        if (checkPlayer(event)) {

        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void damageByBlock(EntityDamageByBlockEvent event) {
        if (checkPlayer(event)) {

        }
    }

    @EventHandler
    public void combust(EntityCombustEvent event) {
        if (checkPlayer(event)) {

        }
    }

    @EventHandler
    public void regenHealth(EntityRegainHealthEvent event) {
        if (checkPlayer(event)) {

        }
    }

    @EventHandler
    public void shootBow(EntityShootBowEvent event) {
        if (checkPlayer(event)) {

        }
    }

    @EventHandler
    public void foodLevelChange(FoodLevelChangeEvent event) {
        if (checkPlayer(event)) {

        }
    }

    @EventHandler
    public void launchProjectile(ProjectileLaunchEvent event) {
        if (checkPlayer(event)) {

        }
    }

    @EventHandler
    public void projectileHit(ProjectileHitEvent event) {
        if (checkPlayer(event)) {

        }
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {

    }

    @EventHandler
    public void blockDamage(BlockDamageEvent event) {

    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {

    }

    @EventHandler
    public void signChange(SignChangeEvent event) {

    }

    @EventHandler
    public void hangingPlace(HangingPlaceEvent event) {

    }

    @EventHandler
    public void hangingBreak(HangingBreakByEntityEvent event) {
        if (event.getRemover().getType().equals(EntityType.PLAYER)) {

        }
    }

    private boolean checkPlayer(EntityEvent entityEvent) {
        if (entityEvent.getEntityType().equals(EntityType.PLAYER)) {
            return true;
        }
        return false;
    }

}

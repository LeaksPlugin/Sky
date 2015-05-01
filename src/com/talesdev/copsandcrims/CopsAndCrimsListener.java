package com.talesdev.copsandcrims;

import com.talesdev.copsandcrims.player.CvCPlayer;
import com.talesdev.copsandcrims.player.PlayerLastDamage;
import com.talesdev.core.TalesCore;
import com.talesdev.core.entity.DamageData;
import com.talesdev.core.player.CorePlayer;
import com.talesdev.core.player.LastPlayerDamage;
import org.bukkit.Art;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Cops And Crimes Listener
 *
 * @author MoKunz
 */
public class CopsAndCrimsListener implements Listener {
    private CopsAndCrims plugin;

    public CopsAndCrimsListener(CopsAndCrims copsAndCrims) {
        this.plugin = copsAndCrims;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.getServerCvCPlayer().addNewPlayer(plugin.getServerCvCPlayer().loadUserData(event.getPlayer()));
        // update health
        plugin.getServer().getScheduler().runTaskLater(plugin, () ->
                plugin.getServer().getOnlinePlayers().forEach(player -> player.setHealth(player.getHealth()))
                , 1);
        event.getPlayer().teleport(event.getPlayer().getWorld().getSpawnLocation());
        if (!plugin.getTdmGameArena().isLocked()) {
            plugin.getTdmGameArena().join(event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.getServerCvCPlayer().saveUserData(plugin.getServerCvCPlayer().getPlayer(event.getPlayer()));
        plugin.getServerCvCPlayer().removePlayer(plugin.getServerCvCPlayer().getPlayer(event.getPlayer()));
        plugin.getTdmGameArena().leave(event.getPlayer());
        // DEBUG
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        CvCPlayer player = plugin.getServerCvCPlayer().getPlayer(event.getPlayer());
        if (player != null) {
            double dX = Math.abs(event.getTo().getX() - event.getFrom().getX());
            double dZ = Math.abs(event.getTo().getZ() - event.getFrom().getZ());
            double xzDistance = dX * dX + dZ * dZ;
            if (xzDistance > 0) {
                player.setWalking(true);
                player.updateLastWalkingTime();
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        // maybe work or not, idk
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageEvent event) {
        LastPlayerDamage damage = new LastPlayerDamage(event.getEntity(), plugin);
        PlayerLastDamage lastDamage = new PlayerLastDamage(event.getCause());
        lastDamage.addAttachment("Weapon", null);
        lastDamage.addAttachment("Bullet", null);
        lastDamage.addAttachment("HeadShot", false);
        damage.setLastDamage(lastDamage);
        if (event.getEntity() instanceof Player) {
            CorePlayer player = TalesCore.getPlugin().getCorePlayer((Player) event.getEntity());
            DamageData damageData = new DamageData(event);
            damageData.addAttachment("Weapon", null);
            damageData.addAttachment("Bullet", null);
            damageData.addAttachment("HeadShot", null);
            player.getPlayerDamage().damage(damageData);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Painting) {
            Painting painting = ((Painting) event.getRightClicked());
            int id = painting.getArt().getId();
            id++;
            if (id > 25) id = 0;
            painting.setArt(Art.getById(id));
        }
    }
}

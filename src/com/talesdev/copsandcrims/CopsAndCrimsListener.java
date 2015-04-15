package com.talesdev.copsandcrims;

import com.talesdev.copsandcrims.player.CvCPlayer;
import com.talesdev.copsandcrims.player.PlayerLastDamage;
import com.talesdev.core.player.LastPlayerDamage;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof LivingEntity) {
            LivingEntity damager = ((LivingEntity) event.getDamager());
            LastPlayerDamage damage = new LastPlayerDamage(event.getEntity(), plugin);
            PlayerLastDamage lastDamage = new PlayerLastDamage(damager);
            lastDamage.setDamageCause(event.getCause());
            lastDamage.addAttachment("Weapon", null);
            lastDamage.addAttachment("Bullet", null);
            lastDamage.addAttachment("HeadShot", false);
            damage.setLastDamage(lastDamage);
        }
    }
}

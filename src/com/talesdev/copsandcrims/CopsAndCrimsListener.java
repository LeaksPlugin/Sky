package com.talesdev.copsandcrims;

import com.talesdev.copsandcrims.weapon.bullet.Bullet;
import com.talesdev.copsandcrims.weapon.bullet.BulletTask;
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
                    Bullet bullet = new Bullet(event.getPlayer(), null, 4);
                    bullet.setRayParameter(2000, 0.05, 4);
                    (new BulletTask(bullet, 3)).runTaskTimer(CopsAndCrims.getPlugin(), 0, 1);
                }
            }
        }
    }
}

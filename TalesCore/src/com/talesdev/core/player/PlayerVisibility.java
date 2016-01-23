package com.talesdev.core.player;

import com.talesdev.core.world.NMSRayTrace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * Player visibility
 *
 * @author MoKunz
 */
public class PlayerVisibility {
    private Player player;

    public PlayerVisibility(Player player) {
        this.player = player;
    }

    public boolean canSee(Entity entity) {
        Vector playerVec = player.getEyeLocation().toVector();
        Vector entityVec = (
                (entity instanceof LivingEntity) ?
                        ((LivingEntity) entity).getEyeLocation().toVector() :
                        entity.getLocation().toVector()
        );
        NMSRayTrace rayTrace = NMSRayTrace.rayTrace(player.getWorld(), playerVec, entityVec);
        return rayTrace.isHit() && rayTrace.getEntity() != null && rayTrace.getEntity().getEntityId() == entity.getEntityId();
    }

    public Player player() {
        return player;
    }
}

package com.talesdev.core.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Player view
 *
 * @author MoKunz
 */
public class PlayerView {
    private Player player;

    public PlayerView(Player player) {
        this.player = player;
    }

    public boolean canSee(Entity entity) {
        return player.hasLineOfSight(entity);
    }

    public List<Entity> entities() {
        return player.getWorld().getEntities().stream().filter(this::canSee).collect(Collectors.toList());
    }

    public Player getPlayer() {
        return player;
    }
}

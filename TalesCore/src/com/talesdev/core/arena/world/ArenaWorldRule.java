package com.talesdev.core.arena.world;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Arena world rule
 *
 * @author MoKunz
 */
public class ArenaWorldRule implements Listener {
    private ArenaWorld arenaWorld;

    public ArenaWorldRule(ArenaWorld arenaWorld) {
        this.arenaWorld = arenaWorld;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (arenaWorld.insideBound(event.getBlock().getLocation().toVector())) {
            event.setCancelled(true);
        }
    }

    public ArenaWorld getArenaWorld() {
        return arenaWorld;
    }
}

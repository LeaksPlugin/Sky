package com.talesdev.core.arena;

import org.bukkit.entity.Player;

/**
 * Arena spawn
 *
 * @author MoKunz
 */
public interface ArenaSpawn {
    public void spawn(GameArena gameArena);

    public void spawn(Player player);
}

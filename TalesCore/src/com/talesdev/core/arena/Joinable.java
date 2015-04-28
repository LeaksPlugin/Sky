package com.talesdev.core.arena;

import org.bukkit.entity.Player;

/**
 * Represent joinable arena
 *
 * @author MoKunz
 */
public interface Joinable {
    boolean join(Player player);

    boolean leave(Player player);
}

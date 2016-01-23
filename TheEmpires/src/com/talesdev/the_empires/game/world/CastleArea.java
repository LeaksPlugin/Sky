package com.talesdev.the_empires.game.world;

import org.bukkit.entity.Player;

/**
 * @author MoKunz
 */
public interface CastleArea {
    Empire getEmpire();

    boolean canBreak(Player player);
}

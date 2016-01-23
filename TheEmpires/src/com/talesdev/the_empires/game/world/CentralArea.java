package com.talesdev.the_empires.game.world;

import com.talesdev.core.arena.GameArena;
import org.bukkit.Location;

/**
 * @author MoKunz
 */
public interface CentralArea {
    Location getLocation();

    GameArena getGameArena();
}

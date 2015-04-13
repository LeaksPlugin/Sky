package com.talesdev.copsandcrims.arena;

import com.talesdev.copsandcrims.player.CvCPlayer;
import org.bukkit.Location;

/**
 * Arena location
 *
 * @author MoKunz
 */
public interface ArenaSpawnLocation {
    public Location getSpawnLocation(CvCArena arena, CvCPlayer player);
}

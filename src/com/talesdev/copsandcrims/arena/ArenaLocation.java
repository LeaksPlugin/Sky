package com.talesdev.copsandcrims.arena;

import com.talesdev.copsandcrims.player.CvCPlayer;
import org.bukkit.Location;

/**
 * Arena location
 *
 * @author MoKunz
 */
public interface ArenaLocation {
    public Location getLobbyLocation(CvCArena arena, CvCPlayer player);

    public Location getSpawnLocation(CvCArena arena, CvCPlayer player);

    public Location getEndLocation(CvCArena arena, CvCPlayer player);
}

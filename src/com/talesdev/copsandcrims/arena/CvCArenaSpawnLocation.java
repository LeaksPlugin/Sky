package com.talesdev.copsandcrims.arena;

import com.talesdev.copsandcrims.player.CvCPlayer;
import org.bukkit.Location;

/**
 * CvC Arena location
 *
 * @author MoKunz
 */
public class CvCArenaSpawnLocation implements ArenaSpawnLocation {

    @Override
    public Location getSpawnLocation(CvCArena arena, CvCPlayer player) {
        return player.getPlayer().getLocation();
    }
}

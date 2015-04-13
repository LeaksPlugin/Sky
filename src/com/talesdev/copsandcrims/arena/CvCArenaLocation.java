package com.talesdev.copsandcrims.arena;

import com.talesdev.copsandcrims.player.CvCPlayer;
import org.bukkit.Location;

/**
 * CvC Arena location
 *
 * @author MoKunz
 */
public class CvCArenaLocation implements ArenaLocation {

    @Override
    public Location getLobbyLocation(CvCArena arena, CvCPlayer player) {
        return null;
    }

    @Override
    public Location getSpawnLocation(CvCArena arena, CvCPlayer player) {
        return null;
    }

    @Override
    public Location getEndLocation(CvCArena arena, CvCPlayer player) {
        return null;
    }
}

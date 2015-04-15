package com.talesdev.copsandcrims.arena.system;

import com.talesdev.copsandcrims.player.CvCPlayer;
import org.bukkit.Location;

/**
 * Arena location
 *
 * @author MoKunz
 */
public interface ArenaSpawnLocation {
    public Location getLobbyLocation();

    public void setLobbyLocation(Location location);

    public Location getEndLocation();

    public void setEndLocation(Location location);
}

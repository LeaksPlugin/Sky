package com.talesdev.copsandcrims.bomb;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * C4
 *
 * @author MoKunz
 */
public interface C4 {
    Location getLocation();

    void place(Player player);

    void countdown(int time);

    void explode();

    void defuse(Player player);

    void remove(Player player);
}

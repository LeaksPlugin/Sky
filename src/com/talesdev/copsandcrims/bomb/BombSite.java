package com.talesdev.copsandcrims.bomb;

import org.bukkit.Location;

/**
 * Bomb site
 *
 * @author MoKunz
 */
public interface BombSite {
    String getName();

    Location getStart();

    Location getEnd();

    C4 getBomb();

    void placeBomb(C4 c4);

    void removeBomb();

    boolean hasBomb();
}

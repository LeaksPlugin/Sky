package com.talesdev.core.arena.world;

import com.talesdev.core.arena.GameArena;
import org.bukkit.util.Vector;

/**
 * Represent world of arena
 *
 * @author MoKunz
 */
public interface ArenaWorld<T extends GameArena> {
    /**
     * Get the name of this arena
     *
     * @return the map name of this arena
     */
    String getName();

    /**
     * Get minimum bound of this arena
     *
     * @return minimum bound of this arena
     */
    Vector minBound();

    /**
     * Get maximum bound of this arena
     *
     * @return maximum bound of this arena
     */
    Vector maxBound();

    /**
     * Check if given vector is inside arena bound
     *
     * @param vector A vector to be checked
     * @return true if is inside otherwise false
     */
    boolean insideBound(Vector vector);

    /**
     * Get the GameArena of this map
     *
     * @return the GameArena of this map
     */
    T getArena();
}

package com.talesdev.copsandcrims.dedicated;

import com.talesdev.core.arena.world.ArenaWorld;
import org.bukkit.util.Vector;

/**
 * TDM Arena World
 *
 * @author MoKunz
 */
public class TDMArenaWorld implements ArenaWorld<TDMGameArena> {
    private String mapName = "";
    private Vector minBound;
    private Vector maxBound;
    private TDMGameArena gameArena;

    public TDMArenaWorld(TDMGameArena gameArena) {
        this.gameArena = gameArena;
    }

    @Override
    public String getName() {
        return mapName;
    }

    public void setName(String mapName) {
        this.mapName = mapName;
    }

    @Override
    public Vector minBound() {
        return minBound;
    }

    @Override
    public Vector maxBound() {
        return maxBound;
    }

    @Override
    public boolean insideBound(Vector vector) {
        return vector.isInAABB(minBound(), maxBound());
    }

    @Override
    public TDMGameArena getArena() {
        return gameArena;
    }

    public void setMinBound(Vector minBound) {
        this.minBound = minBound;
    }

    public void setMaxBound(Vector maxBound) {
        this.maxBound = maxBound;
    }
}

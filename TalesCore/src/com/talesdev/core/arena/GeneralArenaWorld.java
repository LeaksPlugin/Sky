package com.talesdev.core.arena;

import com.talesdev.core.arena.world.ArenaWorld;
import org.bukkit.util.Vector;

/**
 * @author MoKunz
 */
public class GeneralArenaWorld implements ArenaWorld {
    private String mapName = "Untitled";
    private String mapAuthor = "Unknown";
    private Vector minBound;
    private Vector maxBound;
    private GameArena gameArena;

    public GeneralArenaWorld(GameArena gameArena, String mapName, String mapAuthor, Vector minBound, Vector maxBound) {
        if (mapName != null) this.mapName = mapName;
        if (mapAuthor != null) this.mapAuthor = mapAuthor;
        this.gameArena = gameArena;
        this.minBound = minBound;
        this.maxBound = maxBound;
    }

    public GeneralArenaWorld(GameArena gameArena) {
        this(gameArena, "Untitled", "Unknown", new Vector(0, 0, 0), new Vector(0, 0, 0));
    }

    public GeneralArenaWorld(GameArena gameArena, String mapName, String mapAuthor) {
        this(gameArena, mapName, mapAuthor, new Vector(0, 0, 0), new Vector(0, 0, 0));
    }

    @Override
    public String getName() {
        return mapName;
    }

    public void setName(String mapName) {
        this.mapName = mapName;
    }

    @Override
    public String getAuthor() {
        return mapAuthor;
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
    public GameArena getArena() {
        return gameArena;
    }

    public void setMinBound(Vector minBound) {
        this.minBound = minBound;
    }

    public void setMaxBound(Vector maxBound) {
        this.maxBound = maxBound;
    }
}

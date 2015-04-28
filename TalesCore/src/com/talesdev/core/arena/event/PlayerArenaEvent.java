package com.talesdev.core.arena.event;

import com.talesdev.core.arena.GameArena;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

/**
 * Event related to player in arena
 *
 * @author MoKunz
 */
public abstract class PlayerArenaEvent extends Event implements ArenaInterface {
    protected GameArena gameArena;
    protected Player player;

    public PlayerArenaEvent(GameArena gameArena, Player player) {
        this.gameArena = gameArena;
        this.player = player;
    }

    public GameArena getGameArena() {
        return gameArena;
    }

    public Player getPlayer() {
        return player;
    }
}

package com.talesdev.core.arena.event;

import com.talesdev.core.arena.GameArena;
import org.bukkit.entity.Player;

/**
 * Event related to player in arena
 *
 * @author MoKunz
 */
public abstract class PlayerArenaEvent extends ArenaEvent {
    protected Player player;

    public PlayerArenaEvent(GameArena gameArena, Player player) {
        super(gameArena);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}

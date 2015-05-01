package com.talesdev.core.arena.event;

import com.talesdev.core.TalesCore;
import com.talesdev.core.arena.GameArena;
import com.talesdev.core.arena.GameArenaListener;
import com.talesdev.core.player.CorePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

/**
 * General arena listener
 *
 * @author MoKunz
 */
public abstract class GeneralArenaListener<T extends GameArena> implements GameArenaListener {

    private T gameArena;

    public GeneralArenaListener(T gameArena) {
        this.gameArena = gameArena;
    }

    public T getGameArena() {
        return gameArena;
    }

    protected CorePlayer getCorePlayer(Player player) {
        return TalesCore.getPlugin().getCorePlayer(player);
    }

    protected boolean isArena(ArenaInterface arenaInterface) {
        return arenaInterface.getGameArena().getArenaName().equals(getGameArena().getArenaName());
    }

    @EventHandler
    public void onArenaJoin(PlayerJoinArenaEvent event) {

    }
}

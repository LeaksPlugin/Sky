package com.talesdev.core.arena;

import org.bukkit.command.CommandExecutor;

/**
 * Arena command executor
 *
 * @author MoKunz
 */
public abstract class ArenaCommandExecutor<T extends GameArena> implements CommandExecutor {
    private T gameArena;

    public ArenaCommandExecutor(T gameArena) {
        this.gameArena = gameArena;
    }

    public T getGameArena() {
        return gameArena;
    }
}

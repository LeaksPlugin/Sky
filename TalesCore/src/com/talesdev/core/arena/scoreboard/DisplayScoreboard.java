package com.talesdev.core.arena.scoreboard;

import com.talesdev.core.arena.GameArena;
import org.bukkit.entity.Player;

/**
 * In game scoreboard
 *
 * @author MoKunz
 */
public interface DisplayScoreboard<T extends GameArena> {
    void start(Player player);

    void update(Player player);

    Class<? extends DisplayScoreboard> getType();

    T getGameArena();
}

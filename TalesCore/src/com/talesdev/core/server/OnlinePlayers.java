package com.talesdev.core.server;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collection;

/**
 * Online players
 *
 * @author MoKunz
 */
public class OnlinePlayers {
    private Collection<? extends Player> players;

    public OnlinePlayers() {
        players = Bukkit.getOnlinePlayers();
    }

    public final OnlinePlayers except(Player... player) {
        Arrays.asList(player).forEach(players::remove);
        return this;
    }

    public Collection<? extends Player> get() {
        return players;
    }
}

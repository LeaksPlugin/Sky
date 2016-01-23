package com.talesdev.the_empires.game.world;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Set;

/**
 * @author MoKunz
 */
public interface Empire {
    Location getSpawn();

    Vector minBond();

    Vector maxBond();

    Set<Player> players();

    void addPlayer(Player player);

    void removePlayer(Player player);

    boolean canJoin(Player player);

    CastleArea getCastleArea();
}

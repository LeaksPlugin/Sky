package com.talesdev.the_empires.game.world;

import com.talesdev.the_empires.game.TheEmpiresGame;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

/**
 * @author MoKunz
 */
public class DefaultEmpire implements Empire {
    private TheEmpiresGame game;
    private Vector min, max;
    private String teamName;
    private Set<Player> players;
    private Location spawnLocation;

    public DefaultEmpire(TheEmpiresGame game, Vector min, Vector max, String teamName, Location spawnLocation) {
        this.min = min;
        this.max = max;
        this.game = game;
        this.teamName = teamName;
        this.spawnLocation = spawnLocation;
        this.players = new HashSet<>();
    }

    @Override
    public Location getSpawn() {
        return spawnLocation;
    }

    @Override
    public Vector minBond() {
        return min;
    }

    @Override
    public Vector maxBond() {
        return max;
    }

    @Override
    public Set<Player> players() {
        return new HashSet<>(players);
    }

    @Override
    public void addPlayer(Player player) {
        players.add(player);
    }

    @Override
    public void removePlayer(Player player) {
        players.remove(player);
    }

    @Override
    public boolean canJoin(Player player) {
        return false;
    }

    @Override
    public CastleArea getCastleArea() {
        return null;
    }
}

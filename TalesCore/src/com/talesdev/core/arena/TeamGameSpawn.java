package com.talesdev.core.arena;

import com.talesdev.core.world.LocationString;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scoreboard.Team;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Team based game arena spawn
 *
 * @author MoKunz
 */
public class TeamGameSpawn implements ArenaSpawn {
    private GameArena gameArena;
    private Map<String, List<Location>> spawnMap;

    public TeamGameSpawn(GameArena gameArena) {
        this.gameArena = gameArena;
        this.spawnMap = new HashMap<>();
    }

    public Map<String, List<Location>> getSpawnMap() {
        return new HashMap<>(spawnMap);
    }

    private void initList(String key) {
        if (spawnMap.get(key) == null) {
            spawnMap.put(key, new ArrayList<>());
        }
    }

    public List<Location> getSpawnList(String teamName) {
        return new ArrayList<>(spawnMap.get(teamName));
    }

    public void setSpawn(String teamName, List<Location> spawn) {
        spawnMap.put(teamName, spawn == null ? new ArrayList<>() : spawn);
    }

    public void addSpawn(String teamName, Location... locations) {
        initList(teamName);
        spawnMap.get(teamName).addAll(Arrays.asList(locations));
    }

    public void setSpawn(String teamName, int index, Location location) {
        initList(teamName);
        spawnMap.get(teamName).set(index, location);
    }

    public void removeSpawn(String teamName, int index) {
        initList(teamName);
        spawnMap.get(teamName).remove(index);
    }

    public void clearSpawn(String teamName) {
        initList(teamName);
        spawnMap.get(teamName).clear();
    }

    private Team teamOf(Player player) {
        for (Team team : gameArena.getTeam().getTeamList()) {
            if (team.hasPlayer(player)) {
                return team;
            }
        }
        return null;
    }

    public void readFromConfig(FileConfiguration config) {
        for (Team team : getGameArena().getTeam().getTeamList()) {
            readTeam(config, team.getName());
        }
    }

    private void readTeam(FileConfiguration config, String teamName) {
        List<String> stringList = config.getStringList("spawn." + teamName);
        List<Location> locationList = stringList.stream().map(
                locString -> LocationString.fromString(locString).getLocation()).
                collect(Collectors.toList());
        setSpawn(teamName, locationList);
    }

    public void spawn(Player player) {
        Team team = teamOf(player);
        if (team != null) {
            List<Location> spawnList = getSpawnList(team.getName());
            if (spawnList.size() > 0) {
                Collections.shuffle(spawnList);
                Location location = spawnList.get(new Random().nextInt(spawnList.size()));
                Location newLoc = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
                player.teleport(newLoc, PlayerTeleportEvent.TeleportCause.PLUGIN);
            }
        }
    }

    @Override
    public void spawn(GameArena gameArena) {
        this.gameArena.getPlayerSet().forEach(this::spawn);
    }

    public GameArena getGameArena() {
        return gameArena;
    }
}

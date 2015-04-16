package com.talesdev.copsandcrims.arena;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.ServerCvCArena;
import com.talesdev.copsandcrims.arena.data.ArenaStatus;
import com.talesdev.copsandcrims.arena.system.CvCArenaController;
import com.talesdev.copsandcrims.player.CvCPlayer;
import com.talesdev.core.config.ConfigFile;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * CvC Arena
 *
 * @author MoKunz
 */
public class CvCArena {
    // Player collection
    private List<CvCPlayer> players;
    // Arena config file
    private ConfigFile configFile;
    // Plugin reference
    private CopsAndCrims plugin;
    // Current arena status
    private ArenaStatus status;
    // Arena name
    private String arenaName;
    // Reference to host (server) of this arena
    private ServerCvCArena serverArena;
    // lobby stuff
    private Location lobbyLocation;
    private Location endLocation;
    // controller
    private CvCArenaController arenaController;

    public CvCArena(ServerCvCArena serverArena, String arenaName, CvCArenaController controller) {
        this.configFile = new ConfigFile("plugins/CopsAndCrims/arena/" + arenaName + ".yml");
        this.serverArena = serverArena;
        this.plugin = CopsAndCrims.getPlugin();
        this.players = new ArrayList<>();
        this.arenaName = arenaName;
        this.arenaController = controller;
        this.arenaController.setControlledArena(this);
        plugin.getServer().getPluginManager().registerEvents(controller, plugin);
    }

    public void addPlayer(CvCPlayer player) {
        players.add(player);
    }

    public CvCPlayer getPlayer(Player player) {
        for (CvCPlayer cPlayer : getPlayers()) {
            if (cPlayer.getPlayer().getName().equals(player.getName())) {
                return cPlayer;
            }
        }
        return null;
    }

    public CvCArenaController getArenaController() {
        return arenaController;
    }

    public boolean hasPlayer(CvCPlayer player) {
        return players.contains(player);
    }

    public void removePlayer(CvCPlayer player) {
        players.remove(player);
    }

    public String getArenaName() {
        return arenaName;
    }

    public List<CvCPlayer> getPlayers() {
        return players;
    }

    public ArenaStatus getStatus() {
        return status;
    }

    public void setStatus(ArenaStatus status) {
        this.status = status;
    }

    public ServerCvCArena getServerArena() {
        return serverArena;
    }

    public FileConfiguration getConfig() {
        return configFile.getConfig();
    }

    public void shutDown() {
        save();
    }

    public void load() {
        this.configFile.load();
    }

    public void save() {
        this.configFile.save();
    }

    public Location getLobbyLocation() {
        return lobbyLocation;
    }

    public void setLobbyLocation(Location lobbyLocation) {
        this.lobbyLocation = lobbyLocation;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }
}

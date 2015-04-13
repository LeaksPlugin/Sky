package com.talesdev.copsandcrims.arena;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.ServerCvCArena;
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
    // location stuff
    private Location lobbyLocation = null;

    public CvCArena(ServerCvCArena serverArena, String arenaName, CvCArenaController controller) {
        this.configFile = new ConfigFile("plugins/CopsAndCrims/arena/" + arenaName + ".yml");
        this.serverArena = serverArena;
        plugin = CopsAndCrims.getPlugin();
        this.players = new ArrayList<>();
        this.arenaName = arenaName;
        controller.setControlledArena(this);
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
        this.configFile.save();
    }
}

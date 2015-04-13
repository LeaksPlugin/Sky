package com.talesdev.copsandcrims.arena;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.player.CvCPlayer;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

/**
 * CvC Arena
 *
 * @author MoKunz
 */
public class CvCArena {
    private List<CvCPlayer> players;
    private Scoreboard scoreboard;
    private CopsAndCrims plugin;
    private String arenaName;

    public CvCArena(String arenaName, CvCArenaController controller) {
        plugin = CopsAndCrims.getPlugin();
        this.players = new ArrayList<>();
        this.arenaName = arenaName;
        scoreboard = plugin.getServer().getScoreboardManager().getNewScoreboard();
        controller.setControlledArena(this);
        plugin.getServer().getPluginManager().registerEvents(controller, plugin);
    }

    public void addPlayer(CvCPlayer player) {
        players.add(player);
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
}

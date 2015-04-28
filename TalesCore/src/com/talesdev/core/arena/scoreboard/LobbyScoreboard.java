package com.talesdev.core.arena.scoreboard;

import com.talesdev.core.TalesCore;
import com.talesdev.core.player.CorePlayer;
import com.talesdev.core.scoreboard.WrappedScoreboard;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Represent a lobby scoreboard
 *
 * @author MoKunz
 */
public class LobbyScoreboard implements DisplayScoreboard {
    private String title = "";
    private String mapName = "";
    private int players = 0;
    private int maxPlayers = 0;
    private int countdown = -1;

    @Override
    public void start(Player player) {
        if (getCorePlayer(player) != null) {
            WrappedScoreboard scoreboard = getCorePlayer(player).getWrappedScoreboard();
            scoreboard.reset();
            scoreboard.setTitle(title);
            scoreboard.setLine(6, "Map : " + ChatColor.GREEN + getMapName());
            scoreboard.setLine(5, "Players : " + ChatColor.GREEN + getPlayers() + "/" + getMaxPlayers());
            scoreboard.setLine(3, "Status : " + ChatColor.GREEN + generateStatus());
            scoreboard.setLine(1, "mc.talesdev.com");
            scoreboard.setBlankLine(7, 4, 2);
            scoreboard.update();
        }
    }

    @Override
    public void update(Player player) {
        if (getCorePlayer(player) != null) {
            WrappedScoreboard scoreboard = getCorePlayer(player).getWrappedScoreboard();
        }
    }

    @Override
    public Class<? extends LobbyScoreboard> getType() {
        return getClass();
    }

    private String generateStatus() {
        if (countdown > 0) {
            return "Starting in " + countdown + " " + (countdown == 1 ? "second" : "seconds");
        } else {
            return "Waiting...";
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private CorePlayer getCorePlayer(Player player) {
        return TalesCore.getPlugin().getCorePlayer(player);
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }
}

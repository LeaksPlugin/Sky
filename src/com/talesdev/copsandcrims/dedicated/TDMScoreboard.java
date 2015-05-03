package com.talesdev.copsandcrims.dedicated;

import com.talesdev.core.TalesCore;
import com.talesdev.core.arena.scoreboard.DisplayScoreboard;
import com.talesdev.core.player.CorePlayer;
import com.talesdev.core.scoreboard.WrappedScoreboard;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

/**
 * TDM Scoreboard
 *
 * @author MoKunz
 */
public class TDMScoreboard implements DisplayScoreboard {
    private TDMGameArena gameArena;

    public TDMScoreboard(TDMGameArena gameArena) {
        this.gameArena = gameArena;
    }

    @Override
    public void start(Player player) {
        WrappedScoreboard scoreboard = getCorePlayer(player).getWrappedScoreboard();
        Team team = getGameArena().getTeam().getTeam(player);
        int ctkills = 0, tkills = 0;
        scoreboard.setMaxLine(15);
        scoreboard.setTitle(" " + ChatColor.BLUE + "CT" + ctkills + ChatColor.GOLD + "  and  " + ChatColor.RED + tkills + "TR ");
        scoreboard.setLine(14, ChatColor.GREEN + "[Objective]");
        scoreboard.setLine(13, "Get 50 team kills");
        scoreboard.setLine(12, "5:00");
        scoreboard.setLine(10, ChatColor.YELLOW + "[Stats]");
        scoreboard.setLine(9, "Kills : " + ChatColor.RED + getGameArena().getPlayerKD(player).getKills());
        scoreboard.setLine(8, "Deaths : " + ChatColor.RED + getGameArena().getPlayerKD(player).getDeaths());
        scoreboard.setLine(7, "Assists : " + ChatColor.RED + getGameArena().getPlayerKD(player).getAssists());
        scoreboard.setLine(5, "Team : " + (team.getName().equalsIgnoreCase("CounterTerrorist") ? (ChatColor.BLUE + "CT") : (ChatColor.RED + "TR")));
        scoreboard.setBlankLine(15, 11, 6, 4, 3, 2, 1);
        scoreboard.update();
    }

    @Override
    public void update(Player player) {
        WrappedScoreboard scoreboard = getCorePlayer(player).getWrappedScoreboard();
        int ctkills = getGameArena().getKills("CounterTerrorist"), tkills = getGameArena().getKills("Terrorist");
        Team team = getGameArena().getTeam().getTeam(player);
        scoreboard.setTitle(" " + ChatColor.BLUE + "CT" + ctkills + ChatColor.GOLD + "  and  " + ChatColor.RED + tkills + "TR ");
        scoreboard.setLine(12, getGameArena().getTimer().formattedTime());
        if (getGameArena().getPlayerKD(player) != null) {
            scoreboard.setLine(9, "Kills : " + ChatColor.RED + getGameArena().getPlayerKD(player).getKills());
            scoreboard.setLine(8, "Deaths : " + ChatColor.RED + getGameArena().getPlayerKD(player).getDeaths());
            scoreboard.setLine(7, "Assists : " + ChatColor.RED + getGameArena().getPlayerKD(player).getAssists());
        }
        scoreboard.setLine(5, "Team : " + (team.getName().equalsIgnoreCase("CounterTerrorist") ? (ChatColor.BLUE + "CT") : (ChatColor.RED + "TR")));
        scoreboard.update();
    }

    public CorePlayer getCorePlayer(Player player) {
        return TalesCore.getPlugin().getCorePlayer(player);
    }
    @Override
    public Class<? extends DisplayScoreboard> getType() {
        return TDMScoreboard.class;
    }

    @Override
    public TDMGameArena getGameArena() {
        return gameArena;
    }
}

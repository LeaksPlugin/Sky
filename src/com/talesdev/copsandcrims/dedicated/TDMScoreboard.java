package com.talesdev.copsandcrims.dedicated;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.player.CvCPlayer;
import com.talesdev.core.TalesCore;
import com.talesdev.core.arena.GameState;
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
        scoreboard.setLine(6, "Armor : " + getArmorString(player));
        scoreboard.setLine(4, "Team : " + (team.getName().equalsIgnoreCase("CounterTerrorist") ? (ChatColor.BLUE + "CT") : (ChatColor.RED + "TR")));
        scoreboard.setBlankLine(15, 11, 6, 4, 3, 2, 1);
        scoreboard.update();
    }

    @Override
    public void update(Player player) {
        WrappedScoreboard scoreboard = getCorePlayer(player).getWrappedScoreboard();
        int ctkills = getGameArena().getKills("CounterTerrorist"), tkills = getGameArena().getKills("Terrorist");
        Team team = getGameArena().getTeam().getTeam(player);
        // cancel if game end
        if (getGameArena().getGameState().equals(GameState.END)) {
            scoreboard.setTitle(ChatColor.RED + "TDM is now ended ");
            scoreboard.setLine(12, "00:00");
            scoreboard.update();
            return;
        }
        scoreboard.setTitle(" " + ChatColor.BLUE + "CT" + ctkills + ChatColor.GOLD + "  and  " + ChatColor.RED + tkills + "TR ");
        String timeColor = "";
        if (getGameArena().getTimer().getTime() <= 30) {
            if (getGameArena().getTimer().getTime() % 2 == 0) {
                timeColor = ChatColor.RED.toString();
            }
        }
        scoreboard.setLine(12, timeColor + getGameArena().getTimer().formattedTime());
        if (getGameArena().getPlayerKD(player) != null) {
            scoreboard.setLine(9, "Kills : " + ChatColor.RED + getGameArena().getPlayerKD(player).getKills());
            scoreboard.setLine(8, "Deaths : " + ChatColor.RED + getGameArena().getPlayerKD(player).getDeaths());
            scoreboard.setLine(7, "Assists : " + ChatColor.RED + getGameArena().getPlayerKD(player).getAssists());
        }
        scoreboard.setLine(6, "Armor : " + ChatColor.GRAY + getArmorString(player));
        scoreboard.setLine(4, "Team : " + (team.getName().equalsIgnoreCase("CounterTerrorist") ? (ChatColor.BLUE + "\u9290 - CT") : (ChatColor.RED + "\u9291 - T")));
        scoreboard.update();
    }

    public CorePlayer getCorePlayer(Player player) {
        return TalesCore.getPlugin().getCorePlayer(player);
    }

    private String getArmorString(Player player) {
        CvCPlayer cvCPlayer = CopsAndCrims.getPlugin().getServerCvCPlayer().getPlayer(player);
        return cvCPlayer.getArmorContainer().toString();
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

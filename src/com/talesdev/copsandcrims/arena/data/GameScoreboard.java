package com.talesdev.copsandcrims.arena.data;

import com.talesdev.copsandcrims.arena.CvCArena;
import com.talesdev.copsandcrims.arena.game.TDMArenaController;
import com.talesdev.copsandcrims.arena.system.ArenaJoinLeave;
import com.talesdev.copsandcrims.player.CvCPlayer;
import com.talesdev.core.math.DigitalTimer;
import com.talesdev.core.scoreboard.WrappedSidebarObjective;
import org.bukkit.ChatColor;

import java.util.List;

/**
 * Lobby scoreboard wrapper
 *
 * @author MoKunz
 */
public class GameScoreboard {
    private CvCArena arena;
    private String gameTitle;
    private String maxPlayers = "";

    public GameScoreboard(CvCArena arena, String gameTitle) {
        this.arena = arena;
        this.gameTitle = gameTitle;
        if (arena.getArenaController() instanceof ArenaJoinLeave)
            maxPlayers = "/" + ((ArenaJoinLeave) arena.getArenaController()).getMaxPlayers();
    }

    public void firstJoinApply(CvCPlayer player) {
        WrappedSidebarObjective objective = player.getSidebarObjective();
        objective.setTitle(gameTitle);
        objective.reset();
        objective.setLine(6, "Map : " + ChatColor.GREEN + arena.getArenaName());
        objective.setLine(5, "Players : " + ChatColor.GREEN + arena.getTotalPlayers() + maxPlayers);
        objective.setLine(3, "Status : " + ChatColor.GREEN + "Waiting...");
        objective.setLine(1, "mc.talesdev.com");
        objective.setBlankLine(7, 4, 2);
        objective.update();
    }

    public void updateLobby(CvCPlayer player) {
        WrappedSidebarObjective objective = player.getSidebarObjective();
        objective.setLine(5, "Players : " + ChatColor.GREEN.toString() + arena.getTotalPlayers() + maxPlayers);
        objective.update();
    }

    public void updateLobby(List<CvCPlayer> playerlist) {
        playerlist.forEach(this::updateLobby);
    }

    public void updateCountdown(CvCPlayer player, int countdown) {
        WrappedSidebarObjective objective = player.getSidebarObjective();
        objective.setLine(3, "Status : " + ChatColor.GREEN + "Starting in " + countdown);
        objective.update();
    }

    public void gameStartApply(List<CvCPlayer> playerlist) {
        playerlist.forEach(this::gameStartApply);
    }

    public void gameStartApply(CvCPlayer player) {
        WrappedSidebarObjective objective = player.getSidebarObjective();
        objective.reset();
        objective.setMaxLine(15);
        int tkills = 0, ctkills = 0;
        String team = "";
        if (arena.getArenaController() instanceof TDMArenaController) {
            TDMArenaController controller = ((TDMArenaController) arena.getArenaController());
            tkills = controller.getTerroristKills();
            ctkills = controller.getCounterTerroristKills();
            if (controller.getCounterTerroristTeam().hasPlayer(player.getPlayer())) {
                team = "CT";
            } else if (controller.getTerroristTeam().hasPlayer(player.getPlayer())) {
                team = "T";
            }
        }
        objective.reset();
        objective.setTitle(" " + ChatColor.BLUE + "CT" + ctkills + ChatColor.GOLD + "  and  " + ChatColor.RED + tkills + "TR ");
        objective.setTitle(" " + ChatColor.BLUE + "CT" + ctkills + ChatColor.GRAY + "  and  " + ChatColor.RED + tkills + "TR ");
        objective.setLine(14, ChatColor.GREEN + "[Objective]");
        objective.setLine(13, "Get 50 team kills");
        objective.setLine(12, "5:00");
        objective.setLine(10, ChatColor.YELLOW + "[Stats]");
        objective.setLine(9, "Kills : " + ChatColor.RED + player.getArenaData().getKills());
        objective.setLine(8, "Deaths : " + ChatColor.RED + player.getArenaData().getDeaths());
        objective.setLine(7, "Assists : " + ChatColor.RED + player.getArenaData().getAssist());
        objective.setLine(5, "Team : " + (team.equalsIgnoreCase("CT") ? (ChatColor.BLUE + "CT") : (ChatColor.RED + "TR")));
        objective.setBlankLine(15, 11, 6, 4, 3, 2, 1);
        objective.update();
    }

    public void updateGame(CvCPlayer player, DigitalTimer timer) {
        WrappedSidebarObjective objective = player.getSidebarObjective();
        objective.setLine(12, timer.digitalTime());
        objective.update();
    }

    public void updateStats(CvCPlayer player) {
        WrappedSidebarObjective objective = player.getSidebarObjective();
        objective.setLine(9, "Kills : " + ChatColor.RED + player.getArenaData().getKills());
        objective.setLine(8, "Deaths : " + ChatColor.RED + player.getArenaData().getDeaths());
        objective.setLine(7, "Assists : " + ChatColor.RED + player.getArenaData().getAssist());
        objective.update();
    }

    public void updateTeamStats(CvCPlayer player, int tkills, int ctkills) {
        WrappedSidebarObjective objective = player.getSidebarObjective();
        objective.setTitle(" " + ChatColor.BLUE + "CT" + ctkills + ChatColor.GOLD + "  and  " + ChatColor.RED + tkills + "TR ");
    }

    public void gameEndApply(CvCPlayer player) {
        WrappedSidebarObjective objective = player.getSidebarObjective();
        objective.setLine(3, "Status : " + ChatColor.GREEN + "Game ended!");
        objective.update();
    }

    public CvCArena getArena() {
        return arena;
    }
}

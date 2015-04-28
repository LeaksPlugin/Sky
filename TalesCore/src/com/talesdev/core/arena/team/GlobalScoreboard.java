package com.talesdev.core.arena.team;

import com.talesdev.core.arena.GameArena;
import com.talesdev.core.arena.scoreboard.DisplayScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashSet;
import java.util.Set;

/**
 * Global Team
 *
 * @author MoKunz
 */
public class GlobalScoreboard {
    private DisplayScoreboard displayScoreboard;
    private Scoreboard globalScoreboard;
    private Set<Team> teamSet;
    private GameArena gameArena;

    public GlobalScoreboard(GameArena gameArena) {
        this.gameArena = gameArena;
        this.globalScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.teamSet = new HashSet<>();
    }

    public boolean addTeam(Team team) {
        return teamSet.add(team);
    }

    public boolean containsTeam(Team team) {
        return teamSet.contains(team);
    }

    public boolean containsTeam(String name) {
        for (Team team : teamSet) {
            if (team.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean removeTeam(Team team) {
        return teamSet.remove(team);
    }

    public void clear() {
        teamSet.clear();
    }

    public Set<Team> getAllTeam() {
        return new HashSet<>(teamSet);
    }

    public GameArena getGameArena() {
        return gameArena;
    }

    public Scoreboard getGlobalScoreboard() {
        return globalScoreboard;
    }

    public DisplayScoreboard getDisplayScoreboard() {
        return displayScoreboard;
    }

    public void setDisplayScoreboard(DisplayScoreboard displayScoreboard) {
        this.displayScoreboard = displayScoreboard;
        getGameArena().getPlayerSet().forEach(displayScoreboard::start);
    }

    public void updateDisplayScoreboard() {
        getGameArena().getPlayerSet().forEach(displayScoreboard::update);
    }
}

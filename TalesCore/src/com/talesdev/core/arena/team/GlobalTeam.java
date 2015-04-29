package com.talesdev.core.arena.team;

import com.talesdev.core.arena.GameArena;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * Global team
 *
 * @author MoKunz
 */
public class GlobalTeam {
    private GameArena gameArena;
    private List<Team> teamList;

    public GlobalTeam(GameArena gameArena) {
        this.gameArena = gameArena;
        this.teamList = new ArrayList<>();
    }

    public GameArena getGameArena() {
        return gameArena;
    }

    public Scoreboard getScoreboard() {
        return gameArena.getGlobalScoreboard().getScoreboard();
    }

    public List<Team> getTeamList() {
        return new ArrayList<>(teamList);
    }

    public boolean containsTeam(Team t) {
        for (Team team : teamList) {
            if (team.getName().equals(t.getName())) {
                return true;
            }
        }
        return false;
    }

    public void newTeam(Team team) {
        if (!containsTeam(team)) {
            teamList.add(team);
        }
    }

    public Team createTeam(String name) {
        Team team = getScoreboard().registerNewTeam(name);
        team.setAllowFriendlyFire(false);
        team.setCanSeeFriendlyInvisibles(true);
        team.setNameTagVisibility(NameTagVisibility.HIDE_FOR_OTHER_TEAMS);
        return team;
    }

    public Team getTeam(String name) {
        for (Team team : teamList) {
            if (team.getName().equals(name)) {
                return team;
            }
        }
        return null;
    }

    public void removeTeam(Team team) {
        removeTeam(team, true);
    }

    public void removeTeam(Team team, boolean unregister) {
        if (!containsTeam(team)) {
            if (unregister) team.unregister();
            teamList.remove(team);
        }
    }

    public void clearTeam() {
        for (Team team : teamList) {
            team.unregister();
        }
        teamList.clear();
    }
}

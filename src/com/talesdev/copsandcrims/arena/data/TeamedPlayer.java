package com.talesdev.copsandcrims.arena.data;

import com.talesdev.core.math.MathRandom;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represent a teamed player
 *
 * @author MoKunz
 */
public class TeamedPlayer {
    int pos = 0;
    private List<Team> teamList;

    public TeamedPlayer(Team... team) {
        this.teamList = new ArrayList<>();
        Collections.addAll(teamList, team);
    }

    public void team(List<Player> players) {
        int max = players.size() - 1;
        for (Player player : players) {
            teamList.get(pos).addPlayer(player);
            pos++;
            if (pos > max) {
                pos = 0;
            }
        }
        pos = 0;
    }
}

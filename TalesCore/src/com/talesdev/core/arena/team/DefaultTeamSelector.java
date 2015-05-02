package com.talesdev.core.arena.team;

import com.talesdev.core.IndexIterator;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.*;

/**
 * Default team selector
 *
 * @author MoKunz
 */
public class DefaultTeamSelector implements TeamSelector {
    @Override
    public Map<Player, Team> select(GlobalTeam globalTeam) {
        List<Player> playerList = new ArrayList<>(globalTeam.getGameArena().getPlayerSet());
        List<Team> teamList = globalTeam.getTeamList();
        Collections.shuffle(playerList);
        Collections.shuffle(teamList);
        IndexIterator indexIterator = new IndexIterator(teamList.size());
        Map<Player, Team> playerTeamMap = new HashMap<>();
        for (Player player : playerList) {
            Team realTeam = getTeam(globalTeam, teamList.get(indexIterator.next()));
            realTeam.addPlayer(player);
            playerTeamMap.put(player, realTeam);
        }
        return playerTeamMap;
    }

    @Override
    public boolean queue(Player player, String teamName) {
        return false;
    }

    @Override
    public void dequeue(Player player) {

    }

    @Override
    public Optional<String> getQueue(Player player) {
        return null;
    }

    private Team getTeam(GlobalTeam globalTeam, Team team) {
        return globalTeam.getTeam(team.getName());
    }
}

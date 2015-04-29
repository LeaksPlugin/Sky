package com.talesdev.core.arena.team;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.Map;

/**
 * Team selector
 *
 * @author MoKunz
 */
public interface TeamSelector {
    /**
     * Select a random team from provided GlobalTeam
     *
     * @param globalTeam A GlobalTeam
     * @return player team map
     */
    public Map<Player, Team> select(GlobalTeam globalTeam);
}

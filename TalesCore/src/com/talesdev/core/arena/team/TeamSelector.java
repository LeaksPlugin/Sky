package com.talesdev.core.arena.team;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.Map;
import java.util.Optional;

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
    Map<Player, Team> select(GlobalTeam globalTeam);

    /**
     * Queue player into specific team
     *
     * @param player   A player to be queue
     * @param teamName A team name
     * @return true if success (queue is not full) otherwise false
     */
    boolean queue(Player player, String teamName);

    /**
     * Removed player from team queue
     *
     * @param player A player to be dequeue
     */
    void dequeue(Player player);

    /**
     * Get the current team queue of this player
     *
     * @param player A player
     * @return Optional object , the value is present if player is queued for specific team
     */
    Optional<String> getQueue(Player player);
}

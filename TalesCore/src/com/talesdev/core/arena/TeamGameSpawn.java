package com.talesdev.core.arena;

import com.talesdev.core.arena.team.DefaultTeamSelector;
import com.talesdev.core.arena.team.TeamSelector;
import org.bukkit.entity.Player;

/**
 * Team based game arena spawn
 *
 * @author MoKunz
 */
public class TeamGameSpawn implements ArenaSpawn {
    @Override
    public void spawn(GameArena gameArena, Player player) {
        TeamSelector selector = new DefaultTeamSelector();
        selector.select(gameArena.getTeam());
    }
}

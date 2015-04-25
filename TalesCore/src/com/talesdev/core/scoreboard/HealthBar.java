package com.talesdev.core.scoreboard;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criterias;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

/**
 * Player health bar
 *
 * @author MoKunz
 */
public class HealthBar {
    private Objective objective;

    public HealthBar(Scoreboard scoreboard) {
        objective = scoreboard.registerNewObjective("healthbar", Criterias.HEALTH);
        objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        objective.setDisplayName(ChatColor.RED + "\u2764");
    }

    public HealthBar(Player player) {
        if (player.getScoreboard() != null) {
            objective = player.getScoreboard().registerNewObjective("healthbar", Criterias.HEALTH);
            objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
            objective.setDisplayName(ChatColor.RED + "\u2764");
        }
    }

    public Objective getObjective() {
        return objective;
    }
}

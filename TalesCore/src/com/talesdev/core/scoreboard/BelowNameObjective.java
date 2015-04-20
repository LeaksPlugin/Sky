package com.talesdev.core.scoreboard;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;

/**
 * Wrapper for objective
 *
 * @author MoKunz
 */
public class BelowNameObjective {
    private DoubleBufferObjective objective;

    public BelowNameObjective(String name, Scoreboard scoreboard) {
        objective = new DoubleBufferObjective(name, scoreboard);
        objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
    }

    public void set(String name) {
        objective.startWriting();
        objective.writeTitle(name);
        objective.endWriting();
    }
}

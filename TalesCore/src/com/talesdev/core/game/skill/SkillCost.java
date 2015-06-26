package com.talesdev.core.game.skill;

import org.bukkit.entity.Player;

/**
 * Skill casting cost
 *
 * @author MoKunz
 */
public interface SkillCost {
    boolean applyCost(Player player);
}

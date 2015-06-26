package com.talesdev.core.game.skill;

import org.bukkit.entity.Player;

/**
 * @author MoKunz
 */
public class ZeroCost implements SkillCost {
    @Override
    public boolean applyCost(Player player) {
        return true;
    }
}

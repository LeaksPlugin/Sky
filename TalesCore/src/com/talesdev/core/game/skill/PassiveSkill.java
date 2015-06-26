package com.talesdev.core.game.skill;

import org.bukkit.entity.Player;

/**
 * Passive skill
 *
 * @author MoKunz
 */
public interface PassiveSkill {
    public void activate(Player player);

    public void deactivate(Player player);
}

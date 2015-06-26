package com.talesdev.core.game.skill;

import com.talesdev.core.TalesCore;
import com.talesdev.core.player.CorePlayer;
import org.bukkit.entity.Player;

/**
 * @author MoKunz
 */
public class ManaCost implements SkillCost {
    @Override
    public boolean applyCost(Player player) {
        CorePlayer corePlayer = TalesCore.getPlugin().getCorePlayer(player);
        // remove mana
        // removing success
        return true;
    }
}

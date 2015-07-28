package com.talesdev.core.gui;

import com.talesdev.core.player.ClickingAction;
import org.bukkit.entity.Player;

/**
 * @author MoKunz
 */
@FunctionalInterface
public interface NodeTrigger {
    void trigger(Player player, ItemNode node, ClickingAction action);
}

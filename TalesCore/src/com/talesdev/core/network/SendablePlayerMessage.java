package com.talesdev.core.network;

import org.bukkit.entity.Player;

/**
 * Represent an packet related to player messaging
 *
 * @author MoKunz
 */
public interface SendablePlayerMessage {
    public void send(Player... player);
}

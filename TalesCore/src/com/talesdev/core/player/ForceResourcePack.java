package com.talesdev.core.player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import org.bukkit.plugin.Plugin;

/**
 * Force resource pack
 *
 * @author MoKunz
 */
public class ForceResourcePack extends PacketAdapter {
    public ForceResourcePack(Plugin plugin) {
        super(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.RESOURCE_PACK_SEND);
    }
}

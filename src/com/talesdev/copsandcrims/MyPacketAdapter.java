package com.talesdev.copsandcrims;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.plugin.Plugin;

/**
 * Packet listener
 *
 * @author MoKunz
 */
public class MyPacketAdapter extends PacketAdapter {
    public MyPacketAdapter(Plugin plugin) {
        super(plugin, ListenerPriority.HIGHEST, PacketType.Play.Client.HELD_ITEM_SLOT);
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {

    }
}

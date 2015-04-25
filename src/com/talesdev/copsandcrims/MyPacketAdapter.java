package com.talesdev.copsandcrims;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.plugin.Plugin;

/**
 * Packet listener
 *
 * @author MoKunz
 */
public class MyPacketAdapter extends PacketAdapter {
    public MyPacketAdapter(Plugin plugin) {
        super(plugin, ListenerPriority.HIGHEST, PacketType.Play.Server.SCOREBOARD_OBJECTIVE, PacketType.Play.Server.SCOREBOARD_SCORE);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        PacketContainer packet = event.getPacket();
        if (event.getPacketType().equals(PacketType.Play.Server.SCOREBOARD_SCORE)) {
            if (packet.getStrings().read(1).equalsIgnoreCase("healthbar")) {
                int health = packet.getIntegers().read(0);
                packet.getIntegers().write(0, health * 5);
            }
        }
    }
}

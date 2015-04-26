package com.talesdev.core.player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.plugin.Plugin;

/**
 * Class for modifying health objective
 * @author MoKunz
 */
public class HealthBarMultiplier extends PacketAdapter {
    private int multiplier;

    public HealthBarMultiplier(Plugin plugin) {
        this(plugin, 5);
    }

    public HealthBarMultiplier(Plugin plugin, int multiplier) {
        super(plugin, ListenerPriority.HIGHEST, PacketType.Play.Server.SCOREBOARD_SCORE);
        this.multiplier = multiplier;
    }
    @Override
    public void onPacketSending(PacketEvent event) {
        PacketContainer packet = event.getPacket();
        if (event.getPacketType().equals(PacketType.Play.Server.SCOREBOARD_SCORE)) {
            if (packet.getStrings().read(1).equalsIgnoreCase("healthbar")) {
                int health = packet.getIntegers().read(0);
                packet.getIntegers().write(0, health * multiplier);
            }
        }
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }
}

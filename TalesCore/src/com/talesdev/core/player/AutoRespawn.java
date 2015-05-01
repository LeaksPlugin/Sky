package com.talesdev.core.player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

/**
 * Auto respawn
 *
 * @author MoKunz
 */
public class AutoRespawn {
    private Player player;

    public AutoRespawn(Player player) {
        this.player = player;
    }

    public void perform() {
        PacketContainer packetContainer = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Client.CLIENT_COMMAND);
        packetContainer.getClientCommands().write(0, EnumWrappers.ClientCommand.PERFORM_RESPAWN);
        try {
            ProtocolLibrary.getProtocolManager().recieveClientPacket(player, packetContainer);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

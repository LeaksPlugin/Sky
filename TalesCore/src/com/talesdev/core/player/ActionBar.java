package com.talesdev.core.player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.injector.PacketConstructor;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * Action bar helper class
 *
 * @author MoKunz
 */
public class ActionBar {
    private String message = "";
    private ProtocolManager protocolManager;

    public ActionBar(String message) {
        this.message = message;
        this.protocolManager = ProtocolLibrary.getProtocolManager();
    }

    public void send(Player player) {
        WrappedChatComponent component = WrappedChatComponent.fromText(getMessage());

        PacketConstructor constructor = protocolManager.createPacketConstructor(PacketType.Play.Server.CHAT,
                component.getHandleType(), byte.class);
        PacketContainer packet = constructor.createPacket(component.getHandle(), (byte) 2);
        try {
            protocolManager.sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void send(Collection<Player> players) {
        players.forEach(this::send);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

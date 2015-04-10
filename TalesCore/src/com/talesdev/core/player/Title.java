package com.talesdev.core.player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.TitleAction;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * A helper class for sending title to player
 *
 * @author MoKunz
 */
public class Title {
    private ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
    private String title;
    private String subtitle;
    private int fadeIn;
    private int stay;
    private int fadeOut;

    public Title(String title, String subtitle) {
        set(title, subtitle);
        setFadeIn(20);
        setStay(100);
        setFadeOut(20);
    }

    public Title(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        set(title, subtitle);
        setFadeIn(fadeIn);
        setStay(stay);
        setFadeOut(fadeOut);
    }

    public PacketContainer createTitlePacket(TitleAction titleAction) {
        PacketContainer packetContainer = protocolManager.createPacket(PacketType.Play.Server.TITLE);
        packetContainer.getTitleActions().write(0, titleAction);
        return packetContainer;
    }

    public void send(Player player) {
        try {
            sendTitle(player);
            sendSubtitle(player);
            sendTimes(player);
        } catch (InvocationTargetException e) {
            System.out.println("Can't send title packet to " + player.getName() + "!");
            e.printStackTrace();
        }
    }

    public void send(Collection<Player> players) {
        players.forEach(this::send);
    }

    public void sendTitle(Player player) throws InvocationTargetException {
        PacketContainer titlePacket = createTitlePacket(TitleAction.TITLE);
        titlePacket.getChatComponents().write(0, WrappedChatComponent.fromText(title));
        protocolManager.sendServerPacket(player, titlePacket);
    }

    public void sendSubtitle(Player player) throws InvocationTargetException {
        PacketContainer subtitlePacket = createTitlePacket(TitleAction.SUBTITLE);
        subtitlePacket.getChatComponents().write(0, WrappedChatComponent.fromText(subtitle));
        protocolManager.sendServerPacket(player, subtitlePacket);
    }

    public void sendTimes(Player player) throws InvocationTargetException {
        PacketContainer timesPacket = createTitlePacket(TitleAction.TIMES);
        timesPacket.getIntegers()
                .write(0, fadeIn)
                .write(1, stay)
                .write(2, fadeOut);
        protocolManager.sendServerPacket(player, timesPacket);
    }

    public void clear(Player player) throws InvocationTargetException {
        protocolManager.sendServerPacket(player, createTitlePacket(TitleAction.CLEAR));
    }

    public void reset(Player player) throws InvocationTargetException {
        protocolManager.sendServerPacket(player, createTitlePacket(TitleAction.RESET));
    }

    public void set(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getFadeIn() {
        return fadeIn;
    }

    public void setFadeIn(int fadeIn) {
        this.fadeIn = fadeIn;
    }

    public int getStay() {
        return stay;
    }

    public void setStay(int stay) {
        this.stay = stay;
    }

    public int getFadeOut() {
        return fadeOut;
    }

    public void setFadeOut(int fadeOut) {
        this.fadeOut = fadeOut;
    }
}
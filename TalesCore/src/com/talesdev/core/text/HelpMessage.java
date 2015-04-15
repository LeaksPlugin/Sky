package com.talesdev.core.text;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for creating help message
 *
 * @author MoKunz
 */
public class HelpMessage {
    private CommandSender sender;
    private List<BaseHelpMessage> messageList;

    public HelpMessage(CommandSender sender) {
        this.messageList = new ArrayList<>();
        this.sender = sender;
    }

    public String toString() {
        return asPlainString();
    }

    public void print() {
        for (BaseHelpMessage message : messageList) {
            sender.sendMessage(message.asChatMessage());
        }
    }

    public String asPlainString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (BaseHelpMessage message : messageList) {
            stringBuilder.append(ChatColor.stripColor(message.asChatMessage()));
        }
        return stringBuilder.toString();
    }
}

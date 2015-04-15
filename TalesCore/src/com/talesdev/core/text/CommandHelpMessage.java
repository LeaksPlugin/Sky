package com.talesdev.core.text;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;

/**
 * Command HelpMessage
 * TODO : Finish this
 *
 * @author MoKunz
 */
public class CommandHelpMessage implements BaseHelpMessage {
    private String baseCommand;
    private String[] args;

    public CommandHelpMessage(Command command, String... args) {
        baseCommand = command.getName();
        if (args != null && args.length > 0) {
            this.args = args;
        }
    }

    private String argsToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String arg : args) {
            stringBuilder.append(" ").append(arg);
        }
        return stringBuilder.toString();
    }

    @Override
    public String asChatMessage() {
        return ChatColor.GREEN + "/" + baseCommand + argsToString();
    }
}

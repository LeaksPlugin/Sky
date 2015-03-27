package com.talesdev.copsandcrims;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

/**
 * Command Executor for debugging purpose
 * @author MoKunz
 */
public class DebugCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.YELLOW + "Command name : " + ChatColor.BLUE + command.getName());
        sender.sendMessage(ChatColor.YELLOW + "Sender name : " + ChatColor.BLUE + sender.getName());
        sender.sendMessage(ChatColor.YELLOW + "Command args(" + args.length + "): " + ChatColor.BLUE + Arrays.toString(args));
        return true;
    }
}

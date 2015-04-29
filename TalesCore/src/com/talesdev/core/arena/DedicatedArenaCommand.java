package com.talesdev.core.arena;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Dedicated arena command system
 *
 * @author MoKunz
 */
public class DedicatedArenaCommand implements CommandExecutor {
    private GameArena gameArena;
    private String prefix;

    public DedicatedArenaCommand(GameArena gameArena, String cmd) {
        this.gameArena = gameArena;
        this.prefix = cmd;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase(prefix)) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("join")) {
                    if (sender.hasPermission(perm(".command.join"))) {

                    } else {
                        noPerm(sender);
                    }
                } else if (args[0].equalsIgnoreCase("leave")) {
                    if (sender.hasPermission(perm("command.leave"))) {

                    } else {
                        noPerm(sender);
                    }
                }
            }
        }
        return false;
    }

    public GameArena getGameArena() {
        return gameArena;
    }

    private void noPerm(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "You don't have permission to do that!");
    }

    private String perm(String suffix) {
        return prefix + "." + suffix;
    }
}

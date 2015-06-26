package com.talesdev.core;

import com.talesdev.core.entity.PlayerView;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * @author MoKunz
 */
public class DevCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("dev")) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("view")) {
                    if (sender instanceof Player) {
                        PlayerView view = new PlayerView(((Player) sender));
                        List<Entity> entityList = view.entities();
                        sender.sendMessage(ChatColor.GREEN + "Entity list");
                        for (Entity entity : entityList) {
                            sender.sendMessage("  " + ChatColor.BLUE + entity.getName() + ChatColor.GREEN + " at" + ChatColor.BLUE + entity.getLocation().toVector().toString());
                        }
                    } else {
                        sender.sendMessage(errorMessage("Only player can use this command!"));
                    }
                } else {
                    sender.sendMessage(errorMessage("Sub command not found!"));
                }
            } else {
                sender.sendMessage(errorMessage("Too few arguments"));
            }
            return true;
        } else {
            return false;
        }
    }

    private String errorMessage(String msg) {
        return ChatColor.RED + "Error : " + msg;
    }
}

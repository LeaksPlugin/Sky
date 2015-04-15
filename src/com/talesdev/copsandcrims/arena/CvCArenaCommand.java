package com.talesdev.copsandcrims.arena;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.ServerCvCArena;
import com.talesdev.copsandcrims.arena.system.CvCArenaController;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Commands for arena management
 *
 * @author MoKunz
 */
public class CvCArenaCommand implements CommandExecutor {
    private CopsAndCrims plugin;
    private ServerCvCArena serverCvCArena;

    public CvCArenaCommand(CopsAndCrims plugin) {
        this.plugin = plugin;
        this.serverCvCArena = plugin.getServerCvCArena();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("cvcarena")) {
            if (args != null && args.length > 0) {
                boolean player = sender instanceof Player;
                if (args[0].equalsIgnoreCase("create")) {
                    return runCreateCommand(sender, args, player);
                } else if (args[0].equalsIgnoreCase("list")) {
                    return runListCommand(sender, args, player);
                } else if (args[0].equalsIgnoreCase("remove")) {
                    return runRemoveCommand(sender, args, player);
                } else if (args[0].equalsIgnoreCase("reload")) {
                    plugin.getServerCvCArena().load();
                    return true;
                } else if (args[0].equalsIgnoreCase("arena")) {
                    return runArenaCommand(sender, args, player);
                } else {
                    sender.sendMessage(command.getUsage());
                    return true;
                }
            } else {
                sender.sendMessage(ChatColor.GREEN + "CvC Arena System by " + ChatColor.BLUE + "MoKunz");
            }
        }
        return false;
    }

    private boolean runCreateCommand(CommandSender sender, String[] args, boolean player) {
        if (args.length > 1) {
            if (!serverCvCArena.containsArena(args[1])) {
                if (args.length > 2) {
                    CvCArenaController controller = serverCvCArena.getController(args[2]);
                    if (controller != null) {
                        CvCArena arena = new CvCArena(serverCvCArena, args[1], controller);
                        serverCvCArena.addArena(arena);
                        sender.sendMessage(ChatColor.GREEN + "Arena " + ChatColor.BLUE + "\" " + args[1] + "\"" +
                                        ChatColor.GREEN + " created with " + ChatColor.BLUE + controller.getArenaType()
                        );
                        sender.sendMessage(ChatColor.GREEN + "Make sure to setup other things before playing!");
                        serverCvCArena.save();
                    } else {
                        sender.sendMessage(ChatColor.RED + "Error : Arena controller " + ChatColor.BLUE + "\"" +
                                        args[2] + "\"" + ChatColor.RED + "doesn't exist!"
                        );
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Error : Please provide an arena controller type!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Error : Arena already exists!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Error : Please provide an arena name!");
        }
        return true;
    }

    private boolean runListCommand(CommandSender sender, String[] args, boolean player) {
        List<CvCArena> cArenaList = plugin.getServerCvCArena().getArenaList();
        sender.sendMessage(ChatColor.RED + "[CvC]" + ChatColor.GREEN + " -* Arena List *-");
        for (CvCArena arena : cArenaList) {
            sender.sendMessage(" " + ChatColor.BLUE +
                            arena.getArenaName() + ChatColor.YELLOW +
                            " type : " + arena.getArenaController().getArenaType()
            );
        }
        return true;
    }

    private boolean runRemoveCommand(CommandSender sender, String[] args, boolean player) {
        sender.sendMessage(ChatColor.RED + "The only way to remove arena is removing by config file!");
        return true;
    }

    private boolean runArenaCommand(CommandSender sender, String[] args, boolean player) {
        if (serverCvCArena.getArena(args[1]) != null) {
            sender.sendMessage(ChatColor.BLUE + "Implementing in process");
        } else {
            sender.sendMessage(ChatColor.RED + "Error : Arena " + ChatColor.BLUE + "\"" + args[1] + "\" " + ChatColor.RED + "not found!");
        }
        return true;
    }
}

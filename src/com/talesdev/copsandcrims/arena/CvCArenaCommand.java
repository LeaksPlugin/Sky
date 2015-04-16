package com.talesdev.copsandcrims.arena;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.ServerCvCArena;
import com.talesdev.copsandcrims.arena.data.CmdResult;
import com.talesdev.copsandcrims.arena.system.ArenaJoinLeave;
import com.talesdev.copsandcrims.arena.system.CvCArenaController;
import com.talesdev.copsandcrims.player.CvCPlayer;
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
                } else if (args[0].equalsIgnoreCase("join")) {
                    return runJoinCommand(sender, args, player);
                } else if (args[0].equalsIgnoreCase("leave")) {
                    return runLeaveCommand(sender, args, player);
                } else if (args[0].equalsIgnoreCase("arena")) {
                    return runArenaCommand(sender, args, player);
                } else {
                    sender.sendMessage(command.getUsage());
                    return true;
                }
            } else {
                sender.sendMessage(ChatColor.GREEN + "CvC Arena System by " + ChatColor.BLUE + "MoKunz");
                return true;
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
                        CvCArena arena = new CvCArena(serverCvCArena, args[1], controller.createController());
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
        if (args.length > 1) {
            if (serverCvCArena.getArena(args[1]) != null) {
                CvCArena arena = serverCvCArena.getArena(args[1]);
                if (args.length > 2) {
                    String[] newArgs = new String[args.length - 2];
                    System.arraycopy(args, 2, newArgs, 0, newArgs.length);
                    // send arena command
                    CmdResult result = arena.getArenaController().receiveCommand(sender, newArgs);
                    if (result.getResultMessage() != null) sender.sendMessage(result.getResultMessage());
                    return result.isSuccess();
                } else {
                    sender.sendMessage(ChatColor.RED + "Usage : /cvcarena arena " + args[1] + " " + "<operation>");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Error : Arena " + ChatColor.BLUE + "\"" + args[1] + "\" " + ChatColor.RED + "not found!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Usage : /cvcarena arena <arenaName>");
        }
        return true;
    }

    private boolean runJoinCommand(CommandSender sender, String[] args, boolean player) {
        if (args.length > 1) {
            CvCArena arena = serverCvCArena.getArena(args[1]);
            if (arena != null) {
                if (arena.getArenaController() instanceof ArenaJoinLeave) {
                    if (sender instanceof Player) {
                        CvCPlayer cPlayer = plugin.getServerCvCPlayer().getPlayer((Player) sender);
                        if (cPlayer != null) {
                            ((ArenaJoinLeave) arena.getArenaController()).joinArena(cPlayer);
                        }
                    } else {
                        if (args.length > 2) {
                            CvCPlayer refPlayer = plugin.getServerCvCPlayer().getPlayer(plugin.getServer().getPlayer(args[2]));
                            if (refPlayer != null) {
                                ((ArenaJoinLeave) arena.getArenaController()).joinArena(refPlayer);
                                sender.sendMessage(ChatColor.GREEN + args[2] + " joined " + arena.getArenaName());
                            } else {
                                sender.sendMessage(ChatColor.RED + "Error : player not found!");
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "Usage : /cvcarena join " + args[1] + " <player>");
                        }
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Error : You can't join this arena!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Error : Arena " + ChatColor.BLUE + "\"" + args[1] + "\" " + ChatColor.RED + "not found!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Usage : /cvcarena join <arenaName> [Player]");
        }
        return true;
    }

    private boolean runLeaveCommand(CommandSender sender, String[] args, boolean player) {
        CvCPlayer cPlayer = null;
        CvCArena arena;
        if (!(sender instanceof Player)) {
            if (args.length > 1) {
                cPlayer = plugin.getServerCvCPlayer().getPlayer(plugin.getServer().getPlayer(args[1]));
            }
        } else {
            cPlayer = plugin.getServerCvCPlayer().getPlayer(((Player) sender));
        }
        if (cPlayer != null) {
            arena = cPlayer.getArenaData().getPlayingArena();
            if (arena != null) {
                arena = cPlayer.getArenaData().getPlayingArena();
                if (arena.getArenaController() instanceof ArenaJoinLeave) {
                    ((ArenaJoinLeave) arena.getArenaController()).leaveArena(cPlayer);
                } else {
                    sender.sendMessage(ChatColor.RED + "Error : You can't leave that arena!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Error : Invalid state!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Error : Player not found!");
        }
        return true;
    }
}

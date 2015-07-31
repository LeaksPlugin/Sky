package com.talesdev.core.arena;

import com.talesdev.core.TalesCore;
import com.talesdev.core.arena.phase.CountdownPhase;
import com.talesdev.core.arena.phase.EndPhase;
import com.talesdev.core.arena.team.GlobalTeam;
import com.talesdev.core.player.CorePlayer;
import com.talesdev.core.world.LocationString;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Dedicated arena command system
 *
 * @author MoKunz
 */
public class DedicatedArenaCommand implements CommandExecutor {
    protected GameArena gameArena;
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
                        if (sender instanceof Player) {
                            if (args.length > 1) {
                                Player player = Bukkit.getPlayer(args[1]);
                                if (player != null) {
                                    sender.sendMessage(ChatColor.GREEN + "Sending " + player.getName() + "to arena");
                                } else {
                                    sender.sendMessage(ChatColor.RED + "Player " + args[1] + "not found!");
                                }
                            } else {
                                if (!gameArena.containsPlayer(((Player) sender))) {
                                    gameArena.join(((Player) sender));
                                    sender.sendMessage(ChatColor.GREEN + "You joined arena!");
                                } else {
                                    sender.sendMessage(ChatColor.YELLOW + "Already in this arena!");
                                }
                            }
                        } else {
                            if (args.length > 1) {
                                Player player = Bukkit.getPlayer(args[1]);
                                if (player != null) {
                                    if (!gameArena.containsPlayer(player)) {
                                        gameArena.join(player);
                                        sender.sendMessage(ChatColor.GREEN + "Sending " + player.getName() + "to arena");
                                    } else {
                                        sender.sendMessage(ChatColor.YELLOW + "Already in this arena!");
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.RED + "Player " + args[1] + "not found!");
                                }
                            } else {
                                sender.sendMessage(ChatColor.RED + "Usage : /" + prefix + " join <player>");
                            }
                        }
                    } else {
                        noPerm(sender);
                    }
                } else if (args[0].equalsIgnoreCase("leave")) {
                    if (sender.hasPermission(perm("command.leave"))) {
                        if (sender instanceof Player) {
                            if (args.length > 1) {
                                Player player = Bukkit.getPlayer(args[1]);
                                if (player != null) {
                                    if (gameArena.containsPlayer(player)) {
                                        gameArena.leave(player);
                                        sender.sendMessage(ChatColor.GREEN + "Force leaving " + player.getName());
                                    } else {
                                        sender.sendMessage(ChatColor.YELLOW + "Player is not in arena");
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.RED + "Player " + args[1] + "not found!");
                                }
                            }
                            if (gameArena.containsPlayer(((Player) sender))) {
                                gameArena.leave(((Player) sender));
                                sender.sendMessage(ChatColor.GREEN + "You left arena!");
                            } else {
                                sender.sendMessage(ChatColor.YELLOW + "You are not in arena!");
                            }
                        } else {
                            if (args.length > 1) {
                                Player player = Bukkit.getPlayer(args[1]);
                                if (player != null) {
                                    if (gameArena.containsPlayer(player)) {
                                        gameArena.leave(player);
                                        sender.sendMessage(ChatColor.GREEN + "Force leaving " + player.getName());
                                    } else {
                                        sender.sendMessage(ChatColor.YELLOW + "Player is not in arena");
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.RED + "Player " + args[1] + "not found!");
                                }
                            } else {
                                sender.sendMessage(ChatColor.RED + "Usage : /" + prefix + " join <player>");
                            }
                        }
                    } else {
                        noPerm(sender);
                    }
                } else if (args[0].equalsIgnoreCase("start")) {
                    if (sender.hasPermission(perm("command.start"))) {
                        if (gameArena.getGameState().equals(GameState.WAITING)) {
                            gameArena.dispatchPhase(new CountdownPhase());
                            sender.sendMessage(ChatColor.GREEN + "Force starting arena!");
                        } else {
                            sender.sendMessage(ChatColor.RED + "Invalid arena state!");
                        }
                    } else {
                        noPerm(sender);
                    }
                } else if (args[0].equalsIgnoreCase("end")) {
                    if (sender.hasPermission(perm("command.end"))) {
                        if (gameArena.getGameState().equals(GameState.STARTED)) {
                            gameArena.dispatchPhase(new EndPhase());
                            sender.sendMessage(ChatColor.GREEN + "Force ending arena!");
                        } else {
                            sender.sendMessage(ChatColor.RED + "Invalid arena state!");
                        }
                    } else {
                        noPerm(sender);
                    }
                } else if (args[0].equalsIgnoreCase("target")) {
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        if (args.length > 1) {
                            Player target = Bukkit.getPlayer(args[1]);
                            if (target != null) {
                                CorePlayer corePlayer = TalesCore.getPlugin().getCorePlayer(player);
                                corePlayer.setCompassTarget(target);
                                sender.sendMessage(ChatColor.GREEN + "Successfully set the compass target to" + ChatColor.BLUE + target.getName());
                            }
                        }
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("addspawn")) {
                    if (sender instanceof Player) {
                        Player player = ((Player) sender);
                        if (args.length > 1) {
                            if (containsTeam(args[1])) {
                                Location location = player.getLocation();
                                FileConfiguration config = getGameArena().getConfig();
                                List<String> locationList = config.getStringList("spawn." + args[1]);
                                locationList.add(new LocationString(location).toString());
                                config.set("spawn." + args[1], locationList);
                                getGameArena().save();
                                sender.sendMessage(ChatColor.GREEN + "Added spawn location of " + args[1]);
                            } else {
                                sender.sendMessage(ChatColor.RED + "Team " + args[1] + " not found!");
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "Usage : /" + prefix + " addspawn <team>");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "Only player can use this command!");
                    }
                } else if (args[0].equalsIgnoreCase("clearspawn")) {
                    if (args.length > 1) {
                        if (containsTeam(args[1])) {
                            FileConfiguration config = getGameArena().getConfig();
                            config.set("spawn." + args[1], new ArrayList<>());
                            getGameArena().save();
                            sender.sendMessage(ChatColor.GREEN + "Cleared spawn location of " + args[1]);
                        } else {
                            sender.sendMessage(ChatColor.RED + "Team " + args[1] + " not found!");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "Usage : /" + prefix + " clearspawn <team>");
                    }
                } else {
                    if (!customCommands(sender, command, label, args)) {
                        sender.sendMessage(ChatColor.RED + "Usage : /" + prefix + " <subcommand>");
                    }
                }
            } else {
                sender.sendMessage(ChatColor.BLUE + "TalesCore Arena system");
                sender.sendMessage(ChatColor.BLUE + "by MoKunz (talesdev.com)");
            }
        }
        return true;
    }

    protected boolean customCommands(CommandSender sender, Command command, String label, String[] args) {
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

    private boolean containsTeam(String teamName) {
        return getGameArena().getTeam().getTeam(teamName) != null;
    }

    private GlobalTeam getGlobalTeam() {
        return getGameArena().getTeam();
    }
}

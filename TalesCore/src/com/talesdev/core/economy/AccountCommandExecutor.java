package com.talesdev.core.economy;

import com.talesdev.core.TalesCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Account command executor
 *
 * @author MoKunz
 */
public class AccountCommandExecutor implements CommandExecutor {
    private TalesCore plugin;

    public AccountCommandExecutor(TalesCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("account")) {
            if (args.length > 0) {
                Bank bank = plugin.getBank();
                if (args[0].equalsIgnoreCase("list")) {
                    List<Account> accountList = bank.allAccounts();
                    sender.sendMessage(ChatColor.GREEN + "Account list");
                    for (Account account : accountList) {
                        sender.sendMessage(ChatColor.BLUE + "- " + account.owner().getName() + " : " + account.balance());
                    }
                } else if (args[0].equalsIgnoreCase("create")) {
                    if (args.length > 1) {
                        OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
                        if (player != null) {
                            Account account = bank.createAccount(player);
                            float start = 0.0F;
                            if (args.length > 2) {
                                try {
                                    start = Float.parseFloat(args[2]);
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            }
                            account.set(start);
                            sender.sendMessage(ChatColor.GREEN + "Account created! with " + ChatColor.BLUE + start + ChatColor.GREEN + " starting balance");
                        } else {
                            sender.sendMessage(ChatColor.RED + "Error : player " + args[1] + " not found!");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "Error : too few arguments!");
                    }
                } else if (args[0].equalsIgnoreCase("value")) {
                    if (args.length > 1) {
                        Player player = Bukkit.getPlayer(args[1]);
                        if (player != null) {
                            Account account = bank.getAccount(player);
                            if (account != null) {
                                sender.sendMessage(ChatColor.GREEN + "Value : " + ChatColor.BLUE + account.balance());
                            } else {
                                sender.sendMessage(ChatColor.RED + player.getName() + " don't have an account!");
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "Player " + ChatColor.BLUE + args[1] + ChatColor.RED + " not found!");
                        }
                    }
                } else if (args[0].equalsIgnoreCase("set")) {
                    if (args.length > 1) {
                        OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
                        Account account = bank.getAccount(player);
                        if (account != null) {
                            float balance = 0.0F;
                            if (args.length > 2) {
                                try {
                                    balance = Float.parseFloat(args[2]);
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            }
                            sender.sendMessage(ChatColor.GREEN + "Set " + account.owner().getName() + "'s balance to " + balance);
                        } else {
                            sender.sendMessage(ChatColor.RED + "Error : Player not found!");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "Error : Too few arguments!");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Unknown sub command " + ChatColor.BLUE + args[0]);
                }
            } else {
                sender.sendMessage(ChatColor.YELLOW + "Account command (test)");
            }
        }
        return false;
    }
}

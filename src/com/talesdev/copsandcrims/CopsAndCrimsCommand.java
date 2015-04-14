package com.talesdev.copsandcrims;

import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.talesdev.copsandcrims.guns.DesertEagle;
import com.talesdev.copsandcrims.player.CvCPlayer;
import com.talesdev.copsandcrims.weapon.Weapon;
import com.talesdev.copsandcrims.weapon.module.ShootingModule;
import com.talesdev.core.math.MathRandom;
import com.talesdev.core.player.UUIDTask;
import com.talesdev.core.scoreboard.WrappedSidebarObjective;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Main plugin command executor
 *
 * @author MoKunz
 */
public class CopsAndCrimsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("getuuid")) {
                if (args.length > 1) {
                    Bukkit.getScheduler().runTaskAsynchronously(CopsAndCrims.getPlugin(CopsAndCrims.class),
                            new UUIDTask(CopsAndCrims.getPlugin(CopsAndCrims.class), args[1], sender)
                    );
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("test")) {
                if (sender instanceof Player) {
                    Weapon weapon = CopsAndCrims.getPlugin().getWeaponFactory().getWeapon(DesertEagle.class);
                    ((Player) sender).getInventory().addItem(CopsAndCrims.getPlugin().getWeaponFactory().createWeaponItem(DesertEagle.class));
                    CvCPlayer cvCPlayer = CopsAndCrims.getPlugin().getServerCvCPlayer().getPlayer((Player) sender);
                    cvCPlayer.getPlayerBullet().getBullet(weapon.getName()).setBulletCount(
                            cvCPlayer.getPlayerBullet().getBullet(weapon.getName()).getMaxBullet()
                    );
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("getweapon")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (args.length > 1) {
                        Weapon weapon = CopsAndCrims.getPlugin().getWeaponFactory().getWeapon(args[1]);
                        if (weapon != null) {
                            CvCPlayer cPlayer = CopsAndCrims.getPlugin().getServerCvCPlayer().getPlayer(player);
                            player.getInventory().addItem(weapon.createItemStack());
                            if (weapon.containsModule(ShootingModule.class)) {
                                cPlayer.getPlayerBullet().getBullet(weapon.getName()).setBulletCount(
                                        weapon.getModule(ShootingModule.class).getMaxBullet()
                                );
                            }
                            player.sendMessage(ChatColor.GREEN + "Give " + args[1] + " to you.");
                        } else {
                            player.sendMessage(ChatColor.RED + "Error : Weapon " + args[1] + " not found!");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Error : too few arguments!");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Error : only player can use this command!");
                }
                return true;

            } else if (args[0].equalsIgnoreCase("playerinfo")) {
                Player player = null;
                if (sender instanceof Player) {
                    player = (Player) sender;
                } else {
                    if (args.length > 1) {
                        player = Bukkit.getPlayer(args[1]);
                    }
                }
                if (player != null) {
                    sender.sendMessage("Player name : " + player.getName());
                    sender.sendMessage("Display name : " + player.getDisplayName());
                    sender.sendMessage("Location  : " + player.getLocation());
                    sender.sendMessage("Eye location : " + player.getEyeLocation());
                } else {
                    sender.sendMessage(ChatColor.RED + "Error : player not found!");
                }
                return true;
            } else if (args[0].equalsIgnoreCase("sbt")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    CvCPlayer cPlayer = CopsAndCrims.getPlugin().getServerCvCPlayer().getPlayer(player);
                    WrappedSidebarObjective objective = cPlayer.getSidebarObjective();
                    objective.setTitle(ChatColor.GREEN + "Your stats");
                    objective.setLine(14, ChatColor.BLUE + "Kills");
                    objective.setLine(13, ChatColor.GRAY + " " + Integer.toString(MathRandom.randomRange(5, 10)));
                    objective.setLine(11, ChatColor.RED + "Deaths");
                    objective.setLine(10, ChatColor.GRAY + " " + Integer.toString(MathRandom.randomRange(10, 20)));
                    cPlayer.updateScoreboard();
                    return true;
                }
            }
        }
        return false;
    }
}

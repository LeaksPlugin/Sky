package com.talesdev.copsandcrims;

import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.talesdev.copsandcrims.guns.DesertEagle;
import com.talesdev.copsandcrims.player.CvCPlayer;
import com.talesdev.copsandcrims.weapon.Weapon;
import com.talesdev.core.player.UUIDTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
                            player.getInventory().addItem(weapon.createItemStack());
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
            } else if (args[0].equalsIgnoreCase("hideattrib")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (!player.getItemInHand().getType().equals(Material.AIR)) {
                        int slotNumber = player.getInventory().getHeldItemSlot();
                        ItemStack itemStack = removeAttribute(player.getItemInHand());
                        player.getInventory().setItem(slotNumber, itemStack);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private ItemStack removeAttribute(ItemStack stack) {
        NbtCompound compound = (NbtCompound) NbtFactory.fromItemTag(stack = getCraftItemStack(stack));
        compound.put(NbtFactory.ofList("AttributeModifiers"));
        return stack;
    }

    private ItemStack getCraftItemStack(ItemStack stack) {
        if (!MinecraftReflection.isCraftItemStack(stack))
            return MinecraftReflection.getBukkitItemStack(stack);
        else
            return stack;
    }
}

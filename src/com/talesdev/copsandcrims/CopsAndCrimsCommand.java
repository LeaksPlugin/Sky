package com.talesdev.copsandcrims;

import com.talesdev.copsandcrims.guns.DesertEagle;
import com.talesdev.core.player.UUIDTask;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
                    ((Player) sender).getInventory().addItem(CopsAndCrims.getPlugin().getWeaponFactory().createWeaponItem(DesertEagle.class));
                }
                return true;
            }
        }
        return false;
    }
}

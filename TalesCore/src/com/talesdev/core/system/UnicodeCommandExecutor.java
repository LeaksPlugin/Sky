package com.talesdev.core.system;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by MoKunz on 4/4/2015.
 */
public class UnicodeCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args != null) {
            if (args.length > 0) {
                sender.sendMessage(ChatColor.GREEN + "Unicode Character : " + getUnicodeString(args[0]));
            }
        }
        return false;
    }

    private String getUnicodeString(String hexCode) {
        String unicode = hexCode;
        String regex = "([a-fA-F0-9]{4})";
        String character = "";
        if (unicode.matches(regex)) {
            int hexVal = Integer.parseInt(unicode, 16);
            character += (char) hexVal;
            return character;
        }
        return "";
    }
}

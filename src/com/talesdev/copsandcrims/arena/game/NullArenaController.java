package com.talesdev.copsandcrims.arena.game;

import com.talesdev.copsandcrims.arena.data.CmdArguments;
import com.talesdev.copsandcrims.arena.data.CmdResult;
import com.talesdev.copsandcrims.arena.system.CvCArenaController;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import java.util.logging.Level;

/**
 * A controller that do nothing
 *
 * @author MoKunz
 */
public class NullArenaController extends CvCArenaController {
    public NullArenaController() {
        super("Null");
    }

    @Override
    public void startArena() {
        getPlugin().getLogger().log(Level.WARNING, "Attempting to start an arena with NullController");
    }

    @Override
    public void endArena() {
        getPlugin().getLogger().log(Level.WARNING, "Attempting to end an arena with NullController");
    }

    @Override
    public Location getLobbyLocation() {
        return getPlugin().getServer().getWorlds().get(0).getSpawnLocation();
    }

    @Override
    public void setLobbyLocation(Location location) {

    }

    @Override
    public Location getEndLocation() {
        return getPlugin().getServer().getWorlds().get(0).getSpawnLocation();
    }

    @Override
    public void setEndLocation(Location location) {

    }

    @Override
    public CmdResult receiveCommand(CommandSender sender, String[] arguments) {
        return new CmdResult(ChatColor.RED + "Not implemented yet!", true);
    }
}

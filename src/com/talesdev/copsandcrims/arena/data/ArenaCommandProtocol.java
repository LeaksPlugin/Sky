package com.talesdev.copsandcrims.arena.data;

import org.bukkit.command.CommandSender;

/**
 * Interface for responding arena change
 *
 * @author MoKunz
 */
public interface ArenaCommandProtocol {
    public CmdResult receiveCommand(CommandSender sender, String[] args);
}

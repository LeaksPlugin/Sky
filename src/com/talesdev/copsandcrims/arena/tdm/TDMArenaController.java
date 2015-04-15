package com.talesdev.copsandcrims.arena.tdm;

import com.talesdev.copsandcrims.arena.CvCArena;
import com.talesdev.copsandcrims.arena.system.ArenaJoinLeave;
import com.talesdev.copsandcrims.arena.system.CvCArenaController;
import com.talesdev.copsandcrims.player.CvCPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * Defusal arena controller
 * @author MoKunz
 */
public class TDMArenaController extends CvCArenaController implements ArenaJoinLeave {

    public TDMArenaController() {
        super("TDM");
    }

    @Override
    public void joinArena(CvCPlayer cPlayer) {
        cPlayer.getPlayer().teleport(getLobbyLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
        cPlayer.getPlayer().sendMessage(ChatColor.GREEN + "You joined " + getArena().getArenaName() + "");
    }

    @Override
    public void leaveArena(CvCPlayer cPlayer) {
        cPlayer.getPlayer().teleport(getEndLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    @Override
    public void arenaLoaded() {

    }

    @Override
    public void startArena() {

    }

    @Override
    public void endArena() {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public Location getLobbyLocation() {
        return getArena().getLobbyLocation();
    }

    @Override
    public void setLobbyLocation(Location location) {

    }

    @Override
    public Location getEndLocation() {
        return getArena().getEndLocation();
    }

    @Override
    public void setEndLocation(Location location) {

    }
}

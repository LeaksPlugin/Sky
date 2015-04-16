package com.talesdev.copsandcrims.arena.game;

import com.talesdev.copsandcrims.arena.data.CmdResult;
import com.talesdev.copsandcrims.arena.data.PlayerArenaStatus;
import com.talesdev.copsandcrims.arena.system.ArenaJoinLeave;
import com.talesdev.copsandcrims.arena.system.CvCArenaController;
import com.talesdev.copsandcrims.player.CvCPlayer;
import com.talesdev.core.world.LocationString;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * Defusal arena controller
 *
 * @author MoKunz
 */
public class TDMArenaController extends CvCArenaController implements ArenaJoinLeave {

    public TDMArenaController() {
        super("TDM");
    }

    @Override
    public void arenaLoaded() {
        String lobbyLocation = getConfig().getString("Lobby_Location");
        if (lobbyLocation != null) {
            Location location = LocationString.fromString(lobbyLocation).getLocation();
            setLobbyLocation(location);
        }
        String endLocation = getConfig().getString("End_Location");
        if (endLocation != null) {
            Location location = LocationString.fromString(endLocation).getLocation();
            setEndLocation(location);
        }
    }

    @Override
    public CvCArenaController createController() {
        return new TDMArenaController();
    }

    @Override
    public void save() {
        if (getLobbyLocation() != null)
            getConfig().set("Lobby_Location", new LocationString(getLobbyLocation()).toString());
        if (getEndLocation() != null) getConfig().set("End_Location", new LocationString(getEndLocation()).toString());
    }

    @Override
    public void joinArena(CvCPlayer cPlayer) {
        if (!getArena().hasPlayer(cPlayer)) {
            getArena().addPlayer(cPlayer);
            cPlayer.getArenaData().setLastGameMode(cPlayer.getPlayer().getGameMode());
            cPlayer.getArenaData().setStatus(PlayerArenaStatus.IN_LOBBY);
            cPlayer.getArenaData().setPlayingArena(getArena());
            cPlayer.getPlayer().setGameMode(GameMode.ADVENTURE);
            cPlayer.getPlayer().teleport(getLobbyLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
            cPlayer.getPlayer().sendMessage(ChatColor.GREEN + "You joined " + getArena().getArenaName() + "");
        } else {
            cPlayer.getPlayer().sendMessage(ChatColor.GREEN + "You're already in this arena!");
        }
    }

    @Override
    public void leaveArena(CvCPlayer cPlayer) {
        if (cPlayer.getArenaData().getPlayingArena() != null) {
            if (cPlayer.getArenaData().getPlayingArena().getArenaName().equals(getArena().getArenaName())) {
                getArena().removePlayer(cPlayer);
                cPlayer.getArenaData().setStatus(PlayerArenaStatus.NOT_PLAYING);
                cPlayer.getPlayer().sendMessage(ChatColor.GREEN + "You left arena");
                cPlayer.getPlayer().teleport(getEndLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                cPlayer.getPlayer().setGameMode(cPlayer.getArenaData().getLastGameMode());
                cPlayer.getArenaData().setPlayingArena(null);
            }
        }
    }

    @Override
    public void startArena() {

    }

    @Override
    public void endArena() {

    }

    @Override
    public Location getLobbyLocation() {
        return getArena().getLobbyLocation();
    }

    @Override
    public void setLobbyLocation(Location location) {
        getArena().setLobbyLocation(location);
    }

    @Override
    public Location getEndLocation() {
        return getArena().getEndLocation();
    }

    @Override
    public void setEndLocation(Location location) {
        getArena().setEndLocation(location);
    }

    @Override
    public CmdResult receiveCommand(CommandSender sender, String[] args) {
        boolean isPlayer = false;
        Player player = null;
        if (sender instanceof Player) {
            isPlayer = true;
            player = ((Player) sender);
        }
        if (args[0].equalsIgnoreCase("setlobby") || args[0].equalsIgnoreCase("setend")) {
            if (isPlayer) {
                if (args[0].equalsIgnoreCase("setlobby")) {
                    setLobbyLocation(player.getLocation());
                    player.sendMessage(ChatColor.GREEN + "Lobby location of " +
                                    ChatColor.BLUE + getArena().getArenaName() +
                                    ChatColor.GREEN + " set!"
                    );
                } else if (args[0].equalsIgnoreCase("setend")) {
                    setEndLocation(player.getLocation());
                    player.sendMessage(ChatColor.GREEN + "End location of " +
                                    ChatColor.BLUE + getArena().getArenaName() +
                                    ChatColor.GREEN + " set!"
                    );
                }
                getArena().save();
            } else {
                if (args.length > 1) {
                    Player refPlayer = getPlugin().getServer().getPlayer(args[1]);
                    if (refPlayer != null) {
                        if (args[0].equalsIgnoreCase("setlobby")) {
                            setLobbyLocation(refPlayer.getLocation());
                            sender.sendMessage(ChatColor.GREEN + "Lobby location of " +
                                            ChatColor.BLUE + getArena().getArenaName() +
                                            ChatColor.GREEN + " set!"
                            );
                        } else if (args[0].equalsIgnoreCase("setend")) {
                            setEndLocation(refPlayer.getLocation());
                            sender.sendMessage(ChatColor.GREEN + "End location of " +
                                            ChatColor.BLUE + getArena().getArenaName() +
                                            ChatColor.GREEN + " set!"
                            );
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "Error : player " + args[1] + " not found!");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Error : please provide a player name when running in console mode!");
                }
                getArena().save();
            }
        } else if (args[0].equalsIgnoreCase("set")) {

        }
        return new CmdResult(null, true);
    }
}

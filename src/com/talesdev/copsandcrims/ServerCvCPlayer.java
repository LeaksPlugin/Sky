package com.talesdev.copsandcrims;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represent all CvCPlayer in current server
 *
 * @author MoKunz
 */
public class ServerCvCPlayer {
    private List<CvCPlayer> playerList = new ArrayList<>();

    public void addNewPlayer(CvCPlayer cvcPlayer) {
        if (!getPlayerList().contains(cvcPlayer)) {
            getPlayerList().add(cvcPlayer);
        }
    }

    public boolean contains(Player player) {
        return getPlayer(player) != null;
    }

    public CvCPlayer getPlayer(Player player) {
        for (CvCPlayer cvCPlayer : getPlayerList()) {
            if (cvCPlayer.getPlayerName().equalsIgnoreCase(player.getName())) {
                return cvCPlayer;
            }
        }
        return null;
    }

    private List<CvCPlayer> getPlayerList() {
        return playerList;
    }

    public Set<CvCPlayer> getAllPlayers() {
        return new HashSet<>(getPlayerList());
    }

    public CvCPlayer createPlayer(Player player) {
        return new CvCPlayer(player);
    }
}

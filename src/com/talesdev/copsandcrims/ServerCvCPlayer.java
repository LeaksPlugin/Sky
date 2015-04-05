package com.talesdev.copsandcrims;

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

    private List<CvCPlayer> getPlayerList() {
        return playerList;
    }

    public Set<CvCPlayer> getAllPlayers() {
        return new HashSet<>(getPlayerList());
    }

    public CvCPlayer createPlayer() {

    }
}

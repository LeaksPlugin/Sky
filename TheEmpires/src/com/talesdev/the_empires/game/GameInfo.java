package com.talesdev.the_empires.game;

/**
 * @author MoKunz
 */
public class GameInfo {
    private String mapName;
    private int maxPlayers;

    public GameInfo(String mapName, int maxPlayers) {
        this.mapName = mapName;
        this.maxPlayers = maxPlayers;
    }

    public int maxPlayers() {
        return maxPlayers;
    }

    public String getMapName() {
        return mapName;
    }

    public boolean readyToStart(int playerCount) {
        return (playerCount / maxPlayers) * 100F > 66;
    }
}

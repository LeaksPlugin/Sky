package com.talesdev.copsandcrims;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * CvC Player
 * @author MoKunz
 */
public class CvCPlayer {
    private String playerName;
    public CvCPlayer(String playerName){
        this.playerName = playerName;
    }
    public Player getPlayer(){
        return Bukkit.getPlayer(playerName);
    }
    public String getPlayerName(){
        return playerName;
    }
}
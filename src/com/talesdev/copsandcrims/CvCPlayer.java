package com.talesdev.copsandcrims;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * CvC Player
 * @author MoKunz
 */
public class CvCPlayer {
    private double armorPoint;
    private String playerName;
    public CvCPlayer(String playerName){
        this.playerName = playerName;
    }

    public void setArmorPoint(double armorPoint) {
        if (armorPoint > 0) {
            this.armorPoint = armorPoint;
        }
    }

    public double getArmorPoint() {
        return armorPoint;
    }
    public Player getPlayer(){
        return Bukkit.getPlayer(playerName);
    }
    public String getPlayerName(){
        return playerName;
    }
}
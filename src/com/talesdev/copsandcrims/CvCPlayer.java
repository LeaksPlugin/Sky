package com.talesdev.copsandcrims;

import com.talesdev.copsandcrims.weapon.WeaponRecoil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * CvC Player
 * @author MoKunz
 */
public class CvCPlayer {
    private double armorPoint;
    private List<WeaponRecoil> recoils;
    private String playerName;
    private Player player;

    public CvCPlayer(Player player) {
        this.player = player;
        this.recoils = new ArrayList<>();
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

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CvCPlayer && getPlayerName().equalsIgnoreCase(((CvCPlayer) obj).getPlayerName());
    }
}
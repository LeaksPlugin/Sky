package com.talesdev.core.player;

import com.talesdev.core.TalesCore;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;

/**
 * Class for looking up player info and status
 * @author MoKunz
 */
public class PlayerInfo {
    private String playerName;
    public PlayerInfo(String playerName){
        this.playerName = playerName;
    }
    public String getUUID(){
        return "";
    }
    public String getPlayerName(){
        return playerName;
    }
}

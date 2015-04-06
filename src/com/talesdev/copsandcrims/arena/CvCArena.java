package com.talesdev.copsandcrims.arena;

import com.talesdev.copsandcrims.player.CvCPlayer;

import java.util.List;

/**
 * CvC Arena
 * @author MoKunz
 */
public class CvCArena {
    private List<CvCPlayer> players;
    private String arenaName;
    public CvCArena(String arenaName,CvCArenaController controller){
        this.arenaName = arenaName;
        controller.setControlledArena(this);
    }
    public void addPlayer(CvCPlayer player){
        players.add(player);
    }
    public void removePlayer(CvCPlayer player){

    }
    public String getArenaName(){
        return arenaName;
    }
    public List<CvCPlayer> getAllPlayers(){
        return players;
    }
}

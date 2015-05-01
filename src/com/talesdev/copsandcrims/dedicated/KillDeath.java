package com.talesdev.copsandcrims.dedicated;

import org.bukkit.entity.Player;

/**
 * Kill Death
 *
 * @author MoKunz
 */
public class KillDeath {
    private int kills = 0;
    private int deaths = 0;
    private int assists = 0;
    private Player player;

    public KillDeath(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void addKill() {
        kills++;
    }

    public void addDeath() {
        deaths++;
    }

    public void addAssists() {
        assists++;
    }
}

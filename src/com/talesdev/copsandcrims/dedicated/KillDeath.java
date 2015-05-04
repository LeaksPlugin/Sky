package com.talesdev.copsandcrims.dedicated;

import com.talesdev.core.arena.MultiKill;
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
    private long lastKills = 0;
    private MultiKill multiKill;
    private Player player;

    public KillDeath(Player player) {
        this.player = player;
        multiKill = MultiKill.SINGLE;
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
        lastKills = System.currentTimeMillis();
        if (System.currentTimeMillis() - lastKills <= 1000 * 8) {
            multiKill = MultiKill.get(multiKill.getRequired() + 1);
            //player.sendMessage(ChatColor.RED + multiKill.getWord());
        } else {
            multiKill = MultiKill.SINGLE;
        }
    }

    public void addDeath() {
        deaths++;
    }

    public void addAssists() {
        assists++;
    }

    public MultiKill getMultiKill() {
        return multiKill;
    }
}

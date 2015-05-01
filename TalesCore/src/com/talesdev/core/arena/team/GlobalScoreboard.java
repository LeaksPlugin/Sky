package com.talesdev.core.arena.team;

import com.talesdev.core.arena.GameArena;
import com.talesdev.core.arena.Joinable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashSet;
import java.util.Set;

/**
 * Global Team
 *
 * @author MoKunz
 */
public class GlobalScoreboard implements Joinable {
    private Scoreboard globalScoreboard;
    private GameArena gameArena;
    private GlobalTeam team;
    private Set<LocalTeam> localTeamSet;

    public GlobalScoreboard(GameArena gameArena) {
        this.gameArena = gameArena;
        this.globalScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.team = new GlobalTeam(gameArena);
        this.localTeamSet = new HashSet<>();
    }

    public GameArena getGameArena() {
        return gameArena;
    }

    public Scoreboard getScoreboard() {
        return globalScoreboard;
    }

    public void updateLocalTeam() {
        localTeamSet.forEach(LocalTeam::update);
    }

    public GlobalTeam getTeam() {
        return team;
    }

    public Set<LocalTeam> getLocalTeamSet() {
        return new HashSet<>(localTeamSet);
    }

    public void addLocalTeam(Player player) {
        localTeamSet.add(new LocalTeam(player, this));
    }

    public LocalTeam getLocalTeam(Player player) {
        for (LocalTeam localTeam : localTeamSet) {
            if (localTeam.getPlayer().equals(player)) {
                return localTeam;
            }
        }
        return null;
    }

    public boolean containsLocalTeam(Player player) {
        for (LocalTeam localTeam : localTeamSet) {
            if (localTeam.getPlayer().equals(player)) {
                return true;
            }
        }
        return false;
    }

    public void removeLocalTeam(Player player) {
        localTeamSet.remove(getLocalTeam(player));
    }

    @Override
    public boolean join(Player player) {
        addLocalTeam(player);
        return true;
    }

    @Override
    public boolean leave(Player player) {
        removeLocalTeam(player);
        return true;
    }
}

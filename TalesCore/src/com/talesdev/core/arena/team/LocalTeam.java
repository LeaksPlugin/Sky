package com.talesdev.core.arena.team;

import com.talesdev.core.TalesCore;
import com.talesdev.core.player.CorePlayer;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/**
 * Local team (player specific)
 *
 * @author MoKunz
 */
public class LocalTeam {
    private Player player;
    private GlobalScoreboard scoreboard;

    /**
     * Construct a local team adapter for player
     *
     * @param player           A player
     * @param globalScoreboard scoreboard which contain arena teams
     */
    public LocalTeam(Player player, GlobalScoreboard globalScoreboard) {
        this.player = player;
        this.scoreboard = globalScoreboard;
    }

    /**
     * Synchronize arena team with player Local Team
     */
    public void update() {
        CorePlayer corePlayer = getCorePlayer(player);
        Scoreboard localScoreboard = corePlayer.getPlayerScoreboard();
        // all teams in scoreboard
        for (Team globalTeam : getScoreboard().getAllTeam()) {
            Team localTeam = localScoreboard.getTeam(globalTeam.getName());
            // create if not exists
            if (localTeam == null) {
                localTeam = localScoreboard.registerNewTeam(globalTeam.getName());
                globalTeam.getPlayers().forEach(localTeam::addPlayer);
            }
            // sync globalTeam settings
            localTeam.setNameTagVisibility(globalTeam.getNameTagVisibility());
            localTeam.setPrefix(globalTeam.getPrefix());
            localTeam.setCanSeeFriendlyInvisibles(globalTeam.canSeeFriendlyInvisibles());
            localTeam.setAllowFriendlyFire(globalTeam.allowFriendlyFire());
            localTeam.setDisplayName(globalTeam.getDisplayName());
            localTeam.setSuffix(globalTeam.getSuffix());
            // sync player (add)
            for (OfflinePlayer offlinePlayer : globalTeam.getPlayers()) {
                if (!localTeam.hasPlayer(offlinePlayer)) {
                    localTeam.addPlayer(offlinePlayer);
                }
            }
            // sync player (remove)
            for (OfflinePlayer offlinePlayer : localTeam.getPlayers()) {
                if (!globalTeam.hasPlayer(offlinePlayer)) {
                    localTeam.removePlayer(offlinePlayer);
                }
            }
        }
    }

    /**
     * Get owner of this LocalTeam
     *
     * @return player
     */
    public Player getPlayer() {
        return player;
    }


    /**
     * Get global scoreboard
     *
     * @return global scoreboard
     */
    public GlobalScoreboard getScoreboard() {
        return scoreboard;
    }

    /**
     * Method for getting CorePlayer
     *
     * @param player A player
     * @return CorePlayer
     */
    public CorePlayer getCorePlayer(Player player) {
        return TalesCore.getPlugin().getCorePlayer(player);
    }
}

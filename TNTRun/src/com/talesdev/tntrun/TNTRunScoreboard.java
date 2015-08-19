package com.talesdev.tntrun;

import com.talesdev.core.arena.scoreboard.DisplayScoreboard;
import com.talesdev.core.scoreboard.WrappedScoreboard;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * @author MoKunz
 */
public class TNTRunScoreboard implements DisplayScoreboard<TNTRunGameArena> {
    private TNTRunGameArena arena;

    public TNTRunScoreboard(TNTRunGameArena gameArena) {
        arena = gameArena;
    }

    @Override
    public void start(Player player) {
        WrappedScoreboard sb = arena.getCorePlayer(player).getWrappedScoreboard();
        sb.reset();
        sb.setTitle(ChatColor.RED + "TNTGame - TNTRun");
        sb.setMaxLine(12);
        sb.setLine(11, "Double Jumps: " + ChatColor.YELLOW + "0/0");
        sb.setLine(9, "Time: " + ChatColor.GREEN + "00:00");
        sb.setLine(7, "");
        sb.update();
    }

    @Override
    public void update(Player player) {

    }

    @Override
    public Class<TNTRunScoreboard> getType() {
        return TNTRunScoreboard.class;
    }

    @Override
    public TNTRunGameArena getGameArena() {
        return arena;
    }
}

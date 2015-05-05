package com.talesdev.core.arena;

import com.talesdev.core.player.message.Title;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * First blood
 *
 * @author MoKunz
 */
public class FirstBlood {
    private GameArena gameArena;
    private Player victim;
    private String deathMessage;

    public FirstBlood(GameArena gameArena, Player victim, String deathMessage) {
        this.gameArena = gameArena;
        this.victim = victim;
        this.deathMessage = deathMessage;
    }

    public void display() {
        Title title = new Title(ChatColor.RED + "First Blood!", deathMessage, 5, 30, 15);
        Set<Player> playerSet = gameArena.getPlayerSet().stream().filter(
                playerPredicate -> !playerPredicate.equals(victim)).collect(Collectors.toSet());
        title.send(playerSet);
    }
}

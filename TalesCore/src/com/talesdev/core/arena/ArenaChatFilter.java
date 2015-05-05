package com.talesdev.core.arena;

import org.bukkit.ChatColor;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scoreboard.Team;

import java.util.HashSet;
import java.util.function.Predicate;

/**
 * Team chat filter
 * TODO : More chat filter feature
 *
 * @author MoKunz
 */
public class ArenaChatFilter {
    private GameArena gameArena;
    private AsyncPlayerChatEvent event;
    private String message;
    private Predicate<String> teamPredicate;
    private boolean color = true;

    public ArenaChatFilter(AsyncPlayerChatEvent event, GameArena gameArena) {
        this.event = event;
        this.gameArena = gameArena;
        this.message = event.getMessage();
        this.teamPredicate = (message) -> message.matches("@.+");
    }

    public void teamChatIf(Predicate<String> messagePredicate) {
        if (messagePredicate != null) {
            this.teamPredicate = messagePredicate;
        }
    }

    public void apply() {
        Team team = gameArena.getTeam().getTeam(event.getPlayer());
        boolean teamChat = false;
        String visibility = ChatColor.GRAY + "[All]" + ChatColor.RESET;
        if (teamPredicate.test(message)) {
            visibility = ChatColor.GRAY + "[Team]" + ChatColor.RESET;
            teamChat = true;
        }
        if (team != null) {
            String teamName = "";
            if (team.getName().equals("Terrorist")) {
                teamName = ChatColor.RED.toString() + "[\u9291]";
            } else {
                teamName = ChatColor.BLUE.toString() + "[\u9290]";
            }
            event.setFormat(visibility + teamName + ChatColor.RESET + "%s : %s");
            if (teamChat) {
                new HashSet<>(event.getRecipients()).stream().filter(player -> !team.hasPlayer(player)).forEach(player -> {
                    event.getRecipients().remove(player);
                });
                event.setMessage(event.getMessage().replaceFirst("@", ""));
            }
        } else {
            event.setFormat(ChatColor.LIGHT_PURPLE + "[All]" + ChatColor.RESET + "%s : %s");
        }
        if (color) {
            event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
        }
    }

    public boolean isColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }
}
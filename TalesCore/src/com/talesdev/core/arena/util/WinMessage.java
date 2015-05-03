package com.talesdev.core.arena.util;

import com.talesdev.core.arena.GameArena;
import com.talesdev.core.player.message.Title;
import com.talesdev.core.text.AlignedMessage;
import org.bukkit.ChatColor;

/**
 * Win message
 *
 * @author MoKunz
 */
public class WinMessage {
    private GameArena gameArena;
    private String winner;
    private String headMessage;

    public WinMessage(GameArena gameArena, String headMessage, String winner) {
        this.gameArena = gameArena;
        this.winner = winner;
        this.headMessage = headMessage;
    }

    public void send() {
        // title
        Title title = new Title(ChatColor.GREEN + "" + ChatColor.BOLD + "Winner", winner, 5, 100, 0);
        title.send(gameArena.getPlayerSet());
        // normal msg
        gameArena.sendMessage(new AlignedMessage("").center());
        gameArena.sendMessage(new AlignedMessage("").center());
        gameArena.sendMessage(new AlignedMessage(ChatColor.RED + ChatColor.BOLD.toString() + headMessage).center());
        gameArena.sendMessage(new AlignedMessage("").center());
        gameArena.sendMessage(new AlignedMessage(
                ChatColor.YELLOW + "Winner" + ChatColor.GRAY + " - " + winner
        ).center());
        gameArena.sendMessage(new AlignedMessage("").center());
        gameArena.sendMessage(new AlignedMessage("").center());
        gameArena.sendMessage(new AlignedMessage("").center());
    }
}

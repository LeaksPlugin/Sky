package com.talesdev.core.arena.phase;

import com.talesdev.core.arena.GameArena;
import com.talesdev.core.arena.GameState;
import com.talesdev.core.network.BungeeServer;
import com.talesdev.core.player.CleanedPlayer;
import org.bukkit.event.HandlerList;

/**
 * End phase
 *
 * @author MoKunz
 */
public class EndPhase implements GamePhase {

    @Override
    public void dispatch(GameArena arena) {
        if (arena.getGameState().equals(GameState.END)) {
            return;
        }
        arena.setGameState(GameState.END);
        arena.stopGame();
        arena.getScheduler().runTaskLater(arena.getPlugin(), () -> {
            arena.getPlayerSet().forEach(player -> {
                CleanedPlayer cp = new CleanedPlayer(player);
                cp.clean();
            });
            BungeeServer server = new BungeeServer(arena.getPlugin(), "lobby");
            arena.getPlayerSet().forEach(server::send);
            arena.setGameState(GameState.RESET);
            // perform regeneration

            // end regen
            // unregister listener
            HandlerList.unregisterAll(arena.getListener());
            arena.init();
        }, 120L);
    }
}

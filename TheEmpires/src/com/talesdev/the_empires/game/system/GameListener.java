package com.talesdev.the_empires.game.system;

import com.talesdev.core.arena.GameState;
import com.talesdev.the_empires.TheEmpiresPermission;
import com.talesdev.the_empires.game.TheEmpiresGame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * @author MoKunz
 */
public final class GameListener implements Listener {
    private TheEmpiresGame game;

    public GameListener(TheEmpiresGame game) {
        this.game = game;
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        GameState gameState = game.getGameState();
        Player breaker = event.getPlayer();
        if (gameState.canJoin()) {
            if (!breaker.hasPermission(TheEmpiresPermission.ARENA_WAITING_BREAK_BLOCK)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onHungerChange(FoodLevelChangeEvent event) {
        if (game.getGameState().canJoin()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (game.getGameState().canJoin()) {
            event.setCancelled(true);
        }
    }
}

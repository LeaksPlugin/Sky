package com.talesdev.tntrun;

import com.talesdev.core.arena.GameState;
import com.talesdev.core.arena.event.ArenaCountdownEvent;
import com.talesdev.core.arena.event.GeneralArenaListener;
import com.talesdev.core.arena.event.PlayerJoinArenaEvent;
import com.talesdev.core.arena.event.PlayerLeaveArenaEvent;
import com.talesdev.core.arena.scoreboard.LobbyScoreboard;
import com.talesdev.core.player.CleanedPlayer;
import com.talesdev.core.player.message.Title;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Main listener
 *
 * @author MoKunz
 */
public class TNTRunGameArenaListener extends GeneralArenaListener<TNTRunGameArena> {
    public TNTRunGameArenaListener(TNTRunGameArena game) {
        super(game);
    }

    @EventHandler
    public void onArenaJoin(PlayerJoinArenaEvent event) {
        CleanedPlayer cp = new CleanedPlayer(event.getPlayer());
        cp.clean();
        event.setAfterRun(() -> {
            LobbyScoreboard lobby = getGameArena().getLobbyScoreboard();
            lobby.setCountdown(-1);
            lobby.setMaxPlayers(getGameArena().getMaxPlayers());
            lobby.setPlayers(getGameArena().playing());
            getGameArena().initDisplay(event.getPlayer(), lobby);
            getGameArena().updateDisplay(lobby);
        });
    }

    @EventHandler
    public void onArenaCountdown(ArenaCountdownEvent event) {
        // countdown scoreboard update
        LobbyScoreboard lobby = getGameArena().getLobbyScoreboard();
        lobby.setCountdown(event.getCurrentCountdown());
        getGameArena().updateDisplay(lobby);
        // other stuff
        // title
        if (event.getCurrentCountdown() <= 10) {
            String color = calculateColor(event.getCurrentCountdown());
            Title title = new Title(color + event.getCurrentCountdown(), ChatColor.YELLOW + "seconds left until the game start!", 0, 30, 0);
            title.send(getGameArena().getPlayerSet());
        }
    }

    private String calculateColor(int time) {
        if (time <= 2) {
            return ChatColor.RED.toString();
        } else if (time <= 4) {
            return ChatColor.GOLD.toString();
        } else if (time <= 7) {
            return ChatColor.YELLOW.toString();
        } else {
            return ChatColor.GREEN.toString();
        }
    }

    @EventHandler
    public void onArenaLeave(PlayerLeaveArenaEvent event) {
        // update player count
        if (getGameArena().getGameState().canJoin()) {
            LobbyScoreboard lobby = getGameArena().getLobbyScoreboard();
            lobby.setPlayers(getGameArena().playing());
            getGameArena().updateDisplay(lobby);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        TNTFloorSystem floorSystem = getGameArena().getFloorSystem();
        if (getGameArena().getGameState().equals(GameState.STARTED)) {
            Location moveTo = event.getTo();
            if (moveTo.getY() < 0) {
                floorSystem.fallToVoid(event.getPlayer());
                return;
            }
            StandingBlock block = new StandingBlock(event.getPlayer());
            block.find().forEach((b) -> floorSystem.walk(b.getLocation()));
        }
    }

    private boolean hasDelta(int p1, int p2) {
        return Math.abs(p1 - p2) == 1;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onTNTExplode(ExplosionPrimeEvent explodeEvent) {
        explodeEvent.setCancelled(true);
    }

    @EventHandler
    public void onBlockFalling(BlockPhysicsEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockChange(EntityChangeBlockEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            event.setCancelled(true);
        }
    }
}

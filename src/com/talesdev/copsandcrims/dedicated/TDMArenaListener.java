package com.talesdev.copsandcrims.dedicated;

import com.talesdev.copsandcrims.event.EntityDamageByWeaponEvent;
import com.talesdev.core.TalesCore;
import com.talesdev.core.arena.GameState;
import com.talesdev.core.arena.event.ArenaCountdownEvent;
import com.talesdev.core.arena.event.GeneralArenaListener;
import com.talesdev.core.arena.event.PlayerJoinArenaEvent;
import com.talesdev.core.arena.event.PlayerLeaveArenaEvent;
import com.talesdev.core.arena.scoreboard.LobbyScoreboard;
import com.talesdev.core.entity.DamageData;
import com.talesdev.core.player.AutoRespawn;
import com.talesdev.core.player.CleanedPlayer;
import com.talesdev.core.player.CorePlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.*;
import org.bukkit.scoreboard.Team;

import java.util.List;

/**
 * TDM Arena Listener
 *
 * @author MoKunz
 */
public class TDMArenaListener extends GeneralArenaListener<TDMGameArena> {

    public TDMArenaListener(TDMGameArena gameArena) {
        super(gameArena);
    }

    @EventHandler
    public void onArenaJoin(PlayerJoinArenaEvent event) {
        CleanedPlayer cp = new CleanedPlayer(event.getPlayer());
        cp.clean();
        event.setAfterRun(() -> {
            LobbyScoreboard lobby = getGameArena().getLobbyScoreboard();
            lobby.setMapName(ChatColor.GREEN + "NealTheFarmer");
            lobby.setTitle(ChatColor.RED + "CvC - TDM");
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
    public void onHunger(FoodLevelChangeEvent event) {
        if (getGameArena().containsPlayer(Bukkit.getPlayer(event.getEntity().getName()))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void regenEvent(EntityRegainHealthEvent event) {
        if (event.getEntityType().equals(EntityType.PLAYER)) {
            Player player = ((Player) event.getEntity());
            if (getGameArena().containsPlayer(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void interactEvent(BlockFromToEvent event) {
        if (event.getBlock() != null && event.getToBlock() != null) {
            if (event.getBlock().getType().equals(Material.SOIL)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (getGameArena().containsPlayer(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = ((Player) event.getDamager());
            if (getGameArena().containsPlayer(player)) {
                if (!getGameArena().getGameState().equals(GameState.STARTED)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onTeamDamage(EntityDamageByWeaponEvent event) {
        if (getGameArena().getGameState().equals(GameState.END)) {
            event.setCancelled(true);
        }
        if (event.getEntity() instanceof Player) {
            Player player = ((Player) event.getEntity());
            // find team
            Team team = getGameArena().getTeam().getTeam(player);
            if (team.hasPlayer(event.getPlayer())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (getGameArena().containsPlayer(event.getEntity())) {
            // spectator
            Player player = event.getEntity();
            event.getDrops().clear();
            player.getInventory().clear();
            player.setGameMode(GameMode.SPECTATOR);
            getGameArena().getPlayerKD(player).addDeath();
            // add kill to killer
            CorePlayer corePlayer = TalesCore.getPlugin().getCorePlayer(player);
            DamageData last = corePlayer.getPlayerDamage().getLastEntity();
            if (last.getDamager() instanceof Player) {
                Player damager = ((Player) last.getDamager());
                if (last.getAttachment("Weapon") != null && last.getDamageCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
                    if (getGameArena().containsPlayer(damager)) {
                        getGameArena().getPlayerKD(damager).addKill();
                        Team team = getGameArena().getTeam().getTeam(damager);
                        if (team != null) {
                            getGameArena().addKill(team.getName());
                        }
                    }
                }
            }
            List<DamageData> damageDataList = corePlayer.getPlayerDamage().getEntityDamage();
            damageDataList.stream().filter(damageData -> damageData != last).filter(damageData -> last.getDamager() instanceof Player).forEach(damageData -> {
                Player damager = ((Player) last.getDamager());
                if (last.getAttachment("Weapon") != null && last.getDamageCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
                    if (getGameArena().containsPlayer(damager)) {
                        getGameArena().getPlayerKD(damager).addAssists();
                    }
                }
            });
            // TODO : Update player K/D
            // TODO : Update team kills
            if (getGameArena().getGameState().equals(GameState.STARTED)) {
                getGameArena().checkStats();
            }
            // scoreboard
            getGameArena().getPlayerSet().forEach(getGameArena().getLobbyScoreboard()::update);
            // force respawn
            AutoRespawn autoRespawn = new AutoRespawn(player);
            autoRespawn.perform();
        }
    }

}

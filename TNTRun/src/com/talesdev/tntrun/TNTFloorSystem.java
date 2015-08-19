package com.talesdev.tntrun;

import com.talesdev.core.arena.phase.EndPhase;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author MoKunz
 */
public class TNTFloorSystem {
    private TNTRunGameArena game;
    private Set<Material> floorMaterial;
    private Set<Player> playing;
    private Set<Player> spectators;
    private String winner = null;
    private boolean systemActivated = false;

    public TNTFloorSystem(TNTRunGameArena game) {
        this.game = game;
        playing = new HashSet<>();
        spectators = new HashSet<>();
        floorMaterial = new HashSet<>();
        floorMaterial.add(Material.TNT);
    }

    public void startGame() {
        playing.addAll(game.getPlayerSet());
        game.getScheduler().runTaskLater(game.getPlugin(), () -> systemActivated = true, 60);
    }

    public Set<Material> allMaterials() {
        return new HashSet<>(floorMaterial);
    }

    public void addMaterial(Material m) {
        floorMaterial.add(m);
    }

    public void removeMaterial(Material m) {
        floorMaterial.remove(m);
    }

    public void walk(Location location) {
        if (!systemActivated) return;
        Block block = location.getBlock();
        if (block != null) {
            if (floorMaterial.contains(block.getType())) {
                game.getScheduler().runTaskLater(game.getPlugin(), () -> {
                    game.getBlockRegen().breakBlock(block);
                    block.setType(Material.AIR);
                }, 10);
            }
        }
    }

    public Set<Player> getPlaying() {
        return new HashSet<>(playing);
    }

    public void fallToVoid(Player player) {
        playing.remove(player);
        spectators.add(player);
        player.teleport(game.getSpawn());
        player.setGameMode(GameMode.SPECTATOR);
        player.sendMessage(ChatColor.YELLOW + "You are now spectating!");
        checkForWinner();
    }

    protected void remove(Player player) {
        if (playing.contains(player)) {
            playing.remove(player);
        } else if (spectators.contains(player)) {
            spectators.remove(player);
        }
        checkForWinner();
    }

    public void checkForWinner() {
        if (playing.size() == 1) {
            winner = new ArrayList<>(playing).get(0).getName();
        } else if (playing.size() <= 0) {
            winner = ChatColor.YELLOW + "Draw";
        }
        game.dispatchPhase(new EndPhase());
    }

    public String getWinner() {
        return winner;
    }

    public Set<Player> getSpectators() {
        return new HashSet<>(spectators);
    }

    public void reset() {
        playing.clear();
        spectators.clear();
        systemActivated = false;
    }
}

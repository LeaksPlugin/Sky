package com.talesdev.tntrun;

import com.talesdev.core.arena.phase.EndPhase;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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

    public TNTFloorSystem(TNTRunGameArena game) {
        this.game = game;
        playing = new HashSet<>();
        spectators = new HashSet<>();
        floorMaterial = new HashSet<>();
        floorMaterial.add(Material.SAND);
        floorMaterial.add(Material.GRAVEL);
    }

    public void startGame() {
        playing.addAll(game.getPlayerSet());
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
        Block block = location.getBlock();
        if (block != null) {
            if (block.getType().equals(Material.AIR)) {
                return;
            }
            if (floorMaterial.contains(block.getType())) {
                Block tnt = block.getRelative(BlockFace.DOWN);
                if (tnt != null) {
                    final Location locSand = block.getLocation();
                    final Location locTNT = tnt.getLocation();
                    game.getScheduler().runTaskLater(game.getPlugin(), () -> {
                        Block locSandBlock = locSand.getBlock();
                        Block locTNTBlock = locTNT.getBlock();
                        game.getBlockRegen().breakBlock(locSandBlock);
                        game.getBlockRegen().breakBlock(locTNTBlock);
                        locSandBlock.setType(Material.AIR);
                        locTNTBlock.setType(Material.AIR);
                    }, 12);
                }
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

    public void clearPlayers() {
        playing.clear();
        spectators.clear();
    }
}

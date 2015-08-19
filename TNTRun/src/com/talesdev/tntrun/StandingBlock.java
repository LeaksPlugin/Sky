package com.talesdev.tntrun;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Standing block
 *
 * @author MoKunz
 */
public class StandingBlock {
    private Player player;

    public StandingBlock(Player player) {
        this.player = player;
    }

    public List<Block> find() {
        List<Block> blocksBelow = new ArrayList<>();
        Location location = player.getLocation();
        double x = location.getX();
        double z = location.getZ();
        World world = player.getWorld();
        double yBelow = player.getLocation().getY() - 0.0001;
        Block northEast = new Location(world, x + 0.3, yBelow, z - 0.3).getBlock();
        Block northWest = new Location(world, x - 0.3, yBelow, z - 0.3).getBlock();
        Block southEast = new Location(world, x + 0.3, yBelow, z + 0.3).getBlock();
        Block southWest = new Location(world, x - 0.3, yBelow, z + 0.3).getBlock();
        Block[] blocks = {northEast, northWest, southEast, southWest};
        for (Block block : blocks) {
            if (!blocksBelow.isEmpty()) {
                boolean duplicateExists = false;
                for (Block aBlocksBelow : blocksBelow) {
                    if (aBlocksBelow.equals(block)) {
                        duplicateExists = true;
                    }
                }
                if (!duplicateExists) {
                    blocksBelow.add(block);
                }
            } else {
                blocksBelow.add(block);
            }
        }
        return blocksBelow;
    }
}

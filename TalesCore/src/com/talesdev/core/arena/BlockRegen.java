package com.talesdev.core.arena;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

import java.util.HashSet;
import java.util.Set;

/**
 * Block regen
 *
 * @author MoKunz
 */
public class BlockRegen {
    private Set<BlockState> blockStateSet;
    private GameArena gameArena;
    private boolean running;

    public BlockRegen(GameArena gameArena) {
        this.gameArena = gameArena;
        blockStateSet = new HashSet<>();
    }

    public void breakBlock(Block block) {
        if (block != null) {
            blockStateSet.add(block.getState());
        }
    }

    public void regen() {
        if (!gameArena.getGameState().equals(GameState.RESET)) {
            throw new IllegalStateException("Arena status must be GameState.RESET!");
        }
        for (BlockState state : blockStateSet) {
            Block block = state.getBlock();
            block.setType(state.getType());
            block.setData(state.getRawData());
        }
    }

    public Set<BlockState> getBlockStateSet() {
        return new HashSet<>(blockStateSet);
    }

    public void startRegen(int blockPerTick) {
        BlockRegenTask regenTask = new BlockRegenTask(this, blockPerTick);
        regenTask.onFinished(() -> running = false);
        running = true;
        regenTask.start();
    }

    public void reset() {
        blockStateSet.clear();
    }

    public GameArena getGameArena() {
        return gameArena;
    }

    public boolean isRunning() {
        return running;
    }
}

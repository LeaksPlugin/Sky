package com.talesdev.core.arena;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Block regen
 *
 * @author MoKunz
 */
public class BlockRegen {
    private List<BlockState> blockStateSet;
    private Map<Material, Integer> priority;
    private GameArena gameArena;
    private boolean running;
    private BlockRegenTask regenTask;
    private Runnable onFinished = () -> {
    };
    private int defaultSpeed = 100;

    public BlockRegen(GameArena gameArena) {
        this.gameArena = gameArena;
        blockStateSet = new ArrayList<>();
        priority = new HashMap<>();
    }

    public void setPriority(Material material, int priority) {
        this.priority.put(material, priority);
    }

    public int getPriority(Material material) {
        return priority.get(material);
    }

    public Map<Material, Integer> getPriorities() {
        return new HashMap<>(priority);
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
            if (state.getType() == Material.AIR) {
                continue;
            }
            block.setType(state.getType());
            block.setData(state.getRawData());
        }
    }

    public List<BlockState> getBlockStateSet() {
        return new ArrayList<>(blockStateSet);
    }

    public void startRegen(int blockPerTick) {
        if (running) return;
        regenTask = new BlockRegenTask(this, blockPerTick);
        regenTask.onFinished(() -> {
            running = false;
            onFinished.run();
        });
        running = true;
        regenTask.start();
    }

    public void startRegen() {
        startRegen(defaultSpeed);
    }

    public void reset() {
        stopRegen();
        running = false;
        blockStateSet.clear();
        priority.clear();
    }

    public GameArena getGameArena() {
        return gameArena;
    }

    public boolean isRunning() {
        return running;
    }

    public void stopRegen() {
        regenTask.stop();
    }

    public void setSpeed(int defaultSpeed) {
        this.defaultSpeed = defaultSpeed;
    }

    public void onFinished(Runnable onFinished) {
        this.onFinished = onFinished;
    }
}

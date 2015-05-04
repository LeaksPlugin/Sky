package com.talesdev.core.arena;

import org.bukkit.block.BlockState;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Block regen task
 *
 * @author MoKunz
 */
public class BlockRegenTask extends BukkitRunnable {
    private final List<BlockState> regenList;
    private BlockRegen blockRegen;
    private boolean finished;
    private Runnable onFinished;
    private int processed;
    private int speed;

    public BlockRegenTask(BlockRegen blockRegen) {
        this(blockRegen, 10);
    }

    public BlockRegenTask(BlockRegen blockRegen, int speed) {
        this.blockRegen = blockRegen;
        this.speed = speed;
        regenList = new ArrayList<>();
        regenList.addAll(blockRegen.getBlockStateSet().stream().collect(Collectors.toList()));
    }

    public void start() {
        runTaskTimer(blockRegen.getGameArena().getPlugin(), 0, 1);
    }

    @Override
    public void run() {
        if (finished) {
            stop();
            return;
        }
        // processing
        for (int i = processed; i < processed + speed; i++) {
            if (i < regenList.size()) {
                BlockState blockState = regenList.get(i);
                blockState.getBlock().setType(blockState.getType());
                blockState.getBlock().setData(blockState.getRawData());
            } else {
                stop();
                return;
            }
        }
        // add processed
        processed += speed;
    }

    protected void stop() {
        onFinished.run();
        finished = true;
        cancel();
    }

    public boolean isFinished() {
        return finished;
    }

    public void onFinished(Runnable onFinished) {
        this.onFinished = onFinished;
    }
}

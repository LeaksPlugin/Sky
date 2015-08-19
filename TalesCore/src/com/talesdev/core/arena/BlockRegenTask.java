package com.talesdev.core.arena;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Block regen task
 *
 * @author MoKunz
 */
public class BlockRegenTask extends BukkitRunnable {
    private final List<BlockState> regenList;
    private final Map<Material, Integer> priorityMap;
    private BlockRegen blockRegen;
    private DecimalFormat format = new DecimalFormat("###.##");
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
        priorityMap = new HashMap<>();
        priorityMap.putAll(blockRegen.getPriorities());
        sortMaterialList();
    }

    private void sortMaterialList() {
        regenList.sort(new Comparator<BlockState>() {
            @Override
            public int compare(BlockState o1, BlockState o2) {
                Material m1 = o1.getType();
                Material m2 = o2.getType();
                int p1 = 0, p2 = 0;
                if (priorityMap.containsKey(m1)) p1 = priorityMap.get(m1);
                if (priorityMap.containsKey(m2)) p2 = priorityMap.get(m2);
                if (p1 <= p2) {
                    return p1 < p2 ? -1 : 0;
                } else {
                    return 1;
                }
            }
        });
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
        // stop early
        if (regenList.size() == 0) {
            stop();
            return;
        }
        // processing
        blockRegen.getGameArena().getLogger().info("Regeneration progress : " + format.format(((double) processed / (double) regenList.size()) * 100D) + "%");
        for (int i = processed; i < processed + speed; i++) {
            if (i < regenList.size()) {
                BlockState blockState = regenList.get(i);
                blockState.getBlock().setType(blockState.getType(), false);
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

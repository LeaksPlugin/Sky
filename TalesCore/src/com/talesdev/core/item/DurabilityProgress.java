package com.talesdev.core.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * Class represent a durability bar
 *
 * @author MoKunz
 */
public class DurabilityProgress {
    private ItemStack itemStack;
    private short maxDurability;

    public DurabilityProgress(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.maxDurability = itemStack.getType().getMaxDurability();
    }

    public void apply(int percent) {
        short totalDurability = (short) Math.round(((percent / 100D) * maxDurability));
        if (percent == 0) {
            itemStack.setDurability((short) 0);
        } else if (percent == 100) {
            itemStack.setDurability(maxDurability);
        } else {
            itemStack.setDurability(totalDurability);
        }
    }

    public void progressiveApply(Plugin plugin, int timeInTick) {
        DurabilityProgressTask task = new DurabilityProgressTask(this, timeInTick);
        task.runTaskTimer(plugin, 0, 1);
    }

    public void reset() {
        itemStack.setDurability((short) 0);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public short getMaxDurability() {
        return maxDurability;
    }
}

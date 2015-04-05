package com.talesdev.core.player;

import com.talesdev.core.TalesCore;
import com.talesdev.core.entity.MetaData;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Class used to calculate click per second
 *
 * @author MoKunz
 */
public class ClickingPerSecond {
    private MetaData metaData;

    public ClickingPerSecond(Player player) {
        this(player, TalesCore.getPlugin());
    }

    public ClickingPerSecond(Player player, Plugin plugin) {
        Player player1 = player;
        this.metaData = new MetaData(player, plugin);
    }

    public void record() {
        this.metaData.setMetadata("LastClickTime", System.currentTimeMillis());
    }

    public double get() {
        long timeStamp = this.metaData.getMetadata("LastClickTime", Long.TYPE);
        long total = System.currentTimeMillis() - timeStamp;
        return 1000D / total;
    }
}

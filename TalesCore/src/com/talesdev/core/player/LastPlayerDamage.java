package com.talesdev.core.player;

import com.talesdev.core.entity.MetaData;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;

/**
 * LastPlayerDamage
 *
 * @author MoKunz
 */
public class LastPlayerDamage {
    private MetaData metaData;

    public LastPlayerDamage(Metadatable metadatable, Plugin plugin) {
        this.metaData = new MetaData(metadatable, plugin);
    }

    protected MetaData getMetadata() {
        return metaData;
    }

    public void setLastDamage(LastDamageCause cause) {
        if (cause != null) getMetadata().setMetadata("LastDamageCause", cause);
    }

    public LastDamageCause getLastDamage() {
        if (getMetadata().getMetadata("LastDamageCause") instanceof LastDamageCause) {
            return (LastDamageCause) getMetadata().getMetadata("LastDamageCause");
        }
        return null;
    }
}

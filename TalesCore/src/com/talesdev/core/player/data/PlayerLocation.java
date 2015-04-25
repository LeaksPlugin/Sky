package com.talesdev.core.player.data;

import com.talesdev.core.config.Savable;
import com.talesdev.core.player.CorePlayer;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Player movement info
 *
 * @author MoKunz
 */
public class PlayerLocation implements Savable {
    private CorePlayer corePlayer;
    private boolean walking = false;
    private long lastWalkingTime;

    public PlayerLocation(CorePlayer corePlayer) {
        this.corePlayer = corePlayer;
        this.corePlayer.autoSave(this);
        this.lastWalkingTime = System.currentTimeMillis();
    }

    public CorePlayer getCorePlayer() {
        return corePlayer;
    }

    public void updateLastWalkingTime() {
        this.lastWalkingTime = System.currentTimeMillis();
    }

    public long getLastWalkingTime() {
        return lastWalkingTime;
    }

    @Override
    public void loadFrom(FileConfiguration config) {

    }

    @Override
    public void saveTo(FileConfiguration config) {

    }

    @Override
    public String getName() {
        return "PlayerLocation";
    }
}

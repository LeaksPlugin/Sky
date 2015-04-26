package com.talesdev.core.player.data;

import com.talesdev.core.config.Savable;
import com.talesdev.core.event.JoinLeaveNotify;
import com.talesdev.core.player.CorePlayer;
import com.talesdev.core.system.Destroyable;
import com.talesdev.core.world.LocationString;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitTask;

/**
 * Player movement info
 *
 * @author MoKunz
 */
public class PlayerLocation implements Savable, Destroyable, JoinLeaveNotify {
    public static final String WALKING_UPDATE_TASK = "walkingUpdateTask";
    private CorePlayer corePlayer;
    private Location lastJoinLocation;
    private Location lastLeaveLocation;
    private volatile boolean walking = false;
    private volatile long lastWalkingTime;

    public PlayerLocation(CorePlayer corePlayer) {
        this.corePlayer = corePlayer;
        this.corePlayer.autoSave(this);
        this.corePlayer.autoDestroy(this);
        this.lastWalkingTime = System.currentTimeMillis();
        this.lastJoinLocation = corePlayer.getPlayer().getLocation();
        this.lastLeaveLocation = corePlayer.getPlayer().getLocation();
    }

    private void initSystem() {
        BukkitTask task = corePlayer.getScheduler().runTaskTimerAsynchronously(corePlayer.getCore(), () -> {
            if ((System.currentTimeMillis() - getLastWalkingTime() > 200) && isWalking()) {
                setWalking(false);
            }
        }, 0, 1);
        corePlayer.getPlayerTask().store(WALKING_UPDATE_TASK, task);
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

    public boolean isWalking() {
        return walking;
    }

    public void setWalking(boolean walking) {
        this.walking = walking;
    }

    @Override
    public void loadFrom(FileConfiguration config) {
        lastJoinLocation = LocationString.fromString(config.getString(corePlayer.sectionOf(this, "join"))).getLocation();
        lastLeaveLocation = LocationString.fromString(config.getString(corePlayer.sectionOf(this, "leave"))).getLocation();
    }

    @Override
    public void saveTo(FileConfiguration config) {
        config.set(corePlayer.sectionOf(this, "join"), new LocationString(lastJoinLocation).toString());
        config.set(corePlayer.sectionOf(this, "leave"), new LocationString(lastLeaveLocation).toString());
    }

    @Override
    public String getName() {
        return "PlayerLocation";
    }

    @Override
    public void destroy() {
        corePlayer.getPlayerTask().cancel(WALKING_UPDATE_TASK);
    }

    public Location getLastJoinLocation() {
        return lastJoinLocation.clone();
    }

    public Location getLastLeaveLocation() {
        return lastLeaveLocation.clone();
    }

    @Override
    public void playerJoin() {
        lastJoinLocation = corePlayer.getPlayer().getLocation();
    }

    @Override
    public void playerLeave() {
        lastLeaveLocation = corePlayer.getPlayer().getLocation();
    }

    @Override
    public String toString() {
        return "PlayerLocation(" + corePlayer.getPlayer().getName() + "){walking:" + walking + "}";
    }
}

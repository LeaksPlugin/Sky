package com.talesdev.core.player;

import com.talesdev.core.server.ManagedTask;
import com.talesdev.core.system.Destroyable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

/**
 * Player task manager
 *
 * @author MoKunz
 */
public class PlayerTask implements Destroyable, ManagedTask {
    private final CorePlayer corePlayer;
    private Map<String, BukkitTask> taskMap;

    public PlayerTask(CorePlayer corePlayer) {
        this.corePlayer = corePlayer;
        this.corePlayer.autoDestroy(this);
        taskMap = new HashMap<>();
    }

    public void store(String nameRef, BukkitTask bukkitTask) {
        taskMap.putIfAbsent(nameRef, bukkitTask);
    }

    public BukkitTask get(String nameRef) {
        return taskMap.get(nameRef);
    }

    public void cancel(String nameRef) {
        if (contains(nameRef)) {
            get(nameRef).cancel();
        }
    }

    public void cancelAll() {
        taskMap.keySet().forEach(this::cancel);
    }

    public boolean contains(String nameRef) {
        return taskMap.containsKey(nameRef);
    }

    public void destroy() {
        cancelAll();
        taskMap.clear();
    }

    public CorePlayer getCorePlayer() {
        return corePlayer;
    }
}

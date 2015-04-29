package com.talesdev.core.arena;

import com.talesdev.core.server.ManagedTask;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

/**
 * Arena managed task
 *
 * @author MoKunz
 */
public class ArenaTask implements ManagedTask {
    private Map<String, BukkitTask> taskMap;
    private GameArena gameArena;

    public ArenaTask(GameArena gameArena) {
        this.gameArena = gameArena;
        this.taskMap = new HashMap<>();
    }

    @Override
    public void store(String nameRef, BukkitTask bukkitTask) {
        if (!contains(nameRef)) {
            taskMap.put(nameRef, bukkitTask);
        }
    }

    @Override
    public BukkitTask get(String nameRef) {
        return taskMap.get(nameRef);
    }

    @Override
    public void cancel(String nameRef) {
        if (contains(nameRef)) {
            get(nameRef).cancel();
            taskMap.remove(nameRef);
        }
    }

    @Override
    public void cancelAll() {
        taskMap.keySet().forEach(this::cancel);
        taskMap.clear();
    }

    @Override
    public boolean contains(String nameRef) {
        return taskMap.containsKey(nameRef);
    }

    public GameArena getGameArena() {
        return gameArena;
    }
}

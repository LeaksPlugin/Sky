package com.talesdev.core;

import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

/**
 * Bukkit task reference collection
 * @author MoKunz
 */
public class BukkitTaskReference {
    private Map<String,BukkitTask> taskMap = new HashMap<>();
    public void addTask(String refName,BukkitTask bukkitTask){
        taskMap.putIfAbsent(refName,bukkitTask);
    }
    public BukkitTask getTask(String refName){
        return taskMap.get(refName);
    }
    public BukkitTask removeTask(String refName){
        return taskMap.remove(refName);
    }
}
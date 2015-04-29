package com.talesdev.core.server;

import org.bukkit.scheduler.BukkitTask;

/**
 * Managed task
 *
 * @author MoKunz
 */
public interface ManagedTask {
    void store(String nameRef, BukkitTask bukkitTask);

    BukkitTask get(String nameRef);

    void cancel(String nameRef);

    void cancelAll();

    boolean contains(String nameRef);
}

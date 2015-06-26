package com.talesdev.core.economy;

import org.bukkit.OfflinePlayer;

/**
 * @author MoKunz
 */
public interface Account {
    OfflinePlayer owner();

    float balance();

    boolean has(float amount);

    boolean withdraw(float amount);

    boolean deposit(float amount);

    boolean transferTo(Account account, float amount);

    void set(float amount);

    Bank bank();

    void reset();
}

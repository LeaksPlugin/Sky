package com.talesdev.core.economy;

import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.Set;

/**
 * @author MoKunz
 */
public interface Bank {
    List<Account> allAccounts();

    Set<OfflinePlayer> allMembers();

    Account getAccount(OfflinePlayer player);

    boolean hasAccount(OfflinePlayer player);

    Account createAccount(OfflinePlayer player);

    void deleteAccount(OfflinePlayer player);

    String getName();
}

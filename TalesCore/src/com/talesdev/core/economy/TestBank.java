package com.talesdev.core.economy;

import com.talesdev.core.TalesCore;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author MoKunz
 */
public class TestBank implements Bank {
    private List<Account> accounts = new ArrayList<>();
    private TalesCore core;

    public TestBank(TalesCore core) {
        this.core = core;
    }

    @Override
    public List<Account> allAccounts() {
        return new ArrayList<>(accounts);
    }

    @Override
    public Set<OfflinePlayer> allMembers() {
        return accounts.stream().map(Account::owner).collect(Collectors.toSet());
    }

    @Override
    public Account getAccount(OfflinePlayer player) {
        for (Account account : accounts) {
            if (account.owner().equals(player)) {
                return account;
            }
        }
        return null;
    }

    @Override
    public boolean hasAccount(OfflinePlayer player) {
        return getAccount(player) != null;
    }

    @Override
    public Account createAccount(OfflinePlayer player) {
        Account account = new TestAccount(this, player);
        accounts.add(account);
        return account;
    }

    @Override
    public void deleteAccount(OfflinePlayer player) {
        accounts.remove(getAccount(player));
    }

    @Override
    public String getName() {
        return "TestBank";
    }

    public TalesCore getCore() {
        return core;
    }
}

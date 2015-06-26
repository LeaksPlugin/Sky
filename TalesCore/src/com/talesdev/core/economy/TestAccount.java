package com.talesdev.core.economy;

import org.bukkit.OfflinePlayer;

/**
 * @author MoKunz
 */
public class TestAccount implements Account {
    private float amount = 0.0F;
    private OfflinePlayer player;
    private Bank bank;

    public TestAccount(Bank bank, OfflinePlayer player, float amount) {
        this.bank = bank;
        this.amount = amount;
        this.player = player;
    }

    public TestAccount(Bank bank, OfflinePlayer player) {
        this(bank, player, 0.0F);
    }


    @Override
    public OfflinePlayer owner() {
        return player;
    }

    @Override
    public float balance() {
        return amount;
    }

    @Override
    public boolean has(float amount) {
        return this.amount - amount >= 0.0F;
    }

    @Override
    public boolean withdraw(float amount) {
        if (has(amount)) {
            this.amount -= amount;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deposit(float amount) {
        this.amount += amount;
        return true;
    }

    @Override
    public boolean transferTo(Account account, float amount) {
        if (withdraw(amount)) {
            account.deposit(amount);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void set(float amount) {
        this.amount = amount;
    }

    @Override
    public Bank bank() {
        return bank;
    }

    @Override
    public void reset() {
        this.amount = 0.0F;
    }
}

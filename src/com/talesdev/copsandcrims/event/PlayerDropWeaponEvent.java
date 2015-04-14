package com.talesdev.copsandcrims.event;

import com.talesdev.copsandcrims.weapon.Weapon;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

/**
 * Call when player drop a weapon
 *
 * @author MoKunz
 */
public class PlayerDropWeaponEvent extends Event implements Cancellable {
    private boolean cancel = false;
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Weapon weapon;
    private ItemStack weaponItem;

    /**
     * PlayerDropEvent constructor
     */
    public PlayerDropWeaponEvent(Player player, Weapon weapon, ItemStack weaponItem) {
        this.player = player;
        this.weapon = weapon;
        this.weaponItem = weaponItem;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    public ItemStack getWeaponItem() {
        return weaponItem;
    }

    public void setWeaponItem(ItemStack weaponItem) {
        this.weaponItem = weaponItem;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Player getPlayer() {
        return player;
    }
}
